export const enum PositionCategory {
  GOALKEEPER = 'GOALKEEPER',
  DEFENDER = 'DEFENDER',
  MIDFIELDER = 'MIDFIELDER',
  FORWARD = 'FORWARD'
}

export  enum PositionEnum {
  GK = 'GK',
  CB = 'CB',
  CBL = 'CBL',
  CBR = 'CBR',
  LB = 'LB',
  RB = 'RB'
}

export interface IPosition {
  id?: number;
  name?: string;
  desription?: string;
  category?: PositionCategory;
  active?: boolean;
}

export class Position implements IPosition {
  constructor(
    public id?: number,
    public name?: string,
    public desription?: string,
    public category?: PositionCategory,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
