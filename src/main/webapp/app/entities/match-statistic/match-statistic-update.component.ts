import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import MatchStatisticItemService from '../match-statistic-item/match-statistic-item.service';
import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';

import MatchHomeInfoService from '../match-home-info/match-home-info.service';
import { IMatchHomeInfo } from '@/shared/model/match-home-info.model';

import MatchAwayInfoService from '../match-away-info/match-away-info.service';
import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';

import AlertService from '@/shared/alert/alert.service';
import { IMatchStatistic, MatchStatistic } from '@/shared/model/match-statistic.model';
import MatchStatisticService from './match-statistic.service';

const validations: any = {
  matchStatistic: {
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
export default class MatchStatisticUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('matchStatisticService') private matchStatisticService: () => MatchStatisticService;
  public matchStatistic: IMatchStatistic = new MatchStatistic();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('matchStatisticItemService') private matchStatisticItemService: () => MatchStatisticItemService;

  public matchStatisticItems: IMatchStatisticItem[] = [];

  @Inject('matchHomeInfoService') private matchHomeInfoService: () => MatchHomeInfoService;

  public matchHomeInfos: IMatchHomeInfo[] = [];

  @Inject('matchAwayInfoService') private matchAwayInfoService: () => MatchAwayInfoService;

  public matchAwayInfos: IMatchAwayInfo[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchStatisticId) {
        vm.retrieveMatchStatistic(to.params.matchStatisticId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.matchStatistic.id) {
      this.matchStatisticService()
        .update(this.matchStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchStatistic.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.matchStatisticService()
        .create(this.matchStatistic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.matchStatistic.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMatchStatistic(matchStatisticId): void {
    this.matchStatisticService()
      .find(matchStatisticId)
      .then(res => {
        this.matchStatistic = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.matchStatisticItemService()
      .retrieve()
      .then(res => {
        this.matchStatisticItems = res.data;
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
