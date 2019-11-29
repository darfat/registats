export interface IPlayerStatisticItem {
  id?: number;
  name?: string;
  description?: string;
  active?: boolean;
}

export class PlayerStatisticItem implements IPlayerStatisticItem {
  constructor(public id?: number, public name?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
