import { IPlayerStatisticItem } from '@/shared/model/player-statistic-item.model';
import { IMatchStatisticItem } from '@/shared/model/match-statistic-item.model';
import { ITeam } from '@/shared/model/team.model';
import { IPlayer } from '@/shared/model/player.model';
import { IMatch } from '@/shared/model/match.model';

export const enum CommentaryType {
  HIGHLIGHT = 'HIGHLIGHT',
  FULL = 'FULL'
}

export interface IMatchCommentary {
  id?: number;
  title?: string;
  description?: string;
  commentaryType?: CommentaryType;
  minute?: number;
  logDate?: Date;
  playerStatistic?: IPlayerStatisticItem;
  matchStatistic?: IMatchStatisticItem;
  team?: ITeam;
  player?: IPlayer;
  match?: IMatch;
  //add
  time ?: string;
  round? : string;

  //transient
  idx?: number;
}

export class MatchCommentary implements IMatchCommentary {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public commentaryType?: CommentaryType,
    public minute?: number,
    public logDate?: Date,
    public playerStatistic?: IPlayerStatisticItem,
    public matchStatistic?: IMatchStatisticItem,
    public team?: ITeam,
    public player?: IPlayer,
    public match?: IMatch
  ) {}
}
