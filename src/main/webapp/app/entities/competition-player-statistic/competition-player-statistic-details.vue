<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionPlayerStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionPlayerStatistic.detail.title')">CompetitionPlayerStatistic</span> {{competitionPlayerStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{competitionPlayerStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{competitionPlayerStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{competitionPlayerStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{competitionPlayerStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{competitionPlayerStatistic.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="competitionPlayerStatistic.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionPlayerStatistic.competition.id}}">{{competitionPlayerStatistic.competition.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.statistic')">Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="competitionPlayerStatistic.statistic">
                            <router-link :to="{name: 'CompetitionStatisticItemView', params: {statisticId: competitionPlayerStatistic.statistic.id}}">{{competitionPlayerStatistic.statistic.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionPlayerStatistic.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="competitionPlayerStatistic.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: competitionPlayerStatistic.player.id}}">{{competitionPlayerStatistic.player.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionPlayerStatistic.id" :to="{name: 'CompetitionPlayerStatisticEdit', params: {competitionPlayerStatisticId: competitionPlayerStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-player-statistic-details.component.ts">
</script>
