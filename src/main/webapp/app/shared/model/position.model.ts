export const enum PositionCategory {
  GOALKEEPER = 'GOALKEEPER',
  DEFENDER = 'DEFENDER',
  MIDFIELDER = 'MIDFIELDER',
  FORWARD = 'FORWARD'
}

export  enum PositionEnum {
  GK = 'GK',
  CB = 'CB',
  LCB = 'LCB',
  RCB = 'RCB',
  LB = 'LB',
  RB = 'RB',
  DM = 'DM',
  LDM = 'LDM',
  RDM = 'RDM',
  LWB = 'LWB',
  RWB = 'RWB',
  CM = 'CM',
  LCM = 'LCM',
  RCM = 'RCM',
  LM = 'LM',
  RM = 'RM',
  AM = 'AM',
  LAM = 'LAM',
  RAM = 'RAM',
  LW = 'LW',
  RW = 'RW',
  CF = 'CF',
  LCF = 'LCF',
  RCF = 'RCF',
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
