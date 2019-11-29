import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatch } from '@/shared/model/match.model';
import MatchService from './match.service';

@Component
export default class MatchDetails extends Vue {
  @Inject('matchService') private matchService: () => MatchService;
  public match: IMatch = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchId) {
        vm.retrieveMatch(to.params.matchId);
      }
    });
  }

  public retrieveMatch(matchId) {
    this.matchService()
      .find(matchId)
      .then(res => {
        this.match = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
