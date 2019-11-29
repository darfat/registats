import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionTeam, CompetitionTeam } from '@/shared/model/competition-team.model';
import CompetitionTeamService from './competition-team.service';

const validations: any = {
  competitionTeam: {
    status: {},
    description: {},
    active: {}
  }
};

@Component({
  validations
})
export default class CompetitionTeamUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionTeamService') private competitionTeamService: () => CompetitionTeamService;
  public competitionTeam: ICompetitionTeam = new CompetitionTeam();

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionTeamId) {
        vm.retrieveCompetitionTeam(to.params.competitionTeamId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionTeam.id) {
      this.competitionTeamService()
        .update(this.competitionTeam)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionTeam.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionTeamService()
        .create(this.competitionTeam)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionTeam.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionTeam(competitionTeamId): void {
    this.competitionTeamService()
      .find(competitionTeamId)
      .then(res => {
        this.competitionTeam = res;
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
