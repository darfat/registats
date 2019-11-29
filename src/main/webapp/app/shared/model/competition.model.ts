import { ICompetitionName } from '@/shared/model/competition-name.model';
import { ICompetitionTeam } from '@/shared/model/competition-team.model';
import { ICompetitionStanding } from '@/shared/model/competition-standing.model';
import { ICompetitionPlayerStatistic } from '@/shared/model/competition-player-statistic.model';
import { ICompetitionTeamStatistic } from '@/shared/model/competition-team-statistic.model';
import { IMatch } from '@/shared/model/match.model';

export const enum CompetitionFormat {
  LEAGUE = 'LEAGUE',
  HALF_TOURNAMENT = 'HALF_TOURNAMENT',
  TOURNAMENT = 'TOURNAMENT',
  EXHIBITION = 'EXHIBITION'
}

export interface ICompetition {
  id?: number;
  season?: string;
  slug?: string;
  startDate?: Date;
  endDate?: Date;
  competitionFormat?: CompetitionFormat;
  competitionName?: ICompetitionName;
  teams?: ICompetitionTeam[];
  standings?: ICompetitionStanding[];
  playerStatistics?: ICompetitionPlayerStatistic[];
  teamStatistics?: ICompetitionTeamStatistic[];
  matches?: IMatch[];
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public season?: string,
    public slug?: string,
    public startDate?: Date,
    public endDate?: Date,
    public competitionFormat?: CompetitionFormat,
    public competitionName?: ICompetitionName,
    public teams?: ICompetitionTeam[],
    public standings?: ICompetitionStanding[],
    public playerStatistics?: ICompetitionPlayerStatistic[],
    public teamStatistics?: ICompetitionTeamStatistic[],
    public matches?: IMatch[]
  ) {}
}
