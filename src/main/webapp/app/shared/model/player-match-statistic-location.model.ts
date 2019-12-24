import { IPlayerMatchStatistic } from './player-match-statistic.model';

export interface IPlayerMatchStatisticLocation {
  id?: number;
  location?: string;
  description?: string;
  minute?: number;
  statistic?: IPlayerMatchStatistic;

}

export class PlayerMatchStatisticLocation implements IPlayerMatchStatisticLocation {
  constructor(
    public id?: number,
    public location?: string,
    public description?: string,
    public minute?: number,
    public statistic?: IPlayerMatchStatistic
  ) {}
}
