import { ICompetition } from '@/shared/model/competition.model';
import { ICompetitionStatisticItem } from '@/shared/model/competition-statistic-item.model';
import { IPlayer } from '@/shared/model/player.model';

export interface ICompetitionPlayerStatistic {
  id?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  description?: string;
  name?: string;
  competition?: ICompetition;
  statistic?: ICompetitionStatisticItem;
  player?: IPlayer;
}

export class CompetitionPlayerStatistic implements ICompetitionPlayerStatistic {
  constructor(
    public id?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public description?: string,
    public name?: string,
    public competition?: ICompetition,
    public statistic?: ICompetitionStatisticItem,
    public player?: IPlayer
  ) {}
}
