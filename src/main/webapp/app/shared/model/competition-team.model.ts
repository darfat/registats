import { ICompetition } from '@/shared/model/competition.model';

export interface ICompetitionTeam {
  id?: number;
  status?: string;
  description?: string;
  active?: boolean;
  competition?: ICompetition;
}

export class CompetitionTeam implements ICompetitionTeam {
  constructor(
    public id?: number,
    public status?: string,
    public description?: string,
    public active?: boolean,
    public competition?: ICompetition
  ) {
    this.active = this.active || false;
  }
}
