import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import MatchLineupService from '../match-lineup/match-lineup.service';
import { IMatchLineup } from '@/shared/model/match-lineup.model';

import MatchStatisticService from '../match-statistic/match-statistic.service';
import { IMatchStatistic } from '@/shared/model/match-statistic.model';

import MatchService from '../match/match.service';
import { IMatch } from '@/shared/model/match.model';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import AlertService from '@/shared/alert/alert.service';
import { IMatchAwayInfo, MatchAwayInfo } from '@/shared/model/match-away-info.model';
import MatchAwayInfoService from './match-away-info.service';

const validations: any = {
  matchAwayInfo: {
    description: {},
    formation: {},
    analysis: {},
    preMatchTalk: {},
    postMatchTalk: {}
  }
};

@Component({
  validations
})
export default class MatchAwayInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchAwayInfoService') private matchAwayInfoService: () => MatchAwayInfoService;
  public matchAwayInfo: IMatchAwayInfo = new MatchAwayInfo();

  @Inject('matchLineupService') private matchLineupService: () => MatchLineupService;

  public matchLineups: IMatchLineup[] = [];

  @Inject('matchStatisticService') private matchStatisticService: () => MatchStatisticService;

  public matchStatistics: IMatchStatistic[] = [];

  @Inject('matchService') private matchService: () => MatchService;

  public matches: IMatch[] = [];

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchAwayInfoId) {
        vm.retrieveMatchAwayInfo(to.params.matchAwayInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchAwayInfo.id) {
      this.matchAwayInfoService()
        .update(this.matchAwayInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchAwayInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchAwayInfoService()
        .create(this.matchAwayInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchAwayInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMatchAwayInfo(matchAwayInfoId): void {
    this.matchAwayInfoService()
      .find(matchAwayInfoId)
      .then(res => {
        this.matchAwayInfo = res;
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
    this.matchStatisticService()
      .retrieve()
      .then(res => {
        this.matchStatistics = res.data;
      });
    this.matchService()
      .retrieve()
      .then(res => {
        this.matches = res.data;
      });
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
  }
}
