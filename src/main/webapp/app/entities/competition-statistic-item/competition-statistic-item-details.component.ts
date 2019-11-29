import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';
import CompetitionStatisticItemService from './competition-statistic-item.service';

@Component
export default class CompetitionStatisticItemDetails extends Vue {
  @Inject('competitionStatisticItemService') private competitionStatisticItemService: () => CompetitionStatisticItemService;
  public competitionStatisticItem: ICompetitionStatisticItem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionStatisticItemId) {
        vm.retrieveCompetitionStatisticItem(to.params.competitionStatisticItemId);
      }
    });
  }

  public retrieveCompetitionStatisticItem(competitionStatisticItemId) {
    this.competitionStatisticItemService()
      .find(competitionStatisticItemId)
      .then(res => {
        this.competitionStatisticItem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
