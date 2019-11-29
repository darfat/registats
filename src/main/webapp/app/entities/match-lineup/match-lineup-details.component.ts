import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchLineup } from '@/shared/model/match-lineup.model';
import MatchLineupService from './match-lineup.service';

@Component
export default class MatchLineupDetails extends Vue {
  @Inject('matchLineupService') private matchLineupService: () => MatchLineupService;
  public matchLineup: IMatchLineup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchLineupId) {
        vm.retrieveMatchLineup(to.params.matchLineupId);
      }
    });
  }

  public retrieveMatchLineup(matchLineupId) {
    this.matchLineupService()
      .find(matchLineupId)
      .then(res => {
        this.matchLineup = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
