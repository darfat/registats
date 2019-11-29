import { IMatchLineup } from '@/shared/model/match-lineup.model';
import { IMatchStatistic } from '@/shared/model/match-statistic.model';
import { IMatch } from '@/shared/model/match.model';
import { ITeam } from '@/shared/model/team.model';

export const enum Formation {
  FOURFOURTWO = 'FOURFOURTWO',
  FOURONETWOTHREE = 'FOURONETWOTHREE'
}

export interface IMatchAwayInfo {
  id?: number;
  description?: string;
  formation?: Formation;
  analysis?: string;
  preMatchTalk?: string;
  postMatchTalk?: string;
  lineups?: IMatchLineup[];
  statistics?: IMatchStatistic[];
  match?: IMatch;
  team?: ITeam;
}

export class MatchAwayInfo implements IMatchAwayInfo {
  constructor(
    public id?: number,
    public description?: string,
    public formation?: Formation,
    public analysis?: string,
    public preMatchTalk?: string,
    public postMatchTalk?: string,
    public lineups?: IMatchLineup[],
    public statistics?: IMatchStatistic[],
    public match?: IMatch,
    public team?: ITeam
  ) {}
}
