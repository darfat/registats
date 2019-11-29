import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';
import MatchStatisticItemService from './match-statistic-item.service';

@Component
export default class MatchStatisticItemDetails extends Vue {
  @Inject('matchStatisticItemService') private matchStatisticItemService: () => MatchStatisticItemService;
  public matchStatisticItem: IMatchStatisticItem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchStatisticItemId) {
        vm.retrieveMatchStatisticItem(to.params.matchStatisticItemId);
      }
    });
  }

  public retrieveMatchStatisticItem(matchStatisticItemId) {
    this.matchStatisticItemService()
      .find(matchStatisticItemId)
      .then(res => {
        this.matchStatisticItem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
