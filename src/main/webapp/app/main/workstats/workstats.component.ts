import { Component, Vue, Inject } from 'vue-property-decorator';
import { IMatch } from '@/shared/model/match.model';
import MatchService from '@/entities/match/match.service';
import WorkstatsTeam from '@/main/workstats/workstats-team.vue';
import { ImatchTeamInfo } from '@/shared/model/match-team-info.model';
import { Team } from '@/shared/model/team.model';


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
  beforeRouteEnter(to, from, next) {
    next(vm => {
        let matchId = 1;
        vm.loadMatch(matchId);
    });
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
           });
  }

  public pick(aSide){
    console.log(aSide);
    this.side = aSide;
    this.match.homeTeam.description = (this.t = this.t + 1).toString();
  }

  public initHomeTeamInfo(team: Team){
    this.homeTeamInfo.analysis = "good team";
    this.homeTeamInfo.team = team;
    this.homeTeamInfo.lineups = null;
  }
  public previousState() {
    this.$router.go(-1);
  }
}
