<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionTeam">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionTeam.detail.title')">CompetitionTeam</span> {{competitionTeam.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeam.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeam.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeam.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeam.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeam.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{competitionTeam.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionTeam.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="competitionTeam.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionTeam.competition.id}}">{{competitionTeam.competition.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionTeam.id" :to="{name: 'CompetitionTeamEdit', params: {competitionTeamId: competitionTeam.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-team-details.component.ts">
</script>
