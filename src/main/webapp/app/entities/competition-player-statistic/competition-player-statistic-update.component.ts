import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import CompetitionStatisticItemService from '../competition-statistic-item/competition-statistic-item.service';
import { ICompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetitionPlayerStatistic, CompetitionPlayerStatistic } from '@/shared/model/competition-player-statistic.model';
import CompetitionPlayerStatisticService from './competition-player-statistic.service';

const validations: any = {
  competitionPlayerStatistic: {
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
export default class CompetitionPlayerStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionPlayerStatisticService') private competitionPlayerStatisticService: () => CompetitionPlayerStatisticService;
  public competitionPlayerStatistic: ICompetitionPlayerStatistic = new CompetitionPlayerStatistic();

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];

  @Inject('competitionStatisticItemService') private competitionStatisticItemService: () => CompetitionStatisticItemService;

  public competitionStatisticItems: ICompetitionStatisticItem[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionPlayerStatisticId) {
        vm.retrieveCompetitionPlayerStatistic(to.params.competitionPlayerStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competitionPlayerStatistic.id) {
      this.competitionPlayerStatisticService()
        .update(this.competitionPlayerStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionPlayerStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionPlayerStatisticService()
        .create(this.competitionPlayerStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competitionPlayerStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCompetitionPlayerStatistic(competitionPlayerStatisticId): void {
    this.competitionPlayerStatisticService()
      .find(competitionPlayerStatisticId)
      .then(res => {
        this.competitionPlayerStatistic = res;
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
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
  }
}
