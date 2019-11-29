<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="playerHistory">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.playerHistory.detail.title')">PlayerHistory</span> {{playerHistory.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.appearance')">Appearance</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.appearance}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.minutePlayed')">Minute Played</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.minutePlayed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.goals')">Goals</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.goals}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.assists')">Assists</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.assists}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.averageRating')">Average Rating</span>
                    </dt>
                    <dd>
                        <span>{{playerHistory.averageRating}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistory.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="playerHistory.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: playerHistory.player.id}}">{{playerHistory.player.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="playerHistory.id" :to="{name: 'PlayerHistoryEdit', params: {playerHistoryId: playerHistory.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-history-details.component.ts">
</script>
