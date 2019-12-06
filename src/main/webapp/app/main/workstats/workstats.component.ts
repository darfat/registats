import { Component, Vue, Inject } from 'vue-property-decorator';
import { IMatch } from '@/shared/model/match.model';
import MatchService from '@/entities/match/match.service';
import WorkstatsTeam from '@/main/workstats/workstats-team.vue';
import { IMatchTeamInfo, MatchTeamInfo } from '@/shared/model/match-team-info.model';
import { Team, ITeam } from '@/shared/model/team.model';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import { IPlayer } from '@/shared/model/player.model';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import { IMatchCommentary } from '@/shared/model/match-commentary.model';
import Stopwatch from '@/public/stopwatch/stopwatch.vue';
import AlertService from '@/shared/alert/alert.service';
import MatchTeamInfoService from '@/entities/match-team-info/match-team-info.service';


@Component({
  components: {
    'workstats-team': WorkstatsTeam,
    'stopwatch': Stopwatch
  }
})
export default class Workstats extends Vue {
    @Inject('matchService') private matchService: () => MatchService;
    @Inject('alertService') private alertService: () => AlertService;
    @Inject('matchTeamInfoService') private matchTeamInfoService: () => MatchTeamInfoService;

    public match: IMatch = {}
    public side: String = 'home';
    public homeTeamInfo: IMatchTeamInfo = {};
    public awayTeamInfo: IMatchTeamInfo = {};
    public t: number = 0;
    public minute: number = 0;
    public pickedTeam: ITeam;
    public runningMatch: boolean= false;
    public matchTime: string = '00:00:00'
    public round : string = '1stHalf';
    public firstHalf: boolean = false;
    public secondHalf: boolean = false;
    
  beforeRouteEnter(to, from, next) {
    next(vm => {
        let matchId = 1;
        vm.loadMatch(matchId);
        console.log('------------LOAD MATCH-------------')
    });
  }

  mounted(){
    
  }

//   public retrieveCompetition(competitionId) {
//     this.competitionService()
//       .find(competitionId)
//       .then(res => {
//         this.competition = res;
//       });
//   }

  public loadMatch(matchId){
    this.matchService()
           .find(matchId)
           .then(res => {
             this.match = res;
             if(this.match.homeTeamInfo === null){
              this.initHomeTeamInfo(this.match.homeTeam)
             } else {
               console.log('load team info....');
               this.homeTeamInfo = this.match.homeTeamInfo;
             }
             this.match.commentaries = [];
             this.$root.$on('addMatchCommentary',(arg1,arg2) =>{
                this.addMatchCommentary(arg1,arg2);
              });
            this.$root.$on('saveTeamInfo',(arg1) =>{
              this.saveTeamInfo(arg1);
            });
            this.pickedTeam = this.match.homeTeam;
           });
  }

  public pick(aSide){
    console.log(aSide);
    this.side = aSide;
    this.match.homeTeam.description = (this.t = this.t + 1).toString();
    if(aSide === 'home'){
      this.pickedTeam = this.match.homeTeam;
    }
    if(aSide === 'away'){
      this.pickedTeam = this.match.awayTeam;
    }
  }

  public initHomeTeamInfo(team: Team){
    console.log('init home team....');
    this.homeTeamInfo.analysis = "good team";
    this.homeTeamInfo.description = " this is a good team";
    this.homeTeamInfo.team = team;
    this.homeTeamInfo.lineups = null;
    this.homeTeamInfo.statistics = null;
    this.match.homeTeamInfo = this.homeTeamInfo;
  }

  public startMatch(){
    this.runningMatch = true;
  }

  public pauseMatch(){
    this.runningMatch = false;
  }

  public endMatch(){
    this.runningMatch = false;
  }

  public isSaving : boolean = true;

  public saveMatch(){
    this.isSaving = true;
    
    if (this.match.id) {
      this.matchService()
        .update(this.match)
        .then(param => {
          this.isSaving = false;
          //this.$router.go(-1);
          const message = this.$t('registatsApp.match.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } 
    this.isSaving = true;
    
  }

  public saveTeamInfo(teamInfo:MatchTeamInfo){
    if(this.side === 'home') {
      console.log(teamInfo);
      console.log(this.homeTeamInfo);
      if (teamInfo.id) {
        this.matchTeamInfoService()
          .updateTeamInfo(teamInfo)
          .then(param => {
            this.isSaving = false;
            this.homeTeamInfo = param;
            this.match.homeTeamInfo = this.homeTeamInfo;
            //const message = this.$t('registatsApp.matchHomeInfo.updated', { param: param.id });
            //this.alertService().showAlert(message, 'info');
          });
      } else {
        this.matchTeamInfoService()
          .createTeamInfo(teamInfo)
          .then(param => {
            this.homeTeamInfo = param;
            this.isSaving = false;
            this.match.homeTeamInfo = this.homeTeamInfo;
            //const message = this.$t('registatsApp.matchHomeInfo.created', { param: param.id });
            //this.alertService().showAlert(message, 'success');
          });
      }
    }
  }

  public getClassRound1(){
    if(this.round === 'firstHalf'){
      return 'btn btn-success';
    }
    return 'btn btn-danger';
  }
  public getClassRound2(){
    if(this.round === 'secondHalf'){
      return 'btn btn-success';
    }
    return 'btn btn-danger';
  }
  public setRound1(event:any){
    this.round = 'firstHalf';
  }
  public setRound2(event:any){
    this.round = 'secondHalf';
  }

  public finished(){
    console.log('finished')
  }

  public previousState() {
    this.$router.go(-1);
  }

  public addMatchCommentary(player: IPlayer, item: IPlayerStatisticItem) {
    console.log('add commentary....');
    const commentary = this.buildCommetaryObject(player,item);
    this.match.commentaries.push(commentary);
  }
  public buildCommetaryObject(player: IPlayer, item: IPlayerStatisticItem): IMatchCommentary {
    const title = this.buildCommetary(player,item);
    const aTime = this.time;
    this.minute = Number(aTime.split(":")[1]);

    let commentary : IMatchCommentary = {};
    commentary.title = title;
    commentary.player = player;
    commentary.playerStatistic = item;
    commentary.team = this.pickedTeam;
    commentary.minute = this.minute;
    commentary.time = aTime;
    commentary.logDate = new Date();
    commentary.round = this.round;
    commentary.description = commentary.team.name + ' '+ this.round + ' ' + commentary.time + ' ' + commentary.title
    commentary.idx = this.match.commentaries.length+1;
    //commentary.match=this.match;

    return commentary;
  }
  public buildCommetary(player: IPlayer, item: IPlayerStatisticItem): string {
    return player.fullName+" "+item.description;
  }

  // start stop watch
  public time: string = '00:00:00'
    public timeBegan : Date = null
    public timeStopped = null
    public stoppedDuration: number = 0
    public started : any = null
    public running: boolean = false;

  public start() {
    if(this.running) return;
    
    if (this.timeBegan === null) {
      this.reset();
      this.timeBegan = new Date();
    }
  
    if (this.timeStopped !== null) {
      this.stoppedDuration += (Date.now() - this.timeStopped);
    }
  
    this.started = setInterval(this.clockRunning, 1000);	
    this.running = true;
  }
  
  public stop() {
    this.running = false;
    this.timeStopped = new Date();
    clearInterval(this.started);
  }
  
  public reset() {
    this.running = false;
    clearInterval(this.started);
    this.stoppedDuration = 0;
    this.timeBegan = null;
    this.timeStopped = null;
    this.time = "00:00:00";
  }
  
  public clockRunning(){
    let currentTime: Date = new Date();  
    let timeElapsed: Date = new Date(Date.now() - this.timeBegan.valueOf()  - this.stoppedDuration)
    let hour = timeElapsed.getUTCHours()
    let min = timeElapsed.getUTCMinutes()
    let sec = timeElapsed.getUTCSeconds()
    let ms = timeElapsed.getUTCMilliseconds();
  
    this.time = 
      this.zeroPrefix(hour, 2) + ":" + 
      this.zeroPrefix(min, 2) + ":" + 
      this.zeroPrefix(sec, 2) ; 
     // this.zeroPrefix(ms, 3);

  };
  
  public zeroPrefix(num, digit) {
    var zero = '';
    for(var i = 0; i < digit; i++) {
      zero += '0';
    }
    return (zero + num).slice(-digit);
  }

  // end stop watch
}
