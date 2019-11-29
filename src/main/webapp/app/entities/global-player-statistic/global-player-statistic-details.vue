<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="globalPlayerStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.globalPlayerStatistic.detail.title')">GlobalPlayerStatistic</span> {{globalPlayerStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{globalPlayerStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{globalPlayerStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{globalPlayerStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{globalPlayerStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{globalPlayerStatistic.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalPlayerStatistic.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="globalPlayerStatistic.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: globalPlayerStatistic.player.id}}">{{globalPlayerStatistic.player.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="globalPlayerStatistic.id" :to="{name: 'GlobalPlayerStatisticEdit', params: {globalPlayerStatisticId: globalPlayerStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./global-player-statistic-details.component.ts">
</script>
