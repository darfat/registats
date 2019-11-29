import { ICompetition } from '@/shared/model/competition.model';

export interface ICompetitionStanding {
  id?: number;
  position?: number;
  played?: number;
  win?: number;
  draw?: number;
  lose?: number;
  goal?: number;
  goalAgaints?: number;
  point?: number;
  minusPoint?: number;
  competition?: ICompetition;
}

export class CompetitionStanding implements ICompetitionStanding {
  constructor(
    public id?: number,
    public position?: number,
    public played?: number,
    public win?: number,
    public draw?: number,
    public lose?: number,
    public goal?: number,
    public goalAgaints?: number,
    public point?: number,
    public minusPoint?: number,
    public competition?: ICompetition
  ) {}
}
