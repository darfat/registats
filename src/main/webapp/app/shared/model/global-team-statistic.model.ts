import { ITeam } from '@/shared/model/team.model';

export interface IGlobalTeamStatistic {
  id?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  description?: string;
  name?: string;
  team?: ITeam;
}

export class GlobalTeamStatistic implements IGlobalTeamStatistic {
  constructor(
    public id?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public description?: string,
    public name?: string,
    public team?: ITeam
  ) {}
}
