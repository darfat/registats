import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayerHistory } from '@/shared/model/player-history.model';
import PlayerHistoryService from './player-history.service';

@Component
export default class PlayerHistoryDetails extends Vue {
  @Inject('playerHistoryService') private playerHistoryService: () => PlayerHistoryService;
  public playerHistory: IPlayerHistory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerHistoryId) {
        vm.retrievePlayerHistory(to.params.playerHistoryId);
      }
    });
  }

  public retrievePlayerHistory(playerHistoryId) {
    this.playerHistoryService()
      .find(playerHistoryId)
      .then(res => {
        this.playerHistory = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
