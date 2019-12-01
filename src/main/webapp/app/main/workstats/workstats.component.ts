import { Component, Vue, Inject } from 'vue-property-decorator';
import { IMatch } from '@/shared/model/match.model';
import MatchService from '@/entities/match/match.service';
import WorkstatsTeam from '@/main/workstats/workstats-team.vue';
import { ImatchTeamInfo } from '@/shared/model/match-team-info.model';
import { Team, ITeam } from '@/shared/model/team.model';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import { IPlayer } from '@/shared/model/player.model';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import { IMatchCommentary } from '@/shared/model/match-commentary.model';


@Component({
  components: {
    'workstats-team': WorkstatsTeam
  }
})
export default class Workstats extends Vue {
    @Inject('matchService') private matchService: () => MatchService;

    public match: IMatch = {}
    public side: String = 'home';
    public homeTeamInfo: ImatchTeamInfo = {};
    public awayTeamInfo: ImatchTeamInfo = {};
    public t: number = 0;
    public minute: number = 0;
    public pickedTeam: ITeam;
    public start: boolean= false;

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
             this.initHomeTeamInfo(this.match.homeTeam)
             this.match.commentaries = [];
             this.$root.$on('addMatchCommentary',(arg1,arg2) =>{
              this.addMatchCommentary(arg1,arg2);
            });
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
    this.homeTeamInfo.team = team;
    this.homeTeamInfo.lineups = null;
  }

  public startMatch(){
    this.start = true;
  }

  public pauseMatch(){
    this.start = false;
  }

  public endMatch(){
    this.start = false;
  }

  public previousState() {
    this.$router.go(-1);
  }

  public addMatchCommentary(player: IPlayer, item: IPlayerStatisticItem) {
    console.log('add commentary....');
    const commentary = this.buildCommetaryObject(player,item);
    this.minute = this.match.commentaries.length +1;
    this.match.commentaries.push(commentary);
  }
  public buildCommetaryObject(player: IPlayer, item: IPlayerStatisticItem): IMatchCommentary {
    const title = this.buildCommetary(player,item);
    let commentary : IMatchCommentary = {};
    commentary.title = title;
    commentary.description = '';
    commentary.player = player;
    commentary.playerStatistic = item;
    commentary.team = this.pickedTeam;
    commentary.minute = this.minute;

    return commentary;
  }
  public buildCommetary(player: IPlayer, item: IPlayerStatisticItem): string {
    return player.fullName+" "+item.description;
  }
}
