import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IPosition, Position } from '@/shared/model/position.model';
import PositionService from './position.service';

const validations: any = {
  position: {
    name: {},
    desription: {},
    category: {},
    active: {}
  }
};

@Component({
  validations
})
export default class PositionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('positionService') private positionService: () => PositionService;
  public position: IPosition = new Position();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.positionId) {
        vm.retrievePosition(to.params.positionId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.position.id) {
      this.positionService()
        .update(this.position)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.position.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.positionService()
        .create(this.position)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.position.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePosition(positionId): void {
    this.positionService()
      .find(positionId)
      .then(res => {
        this.position = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
