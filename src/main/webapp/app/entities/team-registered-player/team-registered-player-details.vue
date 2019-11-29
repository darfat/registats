<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="teamRegisteredPlayer">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.teamRegisteredPlayer.detail.title')">TeamRegisteredPlayer</span> {{teamRegisteredPlayer.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{teamRegisteredPlayer.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{teamRegisteredPlayer.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{teamRegisteredPlayer.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="teamRegisteredPlayer.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: teamRegisteredPlayer.team.id}}">{{teamRegisteredPlayer.team.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="teamRegisteredPlayer.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: teamRegisteredPlayer.player.id}}">{{teamRegisteredPlayer.player.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamRegisteredPlayer.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="teamRegisteredPlayer.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: teamRegisteredPlayer.competition.id}}">{{teamRegisteredPlayer.competition.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="teamRegisteredPlayer.id" :to="{name: 'TeamRegisteredPlayerEdit', params: {teamRegisteredPlayerId: teamRegisteredPlayer.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./team-registered-player-details.component.ts">
</script>
