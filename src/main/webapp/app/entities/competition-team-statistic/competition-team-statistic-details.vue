<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionTeamStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionTeamStatistic.detail.title')">CompetitionTeamStatistic</span> {{competitionTeamStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeamStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeamStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeamStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeamStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeamStatistic.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="competitionTeamStatistic.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionTeamStatistic.competition.id}}">{{competitionTeamStatistic.competition.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.statistic')">Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="competitionTeamStatistic.statistic">
                            <router-link :to="{name: 'CompetitionStatisticItemView', params: {statisticId: competitionTeamStatistic.statistic.id}}">{{competitionTeamStatistic.statistic.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeamStatistic.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="competitionTeamStatistic.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: competitionTeamStatistic.team.id}}">{{competitionTeamStatistic.team.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionTeamStatistic.id" :to="{name: 'CompetitionTeamStatisticEdit', params: {competitionTeamStatisticId: competitionTeamStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-team-statistic-details.component.ts">
</script>
