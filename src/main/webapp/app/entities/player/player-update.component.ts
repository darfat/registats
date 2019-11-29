import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PlayerHistoryService from '../player-history/player-history.service';
import { IPlayerHistory } from '@/shared/model/player-history.model';

import PositionService from '../position/position.service';
import { IPosition } from '@/shared/model/position.model';

import AlertService from '@/shared/alert/alert.service';
import { IPlayer, Player } from '@/shared/model/player.model';
import PlayerService from './player.service';

const validations: any = {
  player: {
    firstName: {},
    lastName: {},
    fullName: {},
    email: {},
    phoneNumber: {},
    instagram: {},
    photo: {},
    idCard: {},
    address: {},
    birthPlace: {},
    birthDate: {}
  }
};

@Component({
  validations
})
export default class PlayerUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('playerService') private playerService: () => PlayerService;
  public player: IPlayer = new Player();

  @Inject('playerHistoryService') private playerHistoryService: () => PlayerHistoryService;

  public playerHistories: IPlayerHistory[] = [];

  @Inject('positionService') private positionService: () => PositionService;

  public positions: IPosition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerId) {
        vm.retrievePlayer(to.params.playerId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.player.id) {
      this.playerService()
        .update(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.player.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.playerService()
        .create(this.player)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.player.created', { param: param.id });
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
      this.player[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.player[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.player[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.player[field] = null;
    }
  }

  public retrievePlayer(playerId): void {
    this.playerService()
      .find(playerId)
      .then(res => {
        res.birthDate = new Date(res.birthDate);
        this.player = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerHistoryService()
      .retrieve()
      .then(res => {
        this.playerHistories = res.data;
      });
    this.positionService()
      .retrieve()
      .then(res => {
        this.positions = res.data;
      });
  }
}
