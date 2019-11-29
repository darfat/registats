import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import PlayerHistoryStatisticService from '../player-history-statistic/player-history-statistic.service';
import { IPlayerHistoryStatistic } from '@/shared/model/player-history-statistic.model';

import AlertService from '@/shared/alert/alert.service';
import { IPlayerHistory, PlayerHistory } from '@/shared/model/player-history.model';
import PlayerHistoryService from './player-history.service';

const validations: any = {
  playerHistory: {
    startDate: {},
    endDate: {},
    appearance: {},
    minutePlayed: {},
    goals: {},
    assists: {},
    averageRating: {}
  }
};

@Component({
  validations
})
export default class PlayerHistoryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('playerHistoryService') private playerHistoryService: () => PlayerHistoryService;
  public playerHistory: IPlayerHistory = new PlayerHistory();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('playerHistoryStatisticService') private playerHistoryStatisticService: () => PlayerHistoryStatisticService;

  public playerHistoryStatistics: IPlayerHistoryStatistic[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerHistoryId) {
        vm.retrievePlayerHistory(to.params.playerHistoryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.playerHistory.id) {
      this.playerHistoryService()
        .update(this.playerHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerHistory.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.playerHistoryService()
        .create(this.playerHistory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerHistory.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.playerHistory[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.playerHistory[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.playerHistory[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.playerHistory[field] = null;
    }
  }

  public retrievePlayerHistory(playerHistoryId): void {
    this.playerHistoryService()
      .find(playerHistoryId)
      .then(res => {
        res.startDate = new Date(res.startDate);
        res.endDate = new Date(res.endDate);
        this.playerHistory = res;
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
    this.playerHistoryStatisticService()
      .retrieve()
      .then(res => {
        this.playerHistoryStatistics = res.data;
      });
  }
}
