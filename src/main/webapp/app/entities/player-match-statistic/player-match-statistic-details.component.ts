import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import PlayerMatchStatisticService from './player-match-statistic.service';

@Component
export default class PlayerMatchStatisticDetails extends Vue {
  @Inject('playerMatchStatisticService') private playerMatchStatisticService: () => PlayerMatchStatisticService;
  public playerMatchStatistic: IPlayerMatchStatistic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerMatchStatisticId) {
        vm.retrievePlayerMatchStatistic(to.params.playerMatchStatisticId);
      }
    });
  }

  public retrievePlayerMatchStatistic(playerMatchStatisticId) {
    this.playerMatchStatisticService()
      .find(playerMatchStatisticId)
      .then(res => {
        this.playerMatchStatistic = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
