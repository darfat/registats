import { ITeam } from '@/shared/model/team.model';
import { IPlayer } from '@/shared/model/player.model';

export interface ITeamMember {
  id?: number;
  number?: number;
  joinDate?: Date;
  status?: string;
  active?: boolean;
  team?: ITeam;
  player?: IPlayer;
}

export class TeamMember implements ITeamMember {
  constructor(
    public id?: number,
    public number?: number,
    public joinDate?: Date,
    public status?: string,
    public active?: boolean,
    public team?: ITeam,
    public player?: IPlayer
  ) {
    this.active = this.active || false;
  }
}
