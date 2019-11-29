import { ICompetition } from '@/shared/model/competition.model';
import { ICompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';
import { ITeam } from '@/shared/model/team.model';

export interface ICompetitionTeamStatistic {
  id?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  description?: string;
  name?: string;
  competition?: ICompetition;
  statistic?: ICompetitionStatisticItem;
  team?: ITeam;
}

export class CompetitionTeamStatistic implements ICompetitionTeamStatistic {
  constructor(
    public id?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public description?: string,
    public name?: string,
    public competition?: ICompetition,
    public statistic?: ICompetitionStatisticItem,
    public team?: ITeam
  ) {}
}
