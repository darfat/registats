import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TeamMemberService from '../team-member/team-member.service';
import { ITeamMember } from '@/shared/model/team-member.model';

import AlertService from '@/shared/alert/alert.service';
import { ITeam, Team } from '@/shared/model/team.model';
import TeamService from './team.service';

const validations: any = {
  team: {
    name: {},
    description: {},
    logo: {},
    managerName: {},
    headCoachName: {}
  }
};

@Component({
  validations
})
export default class TeamUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('teamService') private teamService: () => TeamService;
  public team: ITeam = new Team();

  @Inject('teamMemberService') private teamMemberService: () => TeamMemberService;

  public teamMembers: ITeamMember[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamId) {
        vm.retrieveTeam(to.params.teamId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.team.id) {
      this.teamService()
        .update(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.team.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.teamService()
        .create(this.team)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.team.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTeam(teamId): void {
    this.teamService()
      .find(teamId)
      .then(res => {
        this.team = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamMemberService()
      .retrieve()
      .then(res => {
        this.teamMembers = res.data;
      });
  }
}
