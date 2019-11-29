import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchHomeInfo } from '@/shared/model/match-home-info.model';
import MatchHomeInfoService from './match-home-info.service';

@Component
export default class MatchHomeInfoDetails extends Vue {
  @Inject('matchHomeInfoService') private matchHomeInfoService: () => MatchHomeInfoService;
  public matchHomeInfo: IMatchHomeInfo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchHomeInfoId) {
        vm.retrieveMatchHomeInfo(to.params.matchHomeInfoId);
      }
    });
  }

  public retrieveMatchHomeInfo(matchHomeInfoId) {
    this.matchHomeInfoService()
      .find(matchHomeInfoId)
      .then(res => {
        this.matchHomeInfo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
