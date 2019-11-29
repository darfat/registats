<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.playerMatchStatistic.home.title')" id="player-match-statistic-heading">Player Match Statistics</span>
            <router-link :to="{name: 'PlayerMatchStatisticCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-player-match-statistic">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.playerMatchStatistic.home.createLabel')">
                    Create a new Player Match Statistic
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
                            v-bind:placeholder="$t('registatsApp.playerMatchStatistic.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && playerMatchStatistics && playerMatchStatistics.length === 0">
            <span v-text="$t('registatsApp.playerMatchStatistic.home.notFound')">No playerMatchStatistics found</span>
        </div>
        <div class="table-responsive" v-if="playerMatchStatistics && playerMatchStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.playerMatchStatistic.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('registatsApp.playerMatchStatistic.value')">Value</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueDouble')"><span v-text="$t('registatsApp.playerMatchStatistic.valueDouble')">Value Double</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueLong')"><span v-text="$t('registatsApp.playerMatchStatistic.valueLong')">Value Long</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueString')"><span v-text="$t('registatsApp.playerMatchStatistic.valueString')">Value String</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('matchLineup.id')"><span v-text="$t('registatsApp.playerMatchStatistic.matchLineup')">Match Lineup</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('statistic.id')"><span v-text="$t('registatsApp.playerMatchStatistic.statistic')">Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="playerMatchStatistic in playerMatchStatistics"
                    :key="playerMatchStatistic.id">
                    <td>
                        <router-link :to="{name: 'PlayerMatchStatisticView', params: {playerMatchStatisticId: playerMatchStatistic.id}}">{{playerMatchStatistic.id}}</router-link>
                    </td>
                    <td>{{playerMatchStatistic.description}}</td>
                    <td>{{playerMatchStatistic.value}}</td>
                    <td>{{playerMatchStatistic.valueDouble}}</td>
                    <td>{{playerMatchStatistic.valueLong}}</td>
                    <td>{{playerMatchStatistic.valueString}}</td>
                    <td>
                        <div v-if="playerMatchStatistic.matchLineup">
                            <router-link :to="{name: 'MatchLineupView', params: {matchLineupId: playerMatchStatistic.matchLineup.id}}">{{playerMatchStatistic.matchLineup.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="playerMatchStatistic.statistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {statisticId: playerMatchStatistic.statistic.id}}">{{playerMatchStatistic.statistic.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PlayerMatchStatisticView', params: {playerMatchStatisticId: playerMatchStatistic.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PlayerMatchStatisticEdit', params: {playerMatchStatisticId: playerMatchStatistic.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(playerMatchStatistic)"
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
            <span slot="modal-title"><span id="registatsApp.playerMatchStatistic.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-playerMatchStatistic-heading" v-bind:title="$t('registatsApp.playerMatchStatistic.delete.question')">Are you sure you want to delete this Player Match Statistic?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-playerMatchStatistic" v-text="$t('entity.action.delete')" v-on:click="removePlayerMatchStatistic()">Delete</button>
            </div>
        </b-modal>
        <div v-show="playerMatchStatistics && playerMatchStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-match-statistic.component.ts">
</script>
