<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="match">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.match.detail.title')">Match</span> {{match.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.match.date')">Date</span>
                    </dt>
                    <dd>
                        <span>{{match.date | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.time')">Time</span>
                    </dt>
                    <dd>
                        <span>{{match.time | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{match.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{match.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.refree')">Refree</span>
                    </dt>
                    <dd>
                        <span>{{match.refree}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.liveStreamUrl')">Live Stream Url</span>
                    </dt>
                    <dd>
                        <span>{{match.liveStreamUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.wheater')">Wheater</span>
                    </dt>
                    <dd>
                        <span>{{match.wheater}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.wind')">Wind</span>
                    </dt>
                    <dd>
                        <span>{{match.wind}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.analysis')">Analysis</span>
                    </dt>
                    <dd>
                        <span>{{match.analysis}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.preMatchTalk')">Pre Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{match.preMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.postMatchTalk')">Post Match Talk</span>
                    </dt>
                    <dd>
                        <span>{{match.postMatchTalk}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.venue')">Venue</span>
                    </dt>
                    <dd>
                        <div v-if="match.venue">
                            <router-link :to="{name: 'VenueView', params: {venueId: match.venue.id}}">{{match.venue.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.homeTeam')">Home Team</span>
                    </dt>
                    <dd>
                        <div v-if="match.homeTeam">
                            <router-link :to="{name: 'TeamView', params: {homeTeamId: match.homeTeam.id}}">{{match.homeTeam.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.awayTeam')">Away Team</span>
                    </dt>
                    <dd>
                        <div v-if="match.awayTeam">
                            <router-link :to="{name: 'TeamView', params: {awayTeamId: match.awayTeam.id}}">{{match.awayTeam.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.match.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="match.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: match.competition.id}}">{{match.competition.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="match.id" :to="{name: 'MatchEdit', params: {matchId: match.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-details.component.ts">
</script>
