<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="playerMatchStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.playerMatchStatistic.detail.title')">PlayerMatchStatistic</span> {{playerMatchStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{playerMatchStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{playerMatchStatistic.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{playerMatchStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{playerMatchStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{playerMatchStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.matchLineup')">Match Lineup</span>
                    </dt>
                    <dd>
                        <div v-if="playerMatchStatistic.matchLineup">
                            <router-link :to="{name: 'MatchLineupView', params: {matchLineupId: playerMatchStatistic.matchLineup.id}}">{{playerMatchStatistic.matchLineup.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerMatchStatistic.statistic')">Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="playerMatchStatistic.statistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {statisticId: playerMatchStatistic.statistic.id}}">{{playerMatchStatistic.statistic.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="playerMatchStatistic.id" :to="{name: 'PlayerMatchStatisticEdit', params: {playerMatchStatisticId: playerMatchStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-match-statistic-details.component.ts">
</script>
