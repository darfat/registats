import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionStanding } from '@/shared/model/competition-standing.model';
import CompetitionStandingService from './competition-standing.service';

@Component
export default class CompetitionStandingDetails extends Vue {
  @Inject('competitionStandingService') private competitionStandingService: () => CompetitionStandingService;
  public competitionStanding: ICompetitionStanding = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionStandingId) {
        vm.retrieveCompetitionStanding(to.params.competitionStandingId);
      }
    });
  }

  public retrieveCompetitionStanding(competitionStandingId) {
    this.competitionStandingService()
      .find(competitionStandingId)
      .then(res => {
        this.competitionStanding = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
