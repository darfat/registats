import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeamMember } from '@/shared/model/team-member.model';
import TeamMemberService from './team-member.service';

@Component
export default class TeamMemberDetails extends Vue {
  @Inject('teamMemberService') private teamMemberService: () => TeamMemberService;
  public teamMember: ITeamMember = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamMemberId) {
        vm.retrieveTeamMember(to.params.teamMemberId);
      }
    });
  }

  public retrieveTeamMember(teamMemberId) {
    this.teamMemberService()
      .find(teamMemberId)
      .then(res => {
        this.teamMember = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
