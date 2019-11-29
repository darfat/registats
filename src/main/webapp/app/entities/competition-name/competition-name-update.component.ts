import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionName, CompetitionName } from '@/shared/model/competition-name.model';
import CompetitionNameService from './competition-name.service';

const validations: any = {
  competitionName: {
    slug: {},
    city: {},
    region: {},
    nation: {},
    name: {
      required
    },
    description: {},
    active: {}
  }
};

@Component({
  validations
})
export default class CompetitionNameUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionNameService') private competitionNameService: () => CompetitionNameService;
  public competitionName: ICompetitionName = new CompetitionName();

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionNameId) {
        vm.retrieveCompetitionName(to.params.competitionNameId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionName.id) {
      this.competitionNameService()
        .update(this.competitionName)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionName.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionNameService()
        .create(this.competitionName)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionName.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionName(competitionNameId): void {
    this.competitionNameService()
      .find(competitionNameId)
      .then(res => {
        this.competitionName = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitionService()
      .retrieve()
      .then(res => {
        this.competitions = res.data;
      });
  }
}
