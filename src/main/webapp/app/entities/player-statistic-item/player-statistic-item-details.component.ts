import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import PlayerStatisticItemService from './player-statistic-item.service';

@Component
export default class PlayerStatisticItemDetails extends Vue {
  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;
  public playerStatisticItem: IPlayerStatisticItem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerStatisticItemId) {
        vm.retrievePlayerStatisticItem(to.params.playerStatisticItemId);
      }
    });
  }

  public retrievePlayerStatisticItem(playerStatisticItemId) {
    this.playerStatisticItemService()
      .find(playerStatisticItemId)
      .then(res => {
        this.playerStatisticItem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
