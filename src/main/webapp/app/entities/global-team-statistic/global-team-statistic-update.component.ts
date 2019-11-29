import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import AlertService from '@/shared/alert/alert.service';
import { IGlobalTeamStatistic, GlobalTeamStatistic } from '@/shared/model/global-team-statistic.model';
import GlobalTeamStatisticService from './global-team-statistic.service';

const validations: any = {
  globalTeamStatistic: {
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
export default class GlobalTeamStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('globalTeamStatisticService') private globalTeamStatisticService: () => GlobalTeamStatisticService;
  public globalTeamStatistic: IGlobalTeamStatistic = new GlobalTeamStatistic();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.globalTeamStatisticId) {
        vm.retrieveGlobalTeamStatistic(to.params.globalTeamStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.globalTeamStatistic.id) {
      this.globalTeamStatisticService()
        .update(this.globalTeamStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.globalTeamStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.globalTeamStatisticService()
        .create(this.globalTeamStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.globalTeamStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGlobalTeamStatistic(globalTeamStatisticId): void {
    this.globalTeamStatisticService()
      .find(globalTeamStatisticId)
      .then(res => {
        this.globalTeamStatistic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
