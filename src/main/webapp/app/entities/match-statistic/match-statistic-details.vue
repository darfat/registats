<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchStatistic.detail.title')">MatchStatistic</span> {{matchStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{matchStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{matchStatistic.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{matchStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{matchStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{matchStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="matchStatistic.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchStatistic.team.id}}">{{matchStatistic.team.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.statistic')">Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="matchStatistic.statistic">
                            <router-link :to="{name: 'MatchStatisticItemView', params: {statisticId: matchStatistic.statistic.id}}">{{matchStatistic.statistic.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.matchHomeInfo')">Match Home Info</span>
                    </dt>
                    <dd>
                        <div v-if="matchStatistic.matchHomeInfo">
                            <router-link :to="{name: 'MatchHomeInfoView', params: {matchHomeInfoId: matchStatistic.matchHomeInfo.id}}">{{matchStatistic.matchHomeInfo.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatistic.matchAwayInfo')">Match Away Info</span>
                    </dt>
                    <dd>
                        <div v-if="matchStatistic.matchAwayInfo">
                            <router-link :to="{name: 'MatchAwayInfoView', params: {matchAwayInfoId: matchStatistic.matchAwayInfo.id}}">{{matchStatistic.matchAwayInfo.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchStatistic.id" :to="{name: 'MatchStatisticEdit', params: {matchStatisticId: matchStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-statistic-details.component.ts">
</script>
