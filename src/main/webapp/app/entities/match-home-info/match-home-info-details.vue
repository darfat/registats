<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchHomeInfo">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchHomeInfo.detail.title')">MatchHomeInfo</span> {{matchHomeInfo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{matchHomeInfo.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.formation')">Formation</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.Formation.' + matchHomeInfo.formation)">{{matchHomeInfo.formation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.analysis')">Analysis</span>
                    </dt>
                    <dd>
                        <span>{{matchHomeInfo.analysis}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.preMatchTalk')">Pre Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{matchHomeInfo.preMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.postMatchTalk')">Post Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{matchHomeInfo.postMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.match')">Match</span>
                    </dt>
                    <dd>
                        <div v-if="matchHomeInfo.match">
                            <router-link :to="{name: 'MatchView', params: {matchId: matchHomeInfo.match.id}}">{{matchHomeInfo.match.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchHomeInfo.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="matchHomeInfo.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchHomeInfo.team.id}}">{{matchHomeInfo.team.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchHomeInfo.id" :to="{name: 'MatchHomeInfoEdit', params: {matchHomeInfoId: matchHomeInfo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-home-info-details.component.ts">
</script>
