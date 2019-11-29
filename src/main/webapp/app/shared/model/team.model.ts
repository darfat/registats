import { ITeamMember } from '@/shared/model/team-member.model';

export interface ITeam {
  id?: number;
  name?: string;
  description?: string;
  logo?: string;
  managerName?: string;
  headCoachName?: string;
  members?: ITeamMember[];
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public logo?: string,
    public managerName?: string,
    public headCoachName?: string,
    public members?: ITeamMember[]
  ) {}
}
