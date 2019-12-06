import { Component, Vue, Inject, Prop } from 'vue-property-decorator';
import { Team, ITeam } from '@/shared/model/team.model';
import { IMatchTeamInfo, MatchTeamInfo } from '@/shared/model/match-team-info.model';
import { IPlayer } from '@/shared/model/player.model';
import PlayerService from '@/entities/player/player.service';
import { IMatchLineup } from '@/shared/model/match-lineup.model';
import StatsPlayer from './stats-player.vue';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import StatsLocation from './stats-location.vue';
import { PositionEnum } from '@/shared/model/position.model';
import DropdownCell from '@/shared/dropdown-cell/dropdown-cell.vue';
import MatchTeamInfoService from '@/entities/match-team-info/match-team-info.service';

@Component({
  components: {
    'stats-player': StatsPlayer,
    'stats-location': StatsLocation,
    'DropdownCell': DropdownCell
  }
})
export default class WorkstatsTeam extends Vue {
  @Prop(Object) readonly team: ITeam | undefined
  @Prop(Object) teamInfo: IMatchTeamInfo | undefined

  @Inject('playerService') private playerService: () => PlayerService;
  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;
  @Inject('matchTeamInfoService') private matchTeamInfoService: () => MatchTeamInfoService;


  public players: IPlayer[] = [];
  public lineups: IMatchLineup[] = [];
  public columns: any =  [
    { field: 'player.fullName',editable: false, title: 'Player' },
    { field: 'number', title: 'Number',editor: 'numeric',width: '80px'  },
    { field: 'role', title: 'Position' ,width: '120px' ,cell: DropdownCell},
    { field: 'minuteIn', title: 'In',editor: 'numeric',width: '80px'   },
    { field: 'minoteOut', title: 'Keluar',editor: 'numeric',width: '80px'  },
  ];
  public completeColumns: any =  [
    { field: 'player.fullName',editable: false, title: 'Player' },
    { field: 'number', title: 'Number',editor: 'numeric',width: '80px'  },
    { field: 'role', title: 'Position' ,width: '120px', cell: DropdownCell},
    { field: 'minuteIn', title: 'In',editor: 'numeric',width: '80px'   },
    { field: 'minoteOut', title: 'Out',editor: 'numeric',width: '80px'  },
  ];
  public minimumColumns: any =  [
    { field: 'player.fullName',editable: false, title: 'Player' },
    { field: 'number', title: 'Number',editor: 'numeric',width: '80px'  },
  ];
  public editID: any= null;
  public playerPicked: IPlayer = null;
  public lineupPlayerPicked: IMatchLineup = null;
  public playerStatisticItems = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
    });
  }

  created () {
    console.log('created.....');
    this.loadReferences();
    
  }
  mounted () {
    console.log('mounted.....');
  }

  public index(){
    console.log(this.team.name);
    console.log(this.teamInfo);
    if(this.teamInfo.lineups == null){
      console.log('init lineup...');
      this.retrieveAllPlayers();    
    } else {
      console.log('continue lineup...');
      this.lineups = this.teamInfo.lineups;
    }
  }

  public retrieveAllPlayers(): void {

    const paginationQuery = {
      page: 0,
      size: 50,
    };
    this.playerService()
      .retrieve()
      .then(
        res => {
          this.players = res.data;
          this.initLineup(this.players);
        },
        err => {
        }
      );
  }
  public initLineup(players: IPlayer[]): void{
    let i=1;
    players.forEach(player => {
      let lineup :IMatchLineup = {};
      lineup.player = player;
      console.log('init statistic for each player...');
      lineup.statistics = this.initPlayerMatchStats(this.playerStatisticItems);
      //temp
      lineup.number = i++;
      if(i<=12){
        lineup.minuteIn = 0;
      } else {
        lineup.minuteIn = -1;
      }

      lineup.role = PositionEnum.CB;
      this.lineups.push(lineup);
    });
  }

  public initPlayerMatchStats(arr: IPlayerStatisticItem[]): IPlayerMatchStatistic[]{
    const matchStats = []
    console.log(arr.length);
    arr.forEach(item => {
      let statItem :IPlayerMatchStatistic = {};
      statItem.statistic = item;
      statItem.value = 0;
      matchStats.push(statItem);
    });
    return matchStats;
  }

  public itemChange(e: any) {
      const data = this.lineups.slice();
      const index = data.findIndex(d => d.player.id === e.dataItem.player.id);
      data[index] = { ...data[index], [e.field]: e.value };
      this.lineups = data;
      Vue.set(e.dataItem, e.field, e.value);
      this.teamInfo.lineups = this.lineups;
  }
  
  public rowClick(e: any) {
      this.editID = e.dataItem.player.id;
      Vue.set(e.dataItem, 'inEdit', true);
      this.pickPlayer(e.dataItem.player);
      this.pickLineup(e.dataItem);
  }

  public pickPlayer(player: IPlayer) {
    this.playerPicked = player;
  }
  public pickLineup(lineup: IMatchLineup) {
    this.lineupPlayerPicked = lineup;
  }
  public commitData(): any {
    console.log({lineups: this.lineups},{teamInfo: this.teamInfo});
    this.teamInfo.lineups = this.lineups;
  }
  public saveLineup(){
    console.log('saving....');
    console.log({lineups: this.lineups},{teamInfo: this.teamInfo});
    this.teamInfo.lineups = this.lineups;
    //this.saveTeamInfo(this.teamInfo);
   this.$root.$emit('saveTeamInfo',this.teamInfo);
    
   // console.log(this.lineupPlayerPicked);
    // if(this.lineupPlayerPicked!=null && ( this.lineupPlayerPicked.statistics === undefined || this.lineupPlayerPicked.statistics === null )){
    //   this.initPlayerMatchStats(this.playerStatisticItems);
    // } else if(this.lineupPlayerPicked.statistics.length === 0){
    //   console.log('init player match stats')
    //   console.log(this.playerStatisticItems);
    //   if(this.playerStatisticItems.length === 0){
    //     this.loadReferences();
    //   }
    //   this.initPlayerMatchStats(this.playerStatisticItems);
    // }
  }

  public saveTeamInfo(teamInfo:MatchTeamInfo){
    if(teamInfo) {
      if (teamInfo.id) {
        this.matchTeamInfoService()
          .updateTeamInfo(teamInfo)
          .then(result => {
            this.teamInfo = result;
            console.log(this.teamInfo);
          });
      } else {
        this.matchTeamInfoService()
          .createTeamInfo(teamInfo)
          .then(result => {
            this.teamInfo = result;
            console.log(this.teamInfo);
          });
      }
    }
  }

  public changeView(value:string){
    if(value==='minimumColumns'){
      this.columns = this.minimumColumns;
    }
    if(value==='completeColumns'){
      this.columns = this.completeColumns;
    }
  }
  public previousState() {
    this.$router.go(-1);
  }

   // get reference
   public loadReferences(){
    this.playerStatisticItemService()
        .retrieve()
        .then(res => {
        this.playerStatisticItems = res.data;
        this.index();
    });
  }
}
