import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import PlayerHistoryService from '../player-history/player-history.service';
import { IPlayerHistory } from '@/shared/model/player-history.model';

import PlayerStatisticItemService from '../player-statistic-item/player-statistic-item.service';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

import AlertService from '@/shared/alert/alert.service';
import { IPlayerHistoryStatistic, PlayerHistoryStatistic } from '@/shared/model/player-history-statistic.model';
import PlayerHistoryStatisticService from './player-history-statistic.service';

const validations: any = {
  playerHistoryStatistic: {
    value: {},
    description: {}
  }
};

@Component({
  validations
})
export default class PlayerHistoryStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('playerHistoryStatisticService') private playerHistoryStatisticService: () => PlayerHistoryStatisticService;
  public playerHistoryStatistic: IPlayerHistoryStatistic = new PlayerHistoryStatistic();

  @Inject('playerHistoryService') private playerHistoryService: () => PlayerHistoryService;

  public playerHistories: IPlayerHistory[] = [];

  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;

  public playerStatisticItems: IPlayerStatisticItem[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerHistoryStatisticId) {
        vm.retrievePlayerHistoryStatistic(to.params.playerHistoryStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.playerHistoryStatistic.id) {
      this.playerHistoryStatisticService()
        .update(this.playerHistoryStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerHistoryStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.playerHistoryStatisticService()
        .create(this.playerHistoryStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerHistoryStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePlayerHistoryStatistic(playerHistoryStatisticId): void {
    this.playerHistoryStatisticService()
      .find(playerHistoryStatisticId)
      .then(res => {
        this.playerHistoryStatistic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerHistoryService()
      .retrieve()
      .then(res => {
        this.playerHistories = res.data;
      });
    this.playerStatisticItemService()
      .retrieve()
      .then(res => {
        this.playerStatisticItems = res.data;
      });
  }
}
