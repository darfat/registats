import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGlobalPlayerStatistic } from '@/shared/model/global-player-statistic.model';
import GlobalPlayerStatisticService from './global-player-statistic.service';

@Component
export default class GlobalPlayerStatisticDetails extends Vue {
  @Inject('globalPlayerStatisticService') private globalPlayerStatisticService: () => GlobalPlayerStatisticService;
  public globalPlayerStatistic: IGlobalPlayerStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalPlayerStatisticId) {
        vm.retrieveGlobalPlayerStatistic(to.params.globalPlayerStatisticId);
      }
    });
  }

  public retrieveGlobalPlayerStatistic(globalPlayerStatisticId) {
    this.globalPlayerStatisticService()
      .find(globalPlayerStatisticId)
      .then(res => {
        this.globalPlayerStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
