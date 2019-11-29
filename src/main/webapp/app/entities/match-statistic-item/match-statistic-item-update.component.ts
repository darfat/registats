import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IMatchStatisticItem, MatchStatisticItem } from '@/shared/model/match-statistic-item.model';
import MatchStatisticItemService from './match-statistic-item.service';

const validations: any = {
  matchStatisticItem: {
    name: {},
    description: {},
    active: {}
  }
};

@Component({
  validations
})
export default class MatchStatisticItemUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchStatisticItemService') private matchStatisticItemService: () => MatchStatisticItemService;
  public matchStatisticItem: IMatchStatisticItem = new MatchStatisticItem();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchStatisticItemId) {
        vm.retrieveMatchStatisticItem(to.params.matchStatisticItemId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchStatisticItem.id) {
      this.matchStatisticItemService()
        .update(this.matchStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchStatisticItem.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchStatisticItemService()
        .create(this.matchStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchStatisticItem.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMatchStatisticItem(matchStatisticItemId): void {
    this.matchStatisticItemService()
      .find(matchStatisticItemId)
      .then(res => {
        this.matchStatisticItem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
