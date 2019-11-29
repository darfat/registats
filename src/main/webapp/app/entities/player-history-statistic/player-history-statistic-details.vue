<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="playerHistoryStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.playerHistoryStatistic.detail.title')">PlayerHistoryStatistic</span> {{playerHistoryStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.playerHistoryStatistic.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{playerHistoryStatistic.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistoryStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{playerHistoryStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistoryStatistic.playerHistory')">Player History</span>
                    </dt>
                    <dd>
                        <div v-if="playerHistoryStatistic.playerHistory">
                            <router-link :to="{name: 'PlayerHistoryView', params: {playerHistoryId: playerHistoryStatistic.playerHistory.id}}">{{playerHistoryStatistic.playerHistory.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerHistoryStatistic.statistic')">Statistic</span>
                    </dt>
                    <dd>
                        <div v-if="playerHistoryStatistic.statistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {statisticId: playerHistoryStatistic.statistic.id}}">{{playerHistoryStatistic.statistic.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="playerHistoryStatistic.id" :to="{name: 'PlayerHistoryStatisticEdit', params: {playerHistoryStatisticId: playerHistoryStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-history-statistic-details.component.ts">
</script>
