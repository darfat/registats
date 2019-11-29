import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionStatisticItem, CompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';
import CompetitionStatisticItemService from './competition-statistic-item.service';

const validations: any = {
  competitionStatisticItem: {
    name: {},
    description: {},
    category: {},
    active: {}
  }
};

@Component({
  validations
})
export default class CompetitionStatisticItemUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionStatisticItemService') private competitionStatisticItemService: () => CompetitionStatisticItemService;
  public competitionStatisticItem: ICompetitionStatisticItem = new CompetitionStatisticItem();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionStatisticItemId) {
        vm.retrieveCompetitionStatisticItem(to.params.competitionStatisticItemId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionStatisticItem.id) {
      this.competitionStatisticItemService()
        .update(this.competitionStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionStatisticItem.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionStatisticItemService()
        .create(this.competitionStatisticItem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionStatisticItem.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionStatisticItem(competitionStatisticItemId): void {
    this.competitionStatisticItemService()
      .find(competitionStatisticItemId)
      .then(res => {
        this.competitionStatisticItem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
