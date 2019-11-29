import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeamRegisteredPlayer } from '@/shared/model/team-registered-player.model';
import TeamRegisteredPlayerService from './team-registered-player.service';

@Component
export default class TeamRegisteredPlayerDetails extends Vue {
  @Inject('teamRegisteredPlayerService') private teamRegisteredPlayerService: () => TeamRegisteredPlayerService;
  public teamRegisteredPlayer: ITeamRegisteredPlayer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamRegisteredPlayerId) {
        vm.retrieveTeamRegisteredPlayer(to.params.teamRegisteredPlayerId);
      }
    });
  }

  public retrieveTeamRegisteredPlayer(teamRegisteredPlayerId) {
    this.teamRegisteredPlayerService()
      .find(teamRegisteredPlayerId)
      .then(res => {
        this.teamRegisteredPlayer = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
