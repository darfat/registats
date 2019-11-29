<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="globalTeamStatistic">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.globalTeamStatistic.detail.title')">GlobalTeamStatistic</span> {{globalTeamStatistic.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.valueDouble')">Value Double</span>
                    </dt>
                    <dd>
                        <span>{{globalTeamStatistic.valueDouble}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.valueLong')">Value Long</span>
                    </dt>
                    <dd>
                        <span>{{globalTeamStatistic.valueLong}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.valueString')">Value String</span>
                    </dt>
                    <dd>
                        <span>{{globalTeamStatistic.valueString}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{globalTeamStatistic.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{globalTeamStatistic.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.globalTeamStatistic.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="globalTeamStatistic.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: globalTeamStatistic.team.id}}">{{globalTeamStatistic.team.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="globalTeamStatistic.id" :to="{name: 'GlobalTeamStatisticEdit', params: {globalTeamStatisticId: globalTeamStatistic.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./global-team-statistic-details.component.ts">
</script>
