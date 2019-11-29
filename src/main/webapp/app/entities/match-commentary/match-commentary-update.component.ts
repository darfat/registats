import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PlayerStatisticItemService from '../player-statistic-item/player-statistic-item.service';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

import MatchStatisticItemService from '../match-statistic-item/match-statistic-item.service';
import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import MatchService from '../match/match.service';
import { IMatch } from '@/shared/model/match.model';

import AlertService from '@/shared/alert/alert.service';
import { IMatchCommentary, MatchCommentary } from '@/shared/model/match-commentary.model';
import MatchCommentaryService from './match-commentary.service';

const validations: any = {
  matchCommentary: {
    title: {},
    description: {},
    commentaryType: {},
    minute: {},
    logDate: {}
  }
};

@Component({
  validations
})
export default class MatchCommentaryUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchCommentaryService') private matchCommentaryService: () => MatchCommentaryService;
  public matchCommentary: IMatchCommentary = new MatchCommentary();

  @Inject('playerStatisticItemService') private playerStatisticItemService: () => PlayerStatisticItemService;

  public playerStatisticItems: IPlayerStatisticItem[] = [];

  @Inject('matchStatisticItemService') private matchStatisticItemService: () => MatchStatisticItemService;

  public matchStatisticItems: IMatchStatisticItem[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('matchService') private matchService: () => MatchService;

  public matches: IMatch[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchCommentaryId) {
        vm.retrieveMatchCommentary(to.params.matchCommentaryId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchCommentary.id) {
      this.matchCommentaryService()
        .update(this.matchCommentary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchCommentary.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchCommentaryService()
        .create(this.matchCommentary)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchCommentary.created', { param: param.id });
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
      this.matchCommentary[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.matchCommentary[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.matchCommentary[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.matchCommentary[field] = null;
    }
  }

  public retrieveMatchCommentary(matchCommentaryId): void {
    this.matchCommentaryService()
      .find(matchCommentaryId)
      .then(res => {
        res.logDate = new Date(res.logDate);
        this.matchCommentary = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerStatisticItemService()
      .retrieve()
      .then(res => {
        this.playerStatisticItems = res.data;
      });
    this.matchStatisticItemService()
      .retrieve()
      .then(res => {
        this.matchStatisticItems = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
    this.matchService()
      .retrieve()
      .then(res => {
        this.matches = res.data;
      });
  }
}
