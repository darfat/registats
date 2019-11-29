import { IMatchLineup } from '@/shared/model/match-lineup.model';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

export interface IPlayerMatchStatistic {
  id?: number;
  description?: number;
  value?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  matchLineup?: IMatchLineup;
  statistic?: IPlayerStatisticItem;
}

export class PlayerMatchStatistic implements IPlayerMatchStatistic {
  constructor(
    public id?: number,
    public description?: number,
    public value?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public matchLineup?: IMatchLineup,
    public statistic?: IPlayerStatisticItem
  ) {}
}
