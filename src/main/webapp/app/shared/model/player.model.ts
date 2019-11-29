import { IPlayerHistory } from '@/shared/model/player-history.model';
import { IPosition } from '@/shared/model/position.model';

export interface IPlayer {
  id?: number;
  firstName?: string;
  lastName?: string;
  fullName?: string;
  email?: string;
  phoneNumber?: string;
  instagram?: string;
  photo?: string;
  idCard?: string;
  address?: string;
  birthPlace?: string;
  birthDate?: Date;
  histories?: IPlayerHistory[];
  position?: IPosition;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public fullName?: string,
    public email?: string,
    public phoneNumber?: string,
    public instagram?: string,
    public photo?: string,
    public idCard?: string,
    public address?: string,
    public birthPlace?: string,
    public birthDate?: Date,
    public histories?: IPlayerHistory[],
    public position?: IPosition
  ) {}
}
