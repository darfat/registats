import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import MatchCommentaryService from '../match-commentary/match-commentary.service';
import { IMatchCommentary } from '@/shared/model/match-commentary.model';

import VenueService from '../venue/venue.service';
import { IVenue } from '@/shared/model/venue.model';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import AlertService from '@/shared/alert/alert.service';
import { IMatch, Match } from '@/shared/model/match.model';
import MatchService from './match.service';

const validations: any = {
  match: {
    date: {},
    time: {},
    name: {},
    description: {},
    refree: {},
    liveStreamUrl: {},
    wheater: {},
    wind: {},
    analysis: {},
    preMatchTalk: {},
    postMatchTalk: {}
  }
};

@Component({
  validations
})
export default class MatchUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchService') private matchService: () => MatchService;
  public match: IMatch = new Match();

  @Inject('matchCommentaryService') private matchCommentaryService: () => MatchCommentaryService;

  public matchCommentaries: IMatchCommentary[] = [];

  @Inject('venueService') private venueService: () => VenueService;

  public venues: IVenue[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchId) {
        vm.retrieveMatch(to.params.matchId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.match.id) {
      this.matchService()
        .update(this.match)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.match.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchService()
        .create(this.match)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.match.created', { param: param.id });
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
      this.match[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.match[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.match[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.match[field] = null;
    }
  }

  public retrieveMatch(matchId): void {
    this.matchService()
      .find(matchId)
      .then(res => {
        res.date = new Date(res.date);
        res.time = new Date(res.time);
        this.match = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.matchCommentaryService()
      .retrieve()
      .then(res => {
        this.matchCommentaries = res.data;
      });
    this.venueService()
      .retrieve()
      .then(res => {
        this.venues = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.competitionService()
      .retrieve()
      .then(res => {
        this.competitions = res.data;
      });
  }
}
