export interface IMatchStatisticItem {
  id?: number;
  name?: string;
  description?: string;
  active?: boolean;
}

export class MatchStatisticItem implements IMatchStatisticItem {
  constructor(public id?: number, public name?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
