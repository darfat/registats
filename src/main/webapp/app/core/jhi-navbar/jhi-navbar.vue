<template>
    <b-navbar toggleable="md" type="dark" class="bg-primary">
        <div class="jh-logo-container float-left">
            <b-navbar-toggle right class="jh-navbar-toggler d-lg-none float-right" href="javascript:void(0);"  data-toggle="collapse" target="header-tabs" aria-expanded="false" aria-label="Toggle navigation">
                <font-awesome-icon icon="bars" />
            </b-navbar-toggle>
            <b-navbar-brand class="logo float-left" b-link to="/">
                <span class="logo-img"></span>
                <span v-text="$t('global.title')" class="navbar-title">registats</span> <span class="navbar-version">{{version}}</span>
            </b-navbar-brand>
        </div>
        <b-collapse is-nav id="header-tabs">
            <b-navbar-nav class="ml-auto">
                <b-nav-item to="/" exact>
                    <span>
                        <font-awesome-icon icon="home" />
                        <span v-text="$t('global.menu.home')">Home</span>
                    </span>
                </b-nav-item>
                <b-nav-item-dropdown
                    id="entity-menu"
                    v-if="authenticated"
                    :class="{'router-link-active': subIsActive('/main')}"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="th-list" />
                        <span v-text="$t('global.menu.home')">Home</span>
                    </span>
                    <b-dropdown-item to="/main/workstats">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.home')">Home</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    id="entity-menu"
                    v-if="authenticated"
                    :class="{'router-link-active': subIsActive('/entity')}"
                    active-class="active" class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="th-list" />
                        <span v-text="$t('global.menu.entities.main')">Entities</span>
                    </span>
                    <b-dropdown-item to="/entity/match-commentary">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchCommentary')">MatchCommentary</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match-lineup">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchLineup')">MatchLineup</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/player-match-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.playerMatchStatistic')">PlayerMatchStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchStatistic')">MatchStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match-home-info">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchHomeInfo')">MatchHomeInfo</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match-away-info">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchAwayInfo')">MatchAwayInfo</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.match')">Match</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/venue">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.venue')">Venue</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-name">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionName')">CompetitionName</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competition')">Competition</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-team">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionTeam')">CompetitionTeam</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-standing">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionStanding')">CompetitionStanding</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-player-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionPlayerStatistic')">CompetitionPlayerStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-team-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionTeamStatistic')">CompetitionTeamStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/team-registered-player">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.teamRegisteredPlayer')">TeamRegisteredPlayer</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/team">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.team')">Team</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/team-member">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.teamMember')">TeamMember</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/player">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.player')">Player</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/player-history">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.playerHistory')">PlayerHistory</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/player-history-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.playerHistoryStatistic')">PlayerHistoryStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/position">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.position')">Position</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/player-statistic-item">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.playerStatisticItem')">PlayerStatisticItem</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/match-statistic-item">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.matchStatisticItem')">MatchStatisticItem</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/competition-statistic-item">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.competitionStatisticItem')">CompetitionStatisticItem</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/global-player-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.globalPlayerStatistic')">GlobalPlayerStatistic</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/entity/global-team-statistic">
                        <font-awesome-icon icon="asterisk" />
                        <span v-text="$t('global.menu.entities.globalTeamStatistic')">GlobalTeamStatistic</span>
                    </b-dropdown-item>
                    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    id="admin-menu"
                    v-if="hasAnyAuthority('ROLE_ADMIN')"
                    :class="{'router-link-active': subIsActive('/admin')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user-plus" />
                        <span v-text="$t('global.menu.admin.main')">Administration</span>
                    </span>
                    <b-dropdown-item to="/admin/user-management">
                        <font-awesome-icon icon="user" />
                        <span v-text="$t('global.menu.admin.userManagement')">User management</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-metrics">
                        <font-awesome-icon icon="tachometer-alt" />
                        <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/admin/jhi-health">
                        <font-awesome-icon icon="heart" />
                        <span v-text="$t('global.menu.admin.health')">Health</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/jhi-configuration">
                        <font-awesome-icon icon="list" />
                        <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/audits">
                        <font-awesome-icon icon="bell" />
                        <span v-text="$t('global.menu.admin.audits')">Audits</span>
                    </b-dropdown-item>
                    <b-dropdown-item  to="/admin/logs">
                        <font-awesome-icon icon="tasks" />
                        <span v-text="$t('global.menu.admin.logs')">Logs</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="swaggerEnabled"  to="/admin/docs">
                        <font-awesome-icon icon="book" />
                        <span v-text="$t('global.menu.admin.apidocs')">API</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
                    <span slot="button-content">
                        <font-awesome-icon icon="flag" />
                        <span v-text="$t('global.menu.language')">Language</span>
                    </span>
                    <b-dropdown-item v-for="(value, key) in languages" :key="`lang-${key}`" v-on:click="changeLanguage(key);"
                        :class="{ active: isActiveLanguage(key)}">
                        {{value.name}}
                    </b-dropdown-item>
                </b-nav-item-dropdown>
                <b-nav-item-dropdown
                    right
                    href="javascript:void(0);"
                    id="account-menu"
                    :class="{'router-link-active': subIsActive('/account')}"
                    active-class="active"
                    class="pointer">
                    <span slot="button-content" class="navbar-dropdown-menu">
                        <font-awesome-icon icon="user" />
                        <span v-text="$t('global.menu.account.main')">
                            Account
                        </span>
                    </span>
                    <b-dropdown-item to="/account/settings" tag="b-dropdown-item" v-if="authenticated">
                        <font-awesome-icon icon="wrench" />
                        <span v-text="$t('global.menu.account.settings')">Settings</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/account/password" tag="b-dropdown-item" v-if="authenticated">
                        <font-awesome-icon icon="lock" />
                        <span v-text="$t('global.menu.account.password')">Password</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="authenticated"  v-on:click="logout()" id="logout">
                        <font-awesome-icon icon="sign-out-alt" />
                        <span v-text="$t('global.menu.account.logout')">Sign out</span>
                    </b-dropdown-item>
                    <b-dropdown-item v-if="!authenticated"  v-on:click="openLogin()" id="login">
                        <font-awesome-icon icon="sign-in-alt" />
                        <span v-text="$t('global.menu.account.login')">Sign in</span>
                    </b-dropdown-item>
                    <b-dropdown-item to="/register" tag="b-dropdown-item" id="register" v-if="!authenticated">
                        <font-awesome-icon icon="user-plus" />
                        <span v-text="$t('global.menu.account.register')">Register</span>
                    </b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</template>

<script lang="ts" src="./jhi-navbar.component.ts">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}


@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span{
    display:none;
  }
}

@media screen and (max-width: 767px) {
  .jh-logo-container {
    width: 100%;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
}
/* waiting for bootstrap fix bug on nav-item-dropdown a:active
https://github.com/bootstrap-vue/bootstrap-vue/issues/2219
*/
nav li.router-link-active .navbar-dropdown-menu {
  cursor: pointer;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url("../../../content/images/logo-jhipster.png") no-repeat center
    center;
  background-size: contain;
  width: 100%;
}
</style>
