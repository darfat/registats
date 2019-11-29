import { ITeam } from '@/shared/model/team.model';
import { IPlayer } from '@/shared/model/player.model';
import { ICompetition } from '@/shared/model/competition.model';

export interface ITeamRegisteredPlayer {
  id?: number;
  number?: number;
  status?: string;
  active?: boolean;
  team?: ITeam;
  player?: IPlayer;
  competition?: ICompetition;
}

export class TeamRegisteredPlayer implements ITeamRegisteredPlayer {
  constructor(
    public id?: number,
    public number?: number,
    public status?: string,
    public active?: boolean,
    public team?: ITeam,
    public player?: IPlayer,
    public competition?: ICompetition
  ) {
    this.active = this.active || false;
  }
}
