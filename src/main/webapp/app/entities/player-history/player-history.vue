<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.playerHistory.home.title')" id="player-history-heading">Player Histories</span>
            <router-link :to="{name: 'PlayerHistoryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-player-history">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.playerHistory.home.createLabel')">
                    Create a new Player History
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
                            v-bind:placeholder="$t('registatsApp.playerHistory.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && playerHistories && playerHistories.length === 0">
            <span v-text="$t('registatsApp.playerHistory.home.notFound')">No playerHistories found</span>
        </div>
        <div class="table-responsive" v-if="playerHistories && playerHistories.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('startDate')"><span v-text="$t('registatsApp.playerHistory.startDate')">Start Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('endDate')"><span v-text="$t('registatsApp.playerHistory.endDate')">End Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('appearance')"><span v-text="$t('registatsApp.playerHistory.appearance')">Appearance</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('minutePlayed')"><span v-text="$t('registatsApp.playerHistory.minutePlayed')">Minute Played</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('goals')"><span v-text="$t('registatsApp.playerHistory.goals')">Goals</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('assists')"><span v-text="$t('registatsApp.playerHistory.assists')">Assists</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('averageRating')"><span v-text="$t('registatsApp.playerHistory.averageRating')">Average Rating</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.playerHistory.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="playerHistory in playerHistories"
                    :key="playerHistory.id">
                    <td>
                        <router-link :to="{name: 'PlayerHistoryView', params: {playerHistoryId: playerHistory.id}}">{{playerHistory.id}}</router-link>
                    </td>
                    <td>{{playerHistory.startDate | formatDate}}</td>
                    <td>{{playerHistory.endDate | formatDate}}</td>
                    <td>{{playerHistory.appearance}}</td>
                    <td>{{playerHistory.minutePlayed}}</td>
                    <td>{{playerHistory.goals}}</td>
                    <td>{{playerHistory.assists}}</td>
                    <td>{{playerHistory.averageRating}}</td>
                    <td>
                        <div v-if="playerHistory.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: playerHistory.player.id}}">{{playerHistory.player.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PlayerHistoryView', params: {playerHistoryId: playerHistory.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PlayerHistoryEdit', params: {playerHistoryId: playerHistory.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(playerHistory)"
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
            <span slot="modal-title"><span id="registatsApp.playerHistory.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-playerHistory-heading" v-bind:title="$t('registatsApp.playerHistory.delete.question')">Are you sure you want to delete this Player History?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-playerHistory" v-text="$t('entity.action.delete')" v-on:click="removePlayerHistory()">Delete</button>
            </div>
        </b-modal>
        <div v-show="playerHistories && playerHistories.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-history.component.ts">
</script>
