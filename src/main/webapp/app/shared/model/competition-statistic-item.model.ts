export const enum CompetitionStatisticItemCategory {
  TEAM = 'TEAM',
  PLAYER = 'PLAYER'
}

export interface ICompetitionStatisticItem {
  id?: number;
  name?: string;
  description?: string;
  category?: CompetitionStatisticItemCategory;
  active?: boolean;
}

export class CompetitionStatisticItem implements ICompetitionStatisticItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public category?: CompetitionStatisticItemCategory,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
