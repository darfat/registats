import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetition } from '@/shared/model/competition.model';
import CompetitionService from './competition.service';

@Component
export default class CompetitionDetails extends Vue {
  @Inject('competitionService') private competitionService: () => CompetitionService;
  public competition: ICompetition = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionId) {
        vm.retrieveCompetition(to.params.competitionId);
      }
    });
  }

  public retrieveCompetition(competitionId) {
    this.competitionService()
      .find(competitionId)
      .then(res => {
        this.competition = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
