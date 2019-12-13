import { IMatchCommentary } from '@/shared/model/match-commentary.model';
import { IVenue } from '@/shared/model/venue.model';
import { ITeam } from '@/shared/model/team.model';
import { ICompetition } from '@/shared/model/competition.model';
import { MatchTeamInfo } from './match-team-info.model';

export interface IMatch {
  id?: number;
  date?: Date;
  time?: Date;
  name?: string;
  description?: string;
  refree?: string;
  liveStreamUrl?: string;
  wheater?: string;
  wind?: string;
  analysis?: string;
  preMatchTalk?: string;
  postMatchTalk?: string;
  commentaries?: IMatchCommentary[];
  venue?: IVenue;
  homeTeam?: ITeam;
  awayTeam?: ITeam;
  competition?: ICompetition;  
  homeTeamInfo?: MatchTeamInfo;
  awayTeamInfo?: MatchTeamInfo;
  status?: string;
}

export class Match implements IMatch {
  constructor(
    public id?: number,
    public date?: Date,
    public time?: Date,
    public name?: string,
    public description?: string,
    public refree?: string,
    public liveStreamUrl?: string,
    public wheater?: string,
    public wind?: string,
    public analysis?: string,
    public preMatchTalk?: string,
    public postMatchTalk?: string,
    public commentaries?: IMatchCommentary[],
    public venue?: IVenue,
    public homeTeam?: ITeam,
    public awayTeam?: ITeam,
    public competition?: ICompetition,
    public start?: string,
  ) {}
}
