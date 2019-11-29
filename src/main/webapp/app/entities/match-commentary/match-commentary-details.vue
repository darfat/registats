<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchCommentary">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchCommentary.detail.title')">MatchCommentary</span> {{matchCommentary.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.title')">Title</span>
                    </dt>
                    <dd>
                        <span>{{matchCommentary.title}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{matchCommentary.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.commentaryType')">Commentary Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.CommentaryType.' + matchCommentary.commentaryType)">{{matchCommentary.commentaryType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.minute')">Minute</span>
                    </dt>
                    <dd>
                        <span>{{matchCommentary.minute}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.logDate')">Log Date</span>
                    </dt>
                    <dd>
                        <span>{{matchCommentary.logDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.playerStatistic')">Player Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="matchCommentary.playerStatistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {playerStatisticId: matchCommentary.playerStatistic.id}}">{{matchCommentary.playerStatistic.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.matchStatistic')">Match Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="matchCommentary.matchStatistic">
                            <router-link :to="{name: 'MatchStatisticItemView', params: {matchStatisticId: matchCommentary.matchStatistic.id}}">{{matchCommentary.matchStatistic.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="matchCommentary.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchCommentary.team.id}}">{{matchCommentary.team.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="matchCommentary.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: matchCommentary.player.id}}">{{matchCommentary.player.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchCommentary.match')">Match</span>
                    </dt>
                    <dd>
                        <div v-if="matchCommentary.match">
                            <router-link :to="{name: 'MatchView', params: {matchId: matchCommentary.match.id}}">{{matchCommentary.match.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchCommentary.id" :to="{name: 'MatchCommentaryEdit', params: {matchCommentaryId: matchCommentary.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-commentary-details.component.ts">
</script>
