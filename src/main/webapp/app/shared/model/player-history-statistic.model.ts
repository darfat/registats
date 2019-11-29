import { IPlayerHistory } from '@/shared/model/player-history.model';
import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';

export interface IPlayerHistoryStatistic {
  id?: number;
  value?: number;
  description?: string;
  playerHistory?: IPlayerHistory;
  statistic?: IPlayerStatisticItem;
}

export class PlayerHistoryStatistic implements IPlayerHistoryStatistic {
  constructor(
    public id?: number,
    public value?: number,
    public description?: string,
    public playerHistory?: IPlayerHistory,
    public statistic?: IPlayerStatisticItem
  ) {}
}
