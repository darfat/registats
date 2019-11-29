import { IPlayer } from '@/shared/model/player.model';
import { IPlayerHistoryStatistic } from '@/shared/model/player-history-statistic.model';

export interface IPlayerHistory {
  id?: number;
  startDate?: Date;
  endDate?: Date;
  appearance?: number;
  minutePlayed?: number;
  goals?: number;
  assists?: number;
  averageRating?: number;
  player?: IPlayer;
  statistics?: IPlayerHistoryStatistic[];
}

export class PlayerHistory implements IPlayerHistory {
  constructor(
    public id?: number,
    public startDate?: Date,
    public endDate?: Date,
    public appearance?: number,
    public minutePlayed?: number,
    public goals?: number,
    public assists?: number,
    public averageRating?: number,
    public player?: IPlayer,
    public statistics?: IPlayerHistoryStatistic[]
  ) {}
}
