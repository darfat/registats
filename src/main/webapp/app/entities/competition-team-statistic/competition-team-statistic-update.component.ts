import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import CompetitionStatisticItemService from '../competition-statistic-item/competition-statistic-item.service';
import { ICompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionTeamStatistic, CompetitionTeamStatistic } from '@/shared/model/competition-team-statistic.model';
import CompetitionTeamStatisticService from './competition-team-statistic.service';

const validations: any = {
  competitionTeamStatistic: {
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
export default class CompetitionTeamStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionTeamStatisticService') private competitionTeamStatisticService: () => CompetitionTeamStatisticService;
  public competitionTeamStatistic: ICompetitionTeamStatistic = new CompetitionTeamStatistic();

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];

  @Inject('competitionStatisticItemService') private competitionStatisticItemService: () => CompetitionStatisticItemService;

  public competitionStatisticItems: ICompetitionStatisticItem[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionTeamStatisticId) {
        vm.retrieveCompetitionTeamStatistic(to.params.competitionTeamStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionTeamStatistic.id) {
      this.competitionTeamStatisticService()
        .update(this.competitionTeamStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionTeamStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionTeamStatisticService()
        .create(this.competitionTeamStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionTeamStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionTeamStatistic(competitionTeamStatisticId): void {
    this.competitionTeamStatisticService()
      .find(competitionTeamStatisticId)
      .then(res => {
        this.competitionTeamStatistic = res;
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
    this.competitionStatisticItemService()
      .retrieve()
      .then(res => {
        this.competitionStatisticItems = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
