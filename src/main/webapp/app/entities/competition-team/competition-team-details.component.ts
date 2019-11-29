import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionTeam } from '@/shared/model/competition-team.model';
import CompetitionTeamService from './competition-team.service';

@Component
export default class CompetitionTeamDetails extends Vue {
  @Inject('competitionTeamService') private competitionTeamService: () => CompetitionTeamService;
  public competitionTeam: ICompetitionTeam = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionTeamId) {
        vm.retrieveCompetitionTeam(to.params.competitionTeamId);
      }
    });
  }

  public retrieveCompetitionTeam(competitionTeamId) {
    this.competitionTeamService()
      .find(competitionTeamId)
      .then(res => {
        this.competitionTeam = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
