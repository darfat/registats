import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import CompetitionNameService from '../competition-name/competition-name.service';
import { ICompetitionName } from '@/shared/model/competition-name.model';

import CompetitionTeamService from '../competition-team/competition-team.service';
import { ICompetitionTeam } from '@/shared/model/competition-team.model';

import CompetitionStandingService from '../competition-standing/competition-standing.service';
import { ICompetitionStanding } from '@/shared/model/competition-standing.model';

import CompetitionPlayerStatisticService from '../competition-player-statistic/competition-player-statistic.service';
import { ICompetitionPlayerStatistic } from '@/shared/model/competition-player-statistic.model';

import CompetitionTeamStatisticService from '../competition-team-statistic/competition-team-statistic.service';
import { ICompetitionTeamStatistic } from '@/shared/model/competition-team-statistic.model';

import MatchService from '../match/match.service';
import { IMatch } from '@/shared/model/match.model';

import AlertService from '@/shared/alert/alert.service';
import { ICompetition, Competition } from '@/shared/model/competition.model';
import CompetitionService from './competition.service';

const validations: any = {
  competition: {
    season: {
      required
    },
    slug: {},
    startDate: {},
    endDate: {},
    competitionFormat: {}
  }
};

@Component({
  validations
})
export default class CompetitionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('competitionService') private competitionService: () => CompetitionService;
  public competition: ICompetition = new Competition();

  @Inject('competitionNameService') private competitionNameService: () => CompetitionNameService;

  public competitionNames: ICompetitionName[] = [];

  @Inject('competitionTeamService') private competitionTeamService: () => CompetitionTeamService;

  public competitionTeams: ICompetitionTeam[] = [];

  @Inject('competitionStandingService') private competitionStandingService: () => CompetitionStandingService;

  public competitionStandings: ICompetitionStanding[] = [];

  @Inject('competitionPlayerStatisticService') private competitionPlayerStatisticService: () => CompetitionPlayerStatisticService;

  public competitionPlayerStatistics: ICompetitionPlayerStatistic[] = [];

  @Inject('competitionTeamStatisticService') private competitionTeamStatisticService: () => CompetitionTeamStatisticService;

  public competitionTeamStatistics: ICompetitionTeamStatistic[] = [];

  @Inject('matchService') private matchService: () => MatchService;

  public matches: IMatch[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.competitionId) {
        vm.retrieveCompetition(to.params.competitionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.competition.id) {
      this.competitionService()
        .update(this.competition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competition.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.competitionService()
        .create(this.competition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.competition.created', { param: param.id });
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
      this.competition[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.competition[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.competition[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.competition[field] = null;
    }
  }

  public retrieveCompetition(competitionId): void {
    this.competitionService()
      .find(competitionId)
      .then(res => {
        res.startDate = new Date(res.startDate);
        res.endDate = new Date(res.endDate);
        this.competition = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.competitionNameService()
      .retrieve()
      .then(res => {
        this.competitionNames = res.data;
      });
    this.competitionTeamService()
      .retrieve()
      .then(res => {
        this.competitionTeams = res.data;
      });
    this.competitionStandingService()
      .retrieve()
      .then(res => {
        this.competitionStandings = res.data;
      });
    this.competitionPlayerStatisticService()
      .retrieve()
      .then(res => {
        this.competitionPlayerStatistics = res.data;
      });
    this.competitionTeamStatisticService()
      .retrieve()
      .then(res => {
        this.competitionTeamStatistics = res.data;
      });
    this.matchService()
      .retrieve()
      .then(res => {
        this.matches = res.data;
      });
  }
}
