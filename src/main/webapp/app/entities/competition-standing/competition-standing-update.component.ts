import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionStanding, CompetitionStanding } from '@/shared/model/competition-standing.model';
import CompetitionStandingService from './competition-standing.service';

const validations: any = {
  competitionStanding: {
    position: {},
    played: {},
    win: {},
    draw: {},
    lose: {},
    goal: {},
    goalAgaints: {},
    point: {},
    minusPoint: {}
  }
};

@Component({
  validations
})
export default class CompetitionStandingUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionStandingService') private competitionStandingService: () => CompetitionStandingService;
  public competitionStanding: ICompetitionStanding = new CompetitionStanding();

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionStandingId) {
        vm.retrieveCompetitionStanding(to.params.competitionStandingId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionStanding.id) {
      this.competitionStandingService()
        .update(this.competitionStanding)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionStanding.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionStandingService()
        .create(this.competitionStanding)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionStanding.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionStanding(competitionStandingId): void {
    this.competitionStandingService()
      .find(competitionStandingId)
      .then(res => {
        this.competitionStanding = res;
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
