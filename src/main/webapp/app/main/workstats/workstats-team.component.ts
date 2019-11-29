import { Component, Vue, Inject, Prop } from 'vue-property-decorator';
import { Team, ITeam } from '@/shared/model/team.model';
import { ImatchTeamInfo } from '@/shared/model/match-team-info.model';
import { IPlayer } from '@/shared/model/player.model';
import PlayerService from '@/entities/player/player.service';
import { IMatchLineup } from '@/shared/model/match-lineup.model';

@Component
export default class WorkstatsTeam extends Vue {
  @Prop(Object) readonly team: ITeam | undefined
  @Prop(Object) teamInfo: ImatchTeamInfo | undefined

  @Inject('playerService') private playerService: () => PlayerService;


  public players: IPlayer[] = [];
  public lineups: IMatchLineup[] = [];
  public columns: any =  [
    { field: 'player.fullName',editable: false, title: 'Player' },
    { field: 'number', title: 'No. Punggung',editor: 'numeric',width: '150px'  },
  ];
  public editID: any= null;


  beforeRouteEnter(to, from, next) {
    next(vm => {
    });
  }

  created () {
    console.log('created.....');
    this.index();
  }
  mounted () {
    console.log('mounted.....');
  }

  public index(){
    console.log(this.team.name);
    console.log(this.teamInfo);
    if(this.teamInfo.lineups == null){
      console.log('init...');
      this.retrieveAllPlayers();    
    } else {
      console.log('continue...');
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
    players.forEach(player => {
      let lineup :IMatchLineup = {};
      lineup.player = player;
      this.lineups.push(lineup);
    });
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
  }

  public showData(): any {
    console.log({lineups: this.lineups});
    this.teamInfo.lineups = this.lineups;
  }

  public previousState() {
    this.$router.go(-1);
  }
}
