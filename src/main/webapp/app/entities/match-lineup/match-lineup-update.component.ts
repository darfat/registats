import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import PlayerMatchStatisticService from '../player-match-statistic/player-match-statistic.service';
import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';

import PositionService from '../position/position.service';
import { IPosition } from '@/shared/model/position.model';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import MatchHomeInfoService from '../match-home-info/match-home-info.service';
import { IMatchHomeInfo } from '@/shared/model/match-home-info.model';

import MatchAwayInfoService from '../match-away-info/match-away-info.service';
import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';

import AlertService from '@/shared/alert/alert.service';
import { IMatchLineup, MatchLineup } from '@/shared/model/match-lineup.model';
import MatchLineupService from './match-lineup.service';

const validations: any = {
  matchLineup: {
    number: {},
    role: {},
    firstHalfPlay: {},
    secondHalfPlay: {},
    status: {},
    minuteIn: {},
    minoteOut: {}
  }
};

@Component({
  validations
})
export default class MatchLineupUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchLineupService') private matchLineupService: () => MatchLineupService;
  public matchLineup: IMatchLineup = new MatchLineup();

  @Inject('playerMatchStatisticService') private playerMatchStatisticService: () => PlayerMatchStatisticService;

  public playerMatchStatistics: IPlayerMatchStatistic[] = [];

  @Inject('positionService') private positionService: () => PositionService;

  public positions: IPosition[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('matchHomeInfoService') private matchHomeInfoService: () => MatchHomeInfoService;

  public matchHomeInfos: IMatchHomeInfo[] = [];

  @Inject('matchAwayInfoService') private matchAwayInfoService: () => MatchAwayInfoService;

  public matchAwayInfos: IMatchAwayInfo[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchLineupId) {
        vm.retrieveMatchLineup(to.params.matchLineupId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchLineup.id) {
      this.matchLineupService()
        .update(this.matchLineup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchLineup.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchLineupService()
        .create(this.matchLineup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchLineup.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMatchLineup(matchLineupId): void {
    this.matchLineupService()
      .find(matchLineupId)
      .then(res => {
        this.matchLineup = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerMatchStatisticService()
      .retrieve()
      .then(res => {
        this.playerMatchStatistics = res.data;
      });
    this.positionService()
      .retrieve()
      .then(res => {
        this.positions = res.data;
      });
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.matchHomeInfoService()
      .retrieve()
      .then(res => {
        this.matchHomeInfos = res.data;
      });
    this.matchAwayInfoService()
      .retrieve()
      .then(res => {
        this.matchAwayInfos = res.data;
      });
  }
}
