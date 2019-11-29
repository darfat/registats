<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.globalPlayerStatistic.home.title')" id="global-player-statistic-heading">Global Player Statistics</span>
            <router-link :to="{name: 'GlobalPlayerStatisticCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-global-player-statistic">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.globalPlayerStatistic.home.createLabel')">
                    Create a new Global Player Statistic
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
                            v-bind:placeholder="$t('registatsApp.globalPlayerStatistic.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && globalPlayerStatistics && globalPlayerStatistics.length === 0">
            <span v-text="$t('registatsApp.globalPlayerStatistic.home.notFound')">No globalPlayerStatistics found</span>
        </div>
        <div class="table-responsive" v-if="globalPlayerStatistics && globalPlayerStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueDouble')"><span v-text="$t('registatsApp.globalPlayerStatistic.valueDouble')">Value Double</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueLong')"><span v-text="$t('registatsApp.globalPlayerStatistic.valueLong')">Value Long</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueString')"><span v-text="$t('registatsApp.globalPlayerStatistic.valueString')">Value String</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.globalPlayerStatistic.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.globalPlayerStatistic.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.globalPlayerStatistic.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="globalPlayerStatistic in globalPlayerStatistics"
                    :key="globalPlayerStatistic.id">
                    <td>
                        <router-link :to="{name: 'GlobalPlayerStatisticView', params: {globalPlayerStatisticId: globalPlayerStatistic.id}}">{{globalPlayerStatistic.id}}</router-link>
                    </td>
                    <td>{{globalPlayerStatistic.valueDouble}}</td>
                    <td>{{globalPlayerStatistic.valueLong}}</td>
                    <td>{{globalPlayerStatistic.valueString}}</td>
                    <td>{{globalPlayerStatistic.description}}</td>
                    <td>{{globalPlayerStatistic.name}}</td>
                    <td>
                        <div v-if="globalPlayerStatistic.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: globalPlayerStatistic.player.id}}">{{globalPlayerStatistic.player.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'GlobalPlayerStatisticView', params: {globalPlayerStatisticId: globalPlayerStatistic.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'GlobalPlayerStatisticEdit', params: {globalPlayerStatisticId: globalPlayerStatistic.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(globalPlayerStatistic)"
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
            <span slot="modal-title"><span id="registatsApp.globalPlayerStatistic.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-globalPlayerStatistic-heading" v-bind:title="$t('registatsApp.globalPlayerStatistic.delete.question')">Are you sure you want to delete this Global Player Statistic?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-globalPlayerStatistic" v-text="$t('entity.action.delete')" v-on:click="removeGlobalPlayerStatistic()">Delete</button>
            </div>
        </b-modal>
        <div v-show="globalPlayerStatistics && globalPlayerStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./global-player-statistic.component.ts">
</script>
