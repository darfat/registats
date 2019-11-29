<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchAwayInfo">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchAwayInfo.detail.title')">MatchAwayInfo</span> {{matchAwayInfo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{matchAwayInfo.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.formation')">Formation</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.Formation.' + matchAwayInfo.formation)">{{matchAwayInfo.formation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.analysis')">Analysis</span>
                    </dt>
                    <dd>
                        <span>{{matchAwayInfo.analysis}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.preMatchTalk')">Pre Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{matchAwayInfo.preMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.postMatchTalk')">Post Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{matchAwayInfo.postMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.match')">Match</span>
                    </dt>
                    <dd>
                        <div v-if="matchAwayInfo.match">
                            <router-link :to="{name: 'MatchView', params: {matchId: matchAwayInfo.match.id}}">{{matchAwayInfo.match.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchAwayInfo.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="matchAwayInfo.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchAwayInfo.team.id}}">{{matchAwayInfo.team.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchAwayInfo.id" :to="{name: 'MatchAwayInfoEdit', params: {matchAwayInfoId: matchAwayInfo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-away-info-details.component.ts">
</script>
