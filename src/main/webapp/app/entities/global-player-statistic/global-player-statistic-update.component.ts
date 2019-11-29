import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import AlertService from '@/shared/alert/alert.service';
import { IGlobalPlayerStatistic, GlobalPlayerStatistic } from '@/shared/model/global-player-statistic.model';
import GlobalPlayerStatisticService from './global-player-statistic.service';

const validations: any = {
  globalPlayerStatistic: {
    valueDouble: {},
    valueLong: {},
    valueString: {},
    description: {},
    name: {}
  }
};

@Component({
  validations
})
export default class GlobalPlayerStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('globalPlayerStatisticService') private globalPlayerStatisticService: () => GlobalPlayerStatisticService;
  public globalPlayerStatistic: IGlobalPlayerStatistic = new GlobalPlayerStatistic();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalPlayerStatisticId) {
        vm.retrieveGlobalPlayerStatistic(to.params.globalPlayerStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.globalPlayerStatistic.id) {
      this.globalPlayerStatisticService()
        .update(this.globalPlayerStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.globalPlayerStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.globalPlayerStatisticService()
        .create(this.globalPlayerStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.globalPlayerStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGlobalPlayerStatistic(globalPlayerStatisticId): void {
    this.globalPlayerStatisticService()
      .find(globalPlayerStatisticId)
      .then(res => {
        this.globalPlayerStatistic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
  }
}
