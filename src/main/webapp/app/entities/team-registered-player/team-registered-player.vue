<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.teamRegisteredPlayer.home.title')" id="team-registered-player-heading">Team Registered Players</span>
            <router-link :to="{name: 'TeamRegisteredPlayerCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-team-registered-player">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.teamRegisteredPlayer.home.createLabel')">
                    Create a new Team Registered Player
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
                            v-bind:placeholder="$t('registatsApp.teamRegisteredPlayer.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && teamRegisteredPlayers && teamRegisteredPlayers.length === 0">
            <span v-text="$t('registatsApp.teamRegisteredPlayer.home.notFound')">No teamRegisteredPlayers found</span>
        </div>
        <div class="table-responsive" v-if="teamRegisteredPlayers && teamRegisteredPlayers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('number')"><span v-text="$t('registatsApp.teamRegisteredPlayer.number')">Number</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('registatsApp.teamRegisteredPlayer.status')">Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('registatsApp.teamRegisteredPlayer.active')">Active</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('team.id')"><span v-text="$t('registatsApp.teamRegisteredPlayer.team')">Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.teamRegisteredPlayer.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.teamRegisteredPlayer.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="teamRegisteredPlayer in teamRegisteredPlayers"
                    :key="teamRegisteredPlayer.id">
                    <td>
                        <router-link :to="{name: 'TeamRegisteredPlayerView', params: {teamRegisteredPlayerId: teamRegisteredPlayer.id}}">{{teamRegisteredPlayer.id}}</router-link>
                    </td>
                    <td>{{teamRegisteredPlayer.number}}</td>
                    <td>{{teamRegisteredPlayer.status}}</td>
                    <td>{{teamRegisteredPlayer.active}}</td>
                    <td>
                        <div v-if="teamRegisteredPlayer.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: teamRegisteredPlayer.team.id}}">{{teamRegisteredPlayer.team.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="teamRegisteredPlayer.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: teamRegisteredPlayer.player.id}}">{{teamRegisteredPlayer.player.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="teamRegisteredPlayer.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: teamRegisteredPlayer.competition.id}}">{{teamRegisteredPlayer.competition.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TeamRegisteredPlayerView', params: {teamRegisteredPlayerId: teamRegisteredPlayer.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TeamRegisteredPlayerEdit', params: {teamRegisteredPlayerId: teamRegisteredPlayer.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(teamRegisteredPlayer)"
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
            <span slot="modal-title"><span id="registatsApp.teamRegisteredPlayer.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-teamRegisteredPlayer-heading" v-bind:title="$t('registatsApp.teamRegisteredPlayer.delete.question')">Are you sure you want to delete this Team Registered Player?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-teamRegisteredPlayer" v-text="$t('entity.action.delete')" v-on:click="removeTeamRegisteredPlayer()">Delete</button>
            </div>
        </b-modal>
        <div v-show="teamRegisteredPlayers && teamRegisteredPlayers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./team-registered-player.component.ts">
</script>
