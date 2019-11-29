import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchCommentary } from '@/shared/model/match-commentary.model';
import MatchCommentaryService from './match-commentary.service';

@Component
export default class MatchCommentaryDetails extends Vue {
  @Inject('matchCommentaryService') private matchCommentaryService: () => MatchCommentaryService;
  public matchCommentary: IMatchCommentary = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchCommentaryId) {
        vm.retrieveMatchCommentary(to.params.matchCommentaryId);
      }
    });
  }

  public retrieveMatchCommentary(matchCommentaryId) {
    this.matchCommentaryService()
      .find(matchCommentaryId)
      .then(res => {
        this.matchCommentary = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
