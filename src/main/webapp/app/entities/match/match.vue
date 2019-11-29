<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.match.home.title')" id="match-heading">Matches</span>
            <router-link :to="{name: 'MatchCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-match">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.match.home.createLabel')">
                    Create a new Match
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <div class="row">
            <div class="col-sm-12">
                <form name="searchForm" class="form-inline" v-on:submit.prevent="search(currentSearch)">
                    <div class="input-group w-100 mt-3">
                        <input type="text" class="form-control" name="currentSearch" id="currentSearch"
                            v-bind:placeholder="$t('registatsApp.match.home.search')"
                            v-model="currentSearch" />
                        <button type="button" id="launch-search" class="btn btn-primary" v-on:click="search(currentSearch)">
                            <font-awesome-icon icon="search"></font-awesome-icon>
                        </button>
                        <button type="button" id="clear-search" class="btn btn-secondary" v-on:click="clear()"
                            v-if="currentSearch">
                            <font-awesome-icon icon="trash"></font-awesome-icon>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && matches && matches.length === 0">
            <span v-text="$t('registatsApp.match.home.notFound')">No matches found</span>
        </div>
        <div class="table-responsive" v-if="matches && matches.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('date')"><span v-text="$t('registatsApp.match.date')">Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('time')"><span v-text="$t('registatsApp.match.time')">Time</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.match.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.match.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('refree')"><span v-text="$t('registatsApp.match.refree')">Refree</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('liveStreamUrl')"><span v-text="$t('registatsApp.match.liveStreamUrl')">Live Stream Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('wheater')"><span v-text="$t('registatsApp.match.wheater')">Wheater</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('wind')"><span v-text="$t('registatsApp.match.wind')">Wind</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('analysis')"><span v-text="$t('registatsApp.match.analysis')">Analysis</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preMatchTalk')"><span v-text="$t('registatsApp.match.preMatchTalk')">Pre Match Talk</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('postMatchTalk')"><span v-text="$t('registatsApp.match.postMatchTalk')">Post Match Talk</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('venue.id')"><span v-text="$t('registatsApp.match.venue')">Venue</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('homeTeam.id')"><span v-text="$t('registatsApp.match.homeTeam')">Home Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('awayTeam.id')"><span v-text="$t('registatsApp.match.awayTeam')">Away Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.match.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="match in matches"
                    :key="match.id">
                    <td>
                        <router-link :to="{name: 'MatchView', params: {matchId: match.id}}">{{match.id}}</router-link>
                    </td>
                    <td>{{match.date | formatDate}}</td>
                    <td>{{match.time | formatDate}}</td>
                    <td>{{match.name}}</td>
                    <td>{{match.description}}</td>
                    <td>{{match.refree}}</td>
                    <td>{{match.liveStreamUrl}}</td>
                    <td>{{match.wheater}}</td>
                    <td>{{match.wind}}</td>
                    <td>{{match.analysis}}</td>
                    <td>{{match.preMatchTalk}}</td>
                    <td>{{match.postMatchTalk}}</td>
                    <td>
                        <div v-if="match.venue">
                            <router-link :to="{name: 'VenueView', params: {venueId: match.venue.id}}">{{match.venue.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="match.homeTeam">
                            <router-link :to="{name: 'TeamView', params: {homeTeamId: match.homeTeam.id}}">{{match.homeTeam.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="match.awayTeam">
                            <router-link :to="{name: 'TeamView', params: {awayTeamId: match.awayTeam.id}}">{{match.awayTeam.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="match.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: match.competition.id}}">{{match.competition.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MatchView', params: {matchId: match.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MatchEdit', params: {matchId: match.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(match)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="registatsApp.match.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-match-heading" v-bind:title="$t('registatsApp.match.delete.question')">Are you sure you want to delete this Match?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-match" v-text="$t('entity.action.delete')" v-on:click="removeMatch()">Delete</button>
            </div>
        </b-modal>
        <div v-show="matches && matches.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match.component.ts">
</script>
