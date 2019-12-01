import { IMatchLineup } from '@/shared/model/match-lineup.model';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import { LOCATION } from './location.model';

export interface IPlayerMatchStatistic {
  id?: number;
  description?: number;
  value?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  matchLineup?: IMatchLineup;
  statistic?: IPlayerStatisticItem;

  //todo
  location?: LOCATION
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
    public statistic?: IPlayerStatisticItem,
    public location?: LOCATION
  ) {}
}
