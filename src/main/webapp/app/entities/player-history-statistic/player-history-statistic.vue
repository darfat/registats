<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.playerHistoryStatistic.home.title')" id="player-history-statistic-heading">Player History Statistics</span>
            <router-link :to="{name: 'PlayerHistoryStatisticCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-player-history-statistic">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.playerHistoryStatistic.home.createLabel')">
                    Create a new Player History Statistic
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
                            v-bind:placeholder="$t('registatsApp.playerHistoryStatistic.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && playerHistoryStatistics && playerHistoryStatistics.length === 0">
            <span v-text="$t('registatsApp.playerHistoryStatistic.home.notFound')">No playerHistoryStatistics found</span>
        </div>
        <div class="table-responsive" v-if="playerHistoryStatistics && playerHistoryStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('registatsApp.playerHistoryStatistic.value')">Value</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.playerHistoryStatistic.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('playerHistory.id')"><span v-text="$t('registatsApp.playerHistoryStatistic.playerHistory')">Player History</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('statistic.id')"><span v-text="$t('registatsApp.playerHistoryStatistic.statistic')">Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="playerHistoryStatistic in playerHistoryStatistics"
                    :key="playerHistoryStatistic.id">
                    <td>
                        <router-link :to="{name: 'PlayerHistoryStatisticView', params: {playerHistoryStatisticId: playerHistoryStatistic.id}}">{{playerHistoryStatistic.id}}</router-link>
                    </td>
                    <td>{{playerHistoryStatistic.value}}</td>
                    <td>{{playerHistoryStatistic.description}}</td>
                    <td>
                        <div v-if="playerHistoryStatistic.playerHistory">
                            <router-link :to="{name: 'PlayerHistoryView', params: {playerHistoryId: playerHistoryStatistic.playerHistory.id}}">{{playerHistoryStatistic.playerHistory.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="playerHistoryStatistic.statistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {statisticId: playerHistoryStatistic.statistic.id}}">{{playerHistoryStatistic.statistic.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PlayerHistoryStatisticView', params: {playerHistoryStatisticId: playerHistoryStatistic.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PlayerHistoryStatisticEdit', params: {playerHistoryStatisticId: playerHistoryStatistic.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(playerHistoryStatistic)"
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
            <span slot="modal-title"><span id="registatsApp.playerHistoryStatistic.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-playerHistoryStatistic-heading" v-bind:title="$t('registatsApp.playerHistoryStatistic.delete.question')">Are you sure you want to delete this Player History Statistic?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-playerHistoryStatistic" v-text="$t('entity.action.delete')" v-on:click="removePlayerHistoryStatistic()">Delete</button>
            </div>
        </b-modal>
        <div v-show="playerHistoryStatistics && playerHistoryStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-history-statistic.component.ts">
</script>
