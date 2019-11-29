import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';
import MatchAwayInfoService from './match-away-info.service';

@Component
export default class MatchAwayInfoDetails extends Vue {
  @Inject('matchAwayInfoService') private matchAwayInfoService: () => MatchAwayInfoService;
  public matchAwayInfo: IMatchAwayInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchAwayInfoId) {
        vm.retrieveMatchAwayInfo(to.params.matchAwayInfoId);
      }
    });
  }

  public retrieveMatchAwayInfo(matchAwayInfoId) {
    this.matchAwayInfoService()
      .find(matchAwayInfoId)
      .then(res => {
        this.matchAwayInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
