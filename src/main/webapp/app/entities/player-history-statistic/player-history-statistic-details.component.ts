import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayerHistoryStatistic } from '@/shared/model/player-history-statistic.model';
import PlayerHistoryStatisticService from './player-history-statistic.service';

@Component
export default class PlayerHistoryStatisticDetails extends Vue {
  @Inject('playerHistoryStatisticService') private playerHistoryStatisticService: () => PlayerHistoryStatisticService;
  public playerHistoryStatistic: IPlayerHistoryStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerHistoryStatisticId) {
        vm.retrievePlayerHistoryStatistic(to.params.playerHistoryStatisticId);
      }
    });
  }

  public retrievePlayerHistoryStatistic(playerHistoryStatisticId) {
    this.playerHistoryStatisticService()
      .find(playerHistoryStatisticId)
      .then(res => {
        this.playerHistoryStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
