import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import AlertService from '@/shared/alert/alert.service';
import { ITeamMember, TeamMember } from '@/shared/model/team-member.model';
import TeamMemberService from './team-member.service';

const validations: any = {
  teamMember: {
    number: {},
    joinDate: {},
    status: {},
    active: {}
  }
};

@Component({
  validations
})
export default class TeamMemberUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('teamMemberService') private teamMemberService: () => TeamMemberService;
  public teamMember: ITeamMember = new TeamMember();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamMemberId) {
        vm.retrieveTeamMember(to.params.teamMemberId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.teamMember.id) {
      this.teamMemberService()
        .update(this.teamMember)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.teamMember.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.teamMemberService()
        .create(this.teamMember)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.teamMember.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.teamMember[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.teamMember[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.teamMember[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.teamMember[field] = null;
    }
  }

  public retrieveTeamMember(teamMemberId): void {
    this.teamMemberService()
      .find(teamMemberId)
      .then(res => {
        res.joinDate = new Date(res.joinDate);
        this.teamMember = res;
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
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
  }
}
