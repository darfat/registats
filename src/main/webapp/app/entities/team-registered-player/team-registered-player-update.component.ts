import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import TeamService from '../team/team.service';
import { ITeam } from '@/shared/model/team.model';

import PlayerService from '../player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import CompetitionService from '../competition/competition.service';
import { ICompetition } from '@/shared/model/competition.model';

import AlertService from '@/shared/alert/alert.service';
import { ITeamRegisteredPlayer, TeamRegisteredPlayer } from '@/shared/model/team-registered-player.model';
import TeamRegisteredPlayerService from './team-registered-player.service';

const validations: any = {
  teamRegisteredPlayer: {
    number: {},
    status: {},
    active: {}
  }
};

@Component({
  validations
})
export default class TeamRegisteredPlayerUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('teamRegisteredPlayerService') private teamRegisteredPlayerService: () => TeamRegisteredPlayerService;
  public teamRegisteredPlayer: ITeamRegisteredPlayer = new TeamRegisteredPlayer();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('competitionService') private competitionService: () => CompetitionService;

  public competitions: ICompetition[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamRegisteredPlayerId) {
        vm.retrieveTeamRegisteredPlayer(to.params.teamRegisteredPlayerId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.teamRegisteredPlayer.id) {
      this.teamRegisteredPlayerService()
        .update(this.teamRegisteredPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.teamRegisteredPlayer.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.teamRegisteredPlayerService()
        .create(this.teamRegisteredPlayer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('registatsApp.teamRegisteredPlayer.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTeamRegisteredPlayer(teamRegisteredPlayerId): void {
    this.teamRegisteredPlayerService()
      .find(teamRegisteredPlayerId)
      .then(res => {
        this.teamRegisteredPlayer = res;
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
    this.competitionService()
      .retrieve()
      .then(res => {
        this.competitions = res.data;
      });
  }
}
