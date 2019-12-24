import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate' // for vue-router 2.2+
]);
import Router from 'vue-router';
const Home = () => import('../core/home/home.vue');
const Error = () => import('../core/error/error.vue');
const Register = () => import('../account/register/register.vue');
const Activate = () => import('../account/activate/activate.vue');
const ResetPasswordInit = () => import('../account/reset-password/init/reset-password-init.vue');
const ResetPasswordFinish = () => import('../account/reset-password/finish/reset-password-finish.vue');
const ChangePassword = () => import('../account/change-password/change-password.vue');
const Settings = () => import('../account/settings/settings.vue');
const JhiUserManagementComponent = () => import('../admin/user-management/user-management.vue');
const JhiUserManagementViewComponent = () => import('../admin/user-management/user-management-view.vue');
const JhiUserManagementEditComponent = () => import('../admin/user-management/user-management-edit.vue');
const JhiConfigurationComponent = () => import('../admin/configuration/configuration.vue');
const JhiDocsComponent = () => import('../admin/docs/docs.vue');
const JhiHealthComponent = () => import('../admin/health/health.vue');
const JhiLogsComponent = () => import('../admin/logs/logs.vue');
const JhiAuditsComponent = () => import('../admin/audits/audits.vue');
const JhiMetricsComponent = () => import('../admin/metrics/metrics.vue');
/* tslint:disable */
// prettier-ignore
const MatchCommentary = () => import('../entities/match-commentary/match-commentary.vue');
// prettier-ignore
const MatchCommentaryUpdate = () => import('../entities/match-commentary/match-commentary-update.vue');
// prettier-ignore
const MatchCommentaryDetails = () => import('../entities/match-commentary/match-commentary-details.vue');
// prettier-ignore
const MatchLineup = () => import('../entities/match-lineup/match-lineup.vue');
// prettier-ignore
const MatchLineupUpdate = () => import('../entities/match-lineup/match-lineup-update.vue');
// prettier-ignore
const MatchLineupDetails = () => import('../entities/match-lineup/match-lineup-details.vue');
// prettier-ignore
const PlayerMatchStatistic = () => import('../entities/player-match-statistic/player-match-statistic.vue');
// prettier-ignore
const PlayerMatchStatisticUpdate = () => import('../entities/player-match-statistic/player-match-statistic-update.vue');
// prettier-ignore
const PlayerMatchStatisticDetails = () => import('../entities/player-match-statistic/player-match-statistic-details.vue');
// prettier-ignore
const MatchStatistic = () => import('../entities/match-statistic/match-statistic.vue');
// prettier-ignore
const MatchStatisticUpdate = () => import('../entities/match-statistic/match-statistic-update.vue');
// prettier-ignore
const MatchStatisticDetails = () => import('../entities/match-statistic/match-statistic-details.vue');
// prettier-ignore
const MatchHomeInfo = () => import('../entities/match-home-info/match-home-info.vue');
// prettier-ignore
const MatchHomeInfoUpdate = () => import('../entities/match-home-info/match-home-info-update.vue');
// prettier-ignore
const MatchHomeInfoDetails = () => import('../entities/match-home-info/match-home-info-details.vue');
// prettier-ignore
const MatchAwayInfo = () => import('../entities/match-away-info/match-away-info.vue');
// prettier-ignore
const MatchAwayInfoUpdate = () => import('../entities/match-away-info/match-away-info-update.vue');
// prettier-ignore
const MatchAwayInfoDetails = () => import('../entities/match-away-info/match-away-info-details.vue');
// prettier-ignore
const Match = () => import('../entities/match/match.vue');
// prettier-ignore
const MatchUpdate = () => import('../entities/match/match-update.vue');
// prettier-ignore
const MatchDetails = () => import('../entities/match/match-details.vue');
// prettier-ignore
const Venue = () => import('../entities/venue/venue.vue');
// prettier-ignore
const VenueUpdate = () => import('../entities/venue/venue-update.vue');
// prettier-ignore
const VenueDetails = () => import('../entities/venue/venue-details.vue');
// prettier-ignore
const CompetitionName = () => import('../entities/competition-name/competition-name.vue');
// prettier-ignore
const CompetitionNameUpdate = () => import('../entities/competition-name/competition-name-update.vue');
// prettier-ignore
const CompetitionNameDetails = () => import('../entities/competition-name/competition-name-details.vue');
// prettier-ignore
const Competition = () => import('../entities/competition/competition.vue');
// prettier-ignore
const CompetitionUpdate = () => import('../entities/competition/competition-update.vue');
// prettier-ignore
const CompetitionDetails = () => import('../entities/competition/competition-details.vue');
// prettier-ignore
const CompetitionTeam = () => import('../entities/competition-team/competition-team.vue');
// prettier-ignore
const CompetitionTeamUpdate = () => import('../entities/competition-team/competition-team-update.vue');
// prettier-ignore
const CompetitionTeamDetails = () => import('../entities/competition-team/competition-team-details.vue');
// prettier-ignore
const CompetitionStanding = () => import('../entities/competition-standing/competition-standing.vue');
// prettier-ignore
const CompetitionStandingUpdate = () => import('../entities/competition-standing/competition-standing-update.vue');
// prettier-ignore
const CompetitionStandingDetails = () => import('../entities/competition-standing/competition-standing-details.vue');
// prettier-ignore
const CompetitionPlayerStatistic = () => import('../entities/competition-player-statistic/competition-player-statistic.vue');
// prettier-ignore
const CompetitionPlayerStatisticUpdate = () => import('../entities/competition-player-statistic/competition-player-statistic-update.vue');
// prettier-ignore
const CompetitionPlayerStatisticDetails = () => import('../entities/competition-player-statistic/competition-player-statistic-details.vue');
// prettier-ignore
const CompetitionTeamStatistic = () => import('../entities/competition-team-statistic/competition-team-statistic.vue');
// prettier-ignore
const CompetitionTeamStatisticUpdate = () => import('../entities/competition-team-statistic/competition-team-statistic-update.vue');
// prettier-ignore
const CompetitionTeamStatisticDetails = () => import('../entities/competition-team-statistic/competition-team-statistic-details.vue');
// prettier-ignore
const TeamRegisteredPlayer = () => import('../entities/team-registered-player/team-registered-player.vue');
// prettier-ignore
const TeamRegisteredPlayerUpdate = () => import('../entities/team-registered-player/team-registered-player-update.vue');
// prettier-ignore
const TeamRegisteredPlayerDetails = () => import('../entities/team-registered-player/team-registered-player-details.vue');
// prettier-ignore
const Team = () => import('../entities/team/team.vue');
// prettier-ignore
const TeamUpdate = () => import('../entities/team/team-update.vue');
// prettier-ignore
const TeamDetails = () => import('../entities/team/team-details.vue');
// prettier-ignore
const TeamMember = () => import('../entities/team-member/team-member.vue');
// prettier-ignore
const TeamMemberUpdate = () => import('../entities/team-member/team-member-update.vue');
// prettier-ignore
const TeamMemberDetails = () => import('../entities/team-member/team-member-details.vue');
// prettier-ignore
const Player = () => import('../entities/player/player.vue');
// prettier-ignore
const PlayerUpdate = () => import('../entities/player/player-update.vue');
// prettier-ignore
const PlayerDetails = () => import('../entities/player/player-details.vue');
// prettier-ignore
const PlayerHistory = () => import('../entities/player-history/player-history.vue');
// prettier-ignore
const PlayerHistoryUpdate = () => import('../entities/player-history/player-history-update.vue');
// prettier-ignore
const PlayerHistoryDetails = () => import('../entities/player-history/player-history-details.vue');
// prettier-ignore
const PlayerHistoryStatistic = () => import('../entities/player-history-statistic/player-history-statistic.vue');
// prettier-ignore
const PlayerHistoryStatisticUpdate = () => import('../entities/player-history-statistic/player-history-statistic-update.vue');
// prettier-ignore
const PlayerHistoryStatisticDetails = () => import('../entities/player-history-statistic/player-history-statistic-details.vue');
// prettier-ignore
const Position = () => import('../entities/position/position.vue');
// prettier-ignore
const PositionUpdate = () => import('../entities/position/position-update.vue');
// prettier-ignore
const PositionDetails = () => import('../entities/position/position-details.vue');
// prettier-ignore
const PlayerStatisticItem = () => import('../entities/player-statistic-item/player-statistic-item.vue');
// prettier-ignore
const PlayerStatisticItemUpdate = () => import('../entities/player-statistic-item/player-statistic-item-update.vue');
// prettier-ignore
const PlayerStatisticItemDetails = () => import('../entities/player-statistic-item/player-statistic-item-details.vue');
// prettier-ignore
const MatchStatisticItem = () => import('../entities/match-statistic-item/match-statistic-item.vue');
// prettier-ignore
const MatchStatisticItemUpdate = () => import('../entities/match-statistic-item/match-statistic-item-update.vue');
// prettier-ignore
const MatchStatisticItemDetails = () => import('../entities/match-statistic-item/match-statistic-item-details.vue');
// prettier-ignore
const CompetitionStatisticItem = () => import('../entities/competition-statistic-item/competition-statistic-item.vue');
// prettier-ignore
const CompetitionStatisticItemUpdate = () => import('../entities/competition-statistic-item/competition-statistic-item-update.vue');
// prettier-ignore
const CompetitionStatisticItemDetails = () => import('../entities/competition-statistic-item/competition-statistic-item-details.vue');
// prettier-ignore
const GlobalPlayerStatistic = () => import('../entities/global-player-statistic/global-player-statistic.vue');
// prettier-ignore
const GlobalPlayerStatisticUpdate = () => import('../entities/global-player-statistic/global-player-statistic-update.vue');
// prettier-ignore
const GlobalPlayerStatisticDetails = () => import('../entities/global-player-statistic/global-player-statistic-details.vue');
// prettier-ignore
const GlobalTeamStatistic = () => import('../entities/global-team-statistic/global-team-statistic.vue');
// prettier-ignore
const GlobalTeamStatisticUpdate = () => import('../entities/global-team-statistic/global-team-statistic-update.vue');
// prettier-ignore
const GlobalTeamStatisticDetails = () => import('../entities/global-team-statistic/global-team-statistic-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here
const Workstats = () => import('../main/workstats/workstats.vue');
const WorkstatsTeam = () => import('../main/workstats/workstats-team.vue');

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/activate',
      name: 'Activate',
      component: Activate
    },
    {
      path: '/reset/request',
      name: 'ResetPasswordInit',
      component: ResetPasswordInit
    },
    {
      path: '/reset/finish',
      name: 'ResetPasswordFinish',
      component: ResetPasswordFinish
    },
    {
      path: '/account/password',
      name: 'ChangePassword',
      component: ChangePassword,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/account/settings',
      name: 'Settings',
      component: Settings,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/admin/user-management',
      name: 'JhiUser',
      component: JhiUserManagementComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/new',
      name: 'JhiUserCreate',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/edit',
      name: 'JhiUserEdit',
      component: JhiUserManagementEditComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/user-management/:userId/view',
      name: 'JhiUserView',
      component: JhiUserManagementViewComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/docs',
      name: 'JhiDocsComponent',
      component: JhiDocsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/audits',
      name: 'JhiAuditsComponent',
      component: JhiAuditsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-health',
      name: 'JhiHealthComponent',
      component: JhiHealthComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/logs',
      name: 'JhiLogsComponent',
      component: JhiLogsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-metrics',
      name: 'JhiMetricsComponent',
      component: JhiMetricsComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    },
    {
      path: '/admin/jhi-configuration',
      name: 'JhiConfigurationComponent',
      component: JhiConfigurationComponent,
      meta: { authorities: ['ROLE_ADMIN'] }
    }
    ,
    {
      path: '/entity/match-commentary',
      name: 'MatchCommentary',
      component: MatchCommentary,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-commentary/new',
      name: 'MatchCommentaryCreate',
      component: MatchCommentaryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-commentary/:matchCommentaryId/edit',
      name: 'MatchCommentaryEdit',
      component: MatchCommentaryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-commentary/:matchCommentaryId/view',
      name: 'MatchCommentaryView',
      component: MatchCommentaryDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match-lineup',
      name: 'MatchLineup',
      component: MatchLineup,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-lineup/new',
      name: 'MatchLineupCreate',
      component: MatchLineupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-lineup/:matchLineupId/edit',
      name: 'MatchLineupEdit',
      component: MatchLineupUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-lineup/:matchLineupId/view',
      name: 'MatchLineupView',
      component: MatchLineupDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/player-match-statistic',
      name: 'PlayerMatchStatistic',
      component: PlayerMatchStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-match-statistic/new',
      name: 'PlayerMatchStatisticCreate',
      component: PlayerMatchStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-match-statistic/:playerMatchStatisticId/edit',
      name: 'PlayerMatchStatisticEdit',
      component: PlayerMatchStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-match-statistic/:playerMatchStatisticId/view',
      name: 'PlayerMatchStatisticView',
      component: PlayerMatchStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match-statistic',
      name: 'MatchStatistic',
      component: MatchStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic/new',
      name: 'MatchStatisticCreate',
      component: MatchStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic/:matchStatisticId/edit',
      name: 'MatchStatisticEdit',
      component: MatchStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic/:matchStatisticId/view',
      name: 'MatchStatisticView',
      component: MatchStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match-home-info',
      name: 'MatchHomeInfo',
      component: MatchHomeInfo,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-home-info/new',
      name: 'MatchHomeInfoCreate',
      component: MatchHomeInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-home-info/:matchHomeInfoId/edit',
      name: 'MatchHomeInfoEdit',
      component: MatchHomeInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-home-info/:matchHomeInfoId/view',
      name: 'MatchHomeInfoView',
      component: MatchHomeInfoDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match-away-info',
      name: 'MatchAwayInfo',
      component: MatchAwayInfo,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-away-info/new',
      name: 'MatchAwayInfoCreate',
      component: MatchAwayInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-away-info/:matchAwayInfoId/edit',
      name: 'MatchAwayInfoEdit',
      component: MatchAwayInfoUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-away-info/:matchAwayInfoId/view',
      name: 'MatchAwayInfoView',
      component: MatchAwayInfoDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match',
      name: 'Match',
      component: Match,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match/new',
      name: 'MatchCreate',
      component: MatchUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match/:matchId/edit',
      name: 'MatchEdit',
      component: MatchUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match/:matchId/view',
      name: 'MatchView',
      component: MatchDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/venue',
      name: 'Venue',
      component: Venue,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/venue/new',
      name: 'VenueCreate',
      component: VenueUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/venue/:venueId/edit',
      name: 'VenueEdit',
      component: VenueUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/venue/:venueId/view',
      name: 'VenueView',
      component: VenueDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-name',
      name: 'CompetitionName',
      component: CompetitionName,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-name/new',
      name: 'CompetitionNameCreate',
      component: CompetitionNameUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-name/:competitionNameId/edit',
      name: 'CompetitionNameEdit',
      component: CompetitionNameUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-name/:competitionNameId/view',
      name: 'CompetitionNameView',
      component: CompetitionNameDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition',
      name: 'Competition',
      component: Competition,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition/new',
      name: 'CompetitionCreate',
      component: CompetitionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition/:competitionId/edit',
      name: 'CompetitionEdit',
      component: CompetitionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition/:competitionId/view',
      name: 'CompetitionView',
      component: CompetitionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-team',
      name: 'CompetitionTeam',
      component: CompetitionTeam,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team/new',
      name: 'CompetitionTeamCreate',
      component: CompetitionTeamUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team/:competitionTeamId/edit',
      name: 'CompetitionTeamEdit',
      component: CompetitionTeamUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team/:competitionTeamId/view',
      name: 'CompetitionTeamView',
      component: CompetitionTeamDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-standing',
      name: 'CompetitionStanding',
      component: CompetitionStanding,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-standing/new',
      name: 'CompetitionStandingCreate',
      component: CompetitionStandingUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-standing/:competitionStandingId/edit',
      name: 'CompetitionStandingEdit',
      component: CompetitionStandingUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-standing/:competitionStandingId/view',
      name: 'CompetitionStandingView',
      component: CompetitionStandingDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-player-statistic',
      name: 'CompetitionPlayerStatistic',
      component: CompetitionPlayerStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-player-statistic/new',
      name: 'CompetitionPlayerStatisticCreate',
      component: CompetitionPlayerStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-player-statistic/:competitionPlayerStatisticId/edit',
      name: 'CompetitionPlayerStatisticEdit',
      component: CompetitionPlayerStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-player-statistic/:competitionPlayerStatisticId/view',
      name: 'CompetitionPlayerStatisticView',
      component: CompetitionPlayerStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-team-statistic',
      name: 'CompetitionTeamStatistic',
      component: CompetitionTeamStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team-statistic/new',
      name: 'CompetitionTeamStatisticCreate',
      component: CompetitionTeamStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team-statistic/:competitionTeamStatisticId/edit',
      name: 'CompetitionTeamStatisticEdit',
      component: CompetitionTeamStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-team-statistic/:competitionTeamStatisticId/view',
      name: 'CompetitionTeamStatisticView',
      component: CompetitionTeamStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/team-registered-player',
      name: 'TeamRegisteredPlayer',
      component: TeamRegisteredPlayer,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-registered-player/new',
      name: 'TeamRegisteredPlayerCreate',
      component: TeamRegisteredPlayerUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-registered-player/:teamRegisteredPlayerId/edit',
      name: 'TeamRegisteredPlayerEdit',
      component: TeamRegisteredPlayerUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-registered-player/:teamRegisteredPlayerId/view',
      name: 'TeamRegisteredPlayerView',
      component: TeamRegisteredPlayerDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/team',
      name: 'Team',
      component: Team,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team/new',
      name: 'TeamCreate',
      component: TeamUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team/:teamId/edit',
      name: 'TeamEdit',
      component: TeamUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team/:teamId/view',
      name: 'TeamView',
      component: TeamDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/team-member',
      name: 'TeamMember',
      component: TeamMember,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-member/new',
      name: 'TeamMemberCreate',
      component: TeamMemberUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-member/:teamMemberId/edit',
      name: 'TeamMemberEdit',
      component: TeamMemberUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/team-member/:teamMemberId/view',
      name: 'TeamMemberView',
      component: TeamMemberDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/player',
      name: 'Player',
      component: Player,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player/new',
      name: 'PlayerCreate',
      component: PlayerUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player/:playerId/edit',
      name: 'PlayerEdit',
      component: PlayerUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player/:playerId/view',
      name: 'PlayerView',
      component: PlayerDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/player-history',
      name: 'PlayerHistory',
      component: PlayerHistory,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history/new',
      name: 'PlayerHistoryCreate',
      component: PlayerHistoryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history/:playerHistoryId/edit',
      name: 'PlayerHistoryEdit',
      component: PlayerHistoryUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history/:playerHistoryId/view',
      name: 'PlayerHistoryView',
      component: PlayerHistoryDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/player-history-statistic',
      name: 'PlayerHistoryStatistic',
      component: PlayerHistoryStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history-statistic/new',
      name: 'PlayerHistoryStatisticCreate',
      component: PlayerHistoryStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history-statistic/:playerHistoryStatisticId/edit',
      name: 'PlayerHistoryStatisticEdit',
      component: PlayerHistoryStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-history-statistic/:playerHistoryStatisticId/view',
      name: 'PlayerHistoryStatisticView',
      component: PlayerHistoryStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/position',
      name: 'Position',
      component: Position,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/position/new',
      name: 'PositionCreate',
      component: PositionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/position/:positionId/edit',
      name: 'PositionEdit',
      component: PositionUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/position/:positionId/view',
      name: 'PositionView',
      component: PositionDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/player-statistic-item',
      name: 'PlayerStatisticItem',
      component: PlayerStatisticItem,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-statistic-item/new',
      name: 'PlayerStatisticItemCreate',
      component: PlayerStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-statistic-item/:playerStatisticItemId/edit',
      name: 'PlayerStatisticItemEdit',
      component: PlayerStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/player-statistic-item/:playerStatisticItemId/view',
      name: 'PlayerStatisticItemView',
      component: PlayerStatisticItemDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/match-statistic-item',
      name: 'MatchStatisticItem',
      component: MatchStatisticItem,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic-item/new',
      name: 'MatchStatisticItemCreate',
      component: MatchStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic-item/:matchStatisticItemId/edit',
      name: 'MatchStatisticItemEdit',
      component: MatchStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/match-statistic-item/:matchStatisticItemId/view',
      name: 'MatchStatisticItemView',
      component: MatchStatisticItemDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/competition-statistic-item',
      name: 'CompetitionStatisticItem',
      component: CompetitionStatisticItem,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-statistic-item/new',
      name: 'CompetitionStatisticItemCreate',
      component: CompetitionStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-statistic-item/:competitionStatisticItemId/edit',
      name: 'CompetitionStatisticItemEdit',
      component: CompetitionStatisticItemUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/competition-statistic-item/:competitionStatisticItemId/view',
      name: 'CompetitionStatisticItemView',
      component: CompetitionStatisticItemDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/global-player-statistic',
      name: 'GlobalPlayerStatistic',
      component: GlobalPlayerStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-player-statistic/new',
      name: 'GlobalPlayerStatisticCreate',
      component: GlobalPlayerStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-player-statistic/:globalPlayerStatisticId/edit',
      name: 'GlobalPlayerStatisticEdit',
      component: GlobalPlayerStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-player-statistic/:globalPlayerStatisticId/view',
      name: 'GlobalPlayerStatisticView',
      component: GlobalPlayerStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    ,
    {
      path: '/entity/global-team-statistic',
      name: 'GlobalTeamStatistic',
      component: GlobalTeamStatistic,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-team-statistic/new',
      name: 'GlobalTeamStatisticCreate',
      component: GlobalTeamStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-team-statistic/:globalTeamStatisticId/edit',
      name: 'GlobalTeamStatisticEdit',
      component: GlobalTeamStatisticUpdate,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/entity/global-team-statistic/:globalTeamStatisticId/view',
      name: 'GlobalTeamStatisticView',
      component: GlobalTeamStatisticDetails,
      meta: { authorities: ['ROLE_USER'] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
    ,
    {
      path: '/main/workstats',
      name: 'Workstats',
      component: Workstats,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/main/workstats/:matchId',
      name: 'Workstats',
      component: Workstats,
      meta: { authorities: ['ROLE_USER'] }
    },
    {
      path: '/main/workstats-team',
      name: 'WorkstatsTeam',
      component: WorkstatsTeam,
      meta: { authorities: ['ROLE_USER'] }
    }
    
  ]
});
