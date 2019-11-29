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
import { IMatchHomeInfo, MatchHomeInfo } from '@/shared/model/match-home-info.model';
import MatchHomeInfoService from './match-home-info.service';

const validations: any = {
  matchHomeInfo: {
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
export default class MatchHomeInfoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchHomeInfoService') private matchHomeInfoService: () => MatchHomeInfoService;
  public matchHomeInfo: IMatchHomeInfo = new MatchHomeInfo();

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
      if (to.params.matchHomeInfoId) {
        vm.retrieveMatchHomeInfo(to.params.matchHomeInfoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchHomeInfo.id) {
      this.matchHomeInfoService()
        .update(this.matchHomeInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchHomeInfo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchHomeInfoService()
        .create(this.matchHomeInfo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchHomeInfo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMatchHomeInfo(matchHomeInfoId): void {
    this.matchHomeInfoService()
      .find(matchHomeInfoId)
      .then(res => {
        this.matchHomeInfo = res;
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
