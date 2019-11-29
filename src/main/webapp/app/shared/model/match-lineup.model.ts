import { IPlayerMatchStatistic } from '@/shared/model/player-match-statistic.model';
import { IPosition } from '@/shared/model/position.model';
import { IPlayer } from '@/shared/model/player.model';
import { IMatchHomeInfo } from '@/shared/model/match-home-info.model';
import { IMatchAwayInfo } from '@/shared/model/match-away-info.model';

export interface IMatchLineup {
  id?: number;
  number?: number;
  role?: string;
  firstHalfPlay?: boolean;
  secondHalfPlay?: boolean;
  status?: string;
  minuteIn?: number;
  minoteOut?: number;
  statistics?: IPlayerMatchStatistic[];
  position?: IPosition;
  player?: IPlayer;
  matchHomeInfo?: IMatchHomeInfo;
  matchAwayInfo?: IMatchAwayInfo;
  //transient
}

export class MatchLineup implements IMatchLineup {
  constructor(
    public id?: number,
    public number?: number,
    public role?: string,
    public firstHalfPlay?: boolean,
    public secondHalfPlay?: boolean,
    public status?: string,
    public minuteIn?: number,
    public minoteOut?: number,
    public statistics?: IPlayerMatchStatistic[],
    public position?: IPosition,
    public player?: IPlayer,
    public matchHomeInfo?: IMatchHomeInfo,
    public matchAwayInfo?: IMatchAwayInfo,
  ) {
    this.firstHalfPlay = this.firstHalfPlay || false;
    this.secondHalfPlay = this.secondHalfPlay || false;
  }
}
