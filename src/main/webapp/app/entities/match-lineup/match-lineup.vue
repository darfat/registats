<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.matchLineup.home.title')" id="match-lineup-heading">Match Lineups</span>
            <router-link :to="{name: 'MatchLineupCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-match-lineup">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.matchLineup.home.createLabel')">
                    Create a new Match Lineup
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
                            v-bind:placeholder="$t('registatsApp.matchLineup.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && matchLineups && matchLineups.length === 0">
            <span v-text="$t('registatsApp.matchLineup.home.notFound')">No matchLineups found</span>
        </div>
        <div class="table-responsive" v-if="matchLineups && matchLineups.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('number')"><span v-text="$t('registatsApp.matchLineup.number')">Number</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('role')"><span v-text="$t('registatsApp.matchLineup.role')">Role</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('firstHalfPlay')"><span v-text="$t('registatsApp.matchLineup.firstHalfPlay')">First Half Play</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('secondHalfPlay')"><span v-text="$t('registatsApp.matchLineup.secondHalfPlay')">Second Half Play</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('registatsApp.matchLineup.status')">Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('minuteIn')"><span v-text="$t('registatsApp.matchLineup.minuteIn')">Minute In</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('minoteOut')"><span v-text="$t('registatsApp.matchLineup.minoteOut')">Minote Out</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('position.id')"><span v-text="$t('registatsApp.matchLineup.position')">Position</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.matchLineup.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('matchHomeInfo.id')"><span v-text="$t('registatsApp.matchLineup.matchHomeInfo')">Match Home Info</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('matchAwayInfo.id')"><span v-text="$t('registatsApp.matchLineup.matchAwayInfo')">Match Away Info</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="matchLineup in matchLineups"
                    :key="matchLineup.id">
                    <td>
                        <router-link :to="{name: 'MatchLineupView', params: {matchLineupId: matchLineup.id}}">{{matchLineup.id}}</router-link>
                    </td>
                    <td>{{matchLineup.number}}</td>
                    <td>{{matchLineup.role}}</td>
                    <td>{{matchLineup.firstHalfPlay}}</td>
                    <td>{{matchLineup.secondHalfPlay}}</td>
                    <td>{{matchLineup.status}}</td>
                    <td>{{matchLineup.minuteIn}}</td>
                    <td>{{matchLineup.minoteOut}}</td>
                    <td>
                        <div v-if="matchLineup.position">
                            <router-link :to="{name: 'PositionView', params: {positionId: matchLineup.position.id}}">{{matchLineup.position.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchLineup.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: matchLineup.player.id}}">{{matchLineup.player.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchLineup.matchHomeInfo">
                            <router-link :to="{name: 'MatchHomeInfoView', params: {matchHomeInfoId: matchLineup.matchHomeInfo.id}}">{{matchLineup.matchHomeInfo.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchLineup.matchAwayInfo">
                            <router-link :to="{name: 'MatchAwayInfoView', params: {matchAwayInfoId: matchLineup.matchAwayInfo.id}}">{{matchLineup.matchAwayInfo.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MatchLineupView', params: {matchLineupId: matchLineup.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MatchLineupEdit', params: {matchLineupId: matchLineup.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(matchLineup)"
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
            <span slot="modal-title"><span id="registatsApp.matchLineup.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-matchLineup-heading" v-bind:title="$t('registatsApp.matchLineup.delete.question')">Are you sure you want to delete this Match Lineup?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-matchLineup" v-text="$t('entity.action.delete')" v-on:click="removeMatchLineup()">Delete</button>
            </div>
        </b-modal>
        <div v-show="matchLineups && matchLineups.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-lineup.component.ts">
</script>
