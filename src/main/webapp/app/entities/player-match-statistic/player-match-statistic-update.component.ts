import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import MatchLineupService from '../match-lineup/match-lineup.service';
import { IMatchLineup } from '@/shared/model/match-lineup.model';

import PlayerStatisticItemService from '../player-statistic-item/player-statistic-item.service';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

import AlertService from '@/shared/alert/alert.service';
import { IPlayerMatchStatistic, PlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import PlayerMatchStatisticService from './player-match-statistic.service';

const validations: any = {
  playerMatchStatistic: {
    description: {},
    value: {},
    valueDouble: {},
    valueLong: {},
    valueString: {}
  }
};

@Component({
  validations
})
export default class PlayerMatchStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('playerMatchStatisticService') private playerMatchStatisticService: () => PlayerMatchStatisticService;
  public playerMatchStatistic: IPlayerMatchStatistic = new PlayerMatchStatistic();

  @Inject('matchLineupService') private matchLineupService: () => MatchLineupService;

  public matchLineups: IMatchLineup[] = [];

  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;

  public playerStatisticItems: IPlayerStatisticItem[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerMatchStatisticId) {
        vm.retrievePlayerMatchStatistic(to.params.playerMatchStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.playerMatchStatistic.id) {
      this.playerMatchStatisticService()
        .update(this.playerMatchStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerMatchStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.playerMatchStatisticService()
        .create(this.playerMatchStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.playerMatchStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePlayerMatchStatistic(playerMatchStatisticId): void {
    this.playerMatchStatisticService()
      .find(playerMatchStatisticId)
      .then(res => {
        this.playerMatchStatistic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.matchLineupService()
      .retrieve()
      .then(res => {
        this.matchLineups = res.data;
      });
    this.playerStatisticItemService()
      .retrieve()
      .then(res => {
        this.playerStatisticItems = res.data;
      });
  }
}
