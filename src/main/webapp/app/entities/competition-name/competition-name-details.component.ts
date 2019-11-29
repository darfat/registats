import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionName } from '@/shared/model/competition-name.model';
import CompetitionNameService from './competition-name.service';

@Component
export default class CompetitionNameDetails extends Vue {
  @Inject('competitionNameService') private competitionNameService: () => CompetitionNameService;
  public competitionName: ICompetitionName = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionNameId) {
        vm.retrieveCompetitionName(to.params.competitionNameId);
      }
    });
  }

  public retrieveCompetitionName(competitionNameId) {
    this.competitionNameService()
      .find(competitionNameId)
      .then(res => {
        this.competitionName = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
