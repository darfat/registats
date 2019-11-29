import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchStatistic } from '@/shared/model/match-statistic.model';
import MatchStatisticService from './match-statistic.service';

@Component
export default class MatchStatisticDetails extends Vue {
  @Inject('matchStatisticService') private matchStatisticService: () => MatchStatisticService;
  public matchStatistic: IMatchStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchStatisticId) {
        vm.retrieveMatchStatistic(to.params.matchStatisticId);
      }
    });
  }

  public retrieveMatchStatistic(matchStatisticId) {
    this.matchStatisticService()
      .find(matchStatisticId)
      .then(res => {
        this.matchStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
