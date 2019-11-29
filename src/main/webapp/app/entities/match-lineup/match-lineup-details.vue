<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchLineup">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchLineup.detail.title')">MatchLineup</span> {{matchLineup.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.role')">Role</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.role}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.firstHalfPlay')">First Half Play</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.firstHalfPlay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.secondHalfPlay')">Second Half Play</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.secondHalfPlay}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.minuteIn')">Minute In</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.minuteIn}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.minoteOut')">Minote Out</span>
                    </dt>
                    <dd>
                        <span>{{matchLineup.minoteOut}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.position')">Position</span>
                    </dt>
                    <dd>
                        <div v-if="matchLineup.position">
                            <router-link :to="{name: 'PositionView', params: {positionId: matchLineup.position.id}}">{{matchLineup.position.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="matchLineup.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: matchLineup.player.id}}">{{matchLineup.player.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.matchHomeInfo')">Match Home Info</span>
                    </dt>
                    <dd>
                        <div v-if="matchLineup.matchHomeInfo">
                            <router-link :to="{name: 'MatchHomeInfoView', params: {matchHomeInfoId: matchLineup.matchHomeInfo.id}}">{{matchLineup.matchHomeInfo.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchLineup.matchAwayInfo')">Match Away Info</span>
                    </dt>
                    <dd>
                        <div v-if="matchLineup.matchAwayInfo">
                            <router-link :to="{name: 'MatchAwayInfoView', params: {matchAwayInfoId: matchLineup.matchAwayInfo.id}}">{{matchLineup.matchAwayInfo.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchLineup.id" :to="{name: 'MatchLineupEdit', params: {matchLineupId: matchLineup.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-lineup-details.component.ts">
</script>
