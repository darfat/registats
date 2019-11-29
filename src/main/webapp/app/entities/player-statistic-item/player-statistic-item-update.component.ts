import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IPlayerStatisticItem, PlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import PlayerStatisticItemService from './player-statistic-item.service';

const validations: any = {
  playerStatisticItem: {
    name: {},
    description: {},
    active: {}
  }
};

@Component({
  validations
})
export default class PlayerStatisticItemUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;
  public playerStatisticItem: IPlayerStatisticItem = new PlayerStatisticItem();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerStatisticItemId) {
        vm.retrievePlayerStatisticItem(to.params.playerStatisticItemId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.playerStatisticItem.id) {
      this.playerStatisticItemService()
        .update(this.playerStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerStatisticItem.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.playerStatisticItemService()
        .create(this.playerStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerStatisticItem.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePlayerStatisticItem(playerStatisticItemId): void {
    this.playerStatisticItemService()
      .find(playerStatisticItemId)
      .then(res => {
        this.playerStatisticItem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
