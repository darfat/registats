// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import App from './app.vue';
import Vue2Filters from 'vue2-filters';
import router from './router';
import * as config from './shared/config/config';
import * as bootstrapVueConfig from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import AuditsService from './admin/audits/audits.service';

import HealthService from './admin/health/health.service';
import MetricsService from './admin/metrics/metrics.service';
import LogsService from './admin/logs/logs.service';
import ActivateService from './account/activate/activate.service';
import RegisterService from './account/register/register.service';
import UserManagementService from '@/admin/user-management/user-management.service';

import LoginService from './account/login.service';
import AccountService from './account/account.service';

import '../content/scss/vendor.scss';
import AlertService from '@/shared/alert/alert.service';
import TranslationService from '@/locale/translation.service';
import ConfigurationService from '@/admin/configuration/configuration.service';

import MatchCommentaryService from '@/entities/match-commentary/match-commentary.service';
import MatchLineupService from '@/entities/match-lineup/match-lineup.service';
import PlayerMatchStatisticService from '@/entities/player-match-statistic/player-match-statistic.service';
import MatchStatisticService from '@/entities/match-statistic/match-statistic.service';
import MatchHomeInfoService from '@/entities/match-home-info/match-home-info.service';
import MatchAwayInfoService from '@/entities/match-away-info/match-away-info.service';
import MatchService from '@/entities/match/match.service';
import VenueService from '@/entities/venue/venue.service';
import CompetitionNameService from '@/entities/competition-name/competition-name.service';
import CompetitionService from '@/entities/competition/competition.service';
import CompetitionTeamService from '@/entities/competition-team/competition-team.service';
import CompetitionStandingService from '@/entities/competition-standing/competition-standing.service';
import CompetitionPlayerStatisticService from '@/entities/competition-player-statistic/competition-player-statistic.service';
import CompetitionTeamStatisticService from '@/entities/competition-team-statistic/competition-team-statistic.service';
import TeamRegisteredPlayerService from '@/entities/team-registered-player/team-registered-player.service';
import TeamService from '@/entities/team/team.service';
import TeamMemberService from '@/entities/team-member/team-member.service';
import PlayerService from '@/entities/player/player.service';
import PlayerHistoryService from '@/entities/player-history/player-history.service';
import PlayerHistoryStatisticService from '@/entities/player-history-statistic/player-history-statistic.service';
import PositionService from '@/entities/position/position.service';
import PlayerStatisticItemService from '@/entities/player-statistic-item/player-statistic-item.service';
import MatchStatisticItemService from '@/entities/match-statistic-item/match-statistic-item.service';
import CompetitionStatisticItemService from '@/entities/competition-statistic-item/competition-statistic-item.service';
import GlobalPlayerStatisticService from '@/entities/global-player-statistic/global-player-statistic.service';
import GlobalTeamStatisticService from '@/entities/global-team-statistic/global-team-statistic.service';
// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here
import MatchTeamInfoService from '@/entities/match-team-info/match-team-info.service';

import '@progress/kendo-theme-default/dist/all.css'
import { Grid } from '@progress/kendo-vue-grid'
import { MaskedTextBox,
  NumericTextBox,
  ColorPicker,
  Slider,
  RangeSlider,
  Switch,
  InputsInstaller } from '@progress/kendo-inputs-vue-wrapper'

  export let aSwitch: Switch

Vue.use(InputsInstaller);
Vue.component('Grid', Grid);
Vue.component('Switch', aSwitch);


Vue.config.productionTip = false;
config.initVueApp(Vue);
config.initFortAwesome(Vue);
bootstrapVueConfig.initBootstrapVue(Vue);
Vue.use(Vue2Filters);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('jhi-item-count', JhiItemCountComponent);

const i18n = config.initI18N(Vue);
const store = config.initVueXStore(Vue);

const alertService = new AlertService(store);
const translationService = new TranslationService(store, i18n);
const loginService = new LoginService();
const accountService = new AccountService(store, translationService, router);

router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    next('/not-found');
  }

  if (to.meta && to.meta.authorities && to.meta.authorities.length > 0) {
    if (!accountService.hasAnyAuthority(to.meta.authorities)) {
      sessionStorage.setItem('requested-url', to.fullPath);
      next('/forbidden');
    } else {
      next();
    }
  } else {
    // no authorities, so just proceed
    next();
  }
});

/* tslint:disable */
new Vue({
  el: '#app',
  components: { App },
  template: '<App/>',
  router,
  provide: {
    loginService: () => loginService,
    activateService: () => new ActivateService(),
    registerService: () => new RegisterService(),
    userService: () => new UserManagementService(),

    auditsService: () => new AuditsService(),

    healthService: () => new HealthService(),

    configurationService: () => new ConfigurationService(),
    logsService: () => new LogsService(),
    metricsService: () => new MetricsService(),
    alertService: () => alertService,
    translationService: () => translationService,
    matchCommentaryService: () => new MatchCommentaryService(),
    matchLineupService: () => new MatchLineupService(),
    playerMatchStatisticService: () => new PlayerMatchStatisticService(),
    matchStatisticService: () => new MatchStatisticService(),
    matchHomeInfoService: () => new MatchHomeInfoService(),
    matchAwayInfoService: () => new MatchAwayInfoService(),
    matchService: () => new MatchService(),
    venueService: () => new VenueService(),
    competitionNameService: () => new CompetitionNameService(),
    competitionService: () => new CompetitionService(),
    competitionTeamService: () => new CompetitionTeamService(),
    competitionStandingService: () => new CompetitionStandingService(),
    competitionPlayerStatisticService: () => new CompetitionPlayerStatisticService(),
    competitionTeamStatisticService: () => new CompetitionTeamStatisticService(),
    teamRegisteredPlayerService: () => new TeamRegisteredPlayerService(),
    teamService: () => new TeamService(),
    teamMemberService: () => new TeamMemberService(),
    playerService: () => new PlayerService(),
    playerHistoryService: () => new PlayerHistoryService(),
    playerHistoryStatisticService: () => new PlayerHistoryStatisticService(),
    positionService: () => new PositionService(),
    playerStatisticItemService: () => new PlayerStatisticItemService(),
    matchStatisticItemService: () => new MatchStatisticItemService(),
    competitionStatisticItemService: () => new CompetitionStatisticItemService(),
    globalPlayerStatisticService: () => new GlobalPlayerStatisticService(),
    globalTeamStatisticService: () => new GlobalTeamStatisticService(),
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
    matchTeamInfoService: () => new MatchTeamInfoService(),
    accountService: () => accountService
  },
  i18n,
  store
});
