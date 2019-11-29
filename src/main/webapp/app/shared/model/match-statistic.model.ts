import { ITeam } from '@/shared/model/team.model';
import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';
import { IMatchHomeInfo } from '@/shared/model/match-home-info.model';
import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';

export interface IMatchStatistic {
  id?: number;
  description?: string;
  value?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  team?: ITeam;
  statistic?: IMatchStatisticItem;
  matchHomeInfo?: IMatchHomeInfo;
  matchAwayInfo?: IMatchAwayInfo;
}

export class MatchStatistic implements IMatchStatistic {
  constructor(
    public id?: number,
    public description?: string,
    public value?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public team?: ITeam,
    public statistic?: IMatchStatisticItem,
    public matchHomeInfo?: IMatchHomeInfo,
    public matchAwayInfo?: IMatchAwayInfo
  ) {}
}
