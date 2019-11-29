import { IPlayer } from '@/shared/model/player.model';

export interface IGlobalPlayerStatistic {
  id?: number;
  valueDouble?: number;
  valueLong?: number;
  valueString?: string;
  description?: string;
  name?: string;
  player?: IPlayer;
}

export class GlobalPlayerStatistic implements IGlobalPlayerStatistic {
  constructor(
    public id?: number,
    public valueDouble?: number,
    public valueLong?: number,
    public valueString?: string,
    public description?: string,
    public name?: string,
    public player?: IPlayer
  ) {}
}
