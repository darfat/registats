import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGlobalTeamStatistic } from '@/shared/model/global-team-statistic.model';
import GlobalTeamStatisticService from './global-team-statistic.service';

@Component
export default class GlobalTeamStatisticDetails extends Vue {
  @Inject('globalTeamStatisticService') private globalTeamStatisticService: () => GlobalTeamStatisticService;
  public globalTeamStatistic: IGlobalTeamStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalTeamStatisticId) {
        vm.retrieveGlobalTeamStatistic(to.params.globalTeamStatisticId);
      }
    });
  }

  public retrieveGlobalTeamStatistic(globalTeamStatisticId) {
    this.globalTeamStatisticService()
      .find(globalTeamStatisticId)
      .then(res => {
        this.globalTeamStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
