import { ICompetition } from '@/shared/model/competition.model';

export interface ICompetitionName {
  id?: number;
  slug?: string;
  city?: string;
  region?: string;
  nation?: string;
  name?: string;
  description?: string;
  active?: boolean;
  competitions?: ICompetition[];
}

export class CompetitionName implements ICompetitionName {
  constructor(
    public id?: number,
    public slug?: string,
    public city?: string,
    public region?: string,
    public nation?: string,
    public name?: string,
    public description?: string,
    public active?: boolean,
    public competitions?: ICompetition[]
  ) {
    this.active = this.active || false;
  }
}
