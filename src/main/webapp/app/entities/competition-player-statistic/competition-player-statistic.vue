<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competitionPlayerStatistic.home.title')" id="competition-player-statistic-heading">Competition Player Statistics</span>
            <router-link :to="{name: 'CompetitionPlayerStatisticCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition-player-statistic">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competitionPlayerStatistic.home.createLabel')">
                    Create a new Competition Player Statistic
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
                            v-bind:placeholder="$t('registatsApp.competitionPlayerStatistic.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitionPlayerStatistics && competitionPlayerStatistics.length === 0">
            <span v-text="$t('registatsApp.competitionPlayerStatistic.home.notFound')">No competitionPlayerStatistics found</span>
        </div>
        <div class="table-responsive" v-if="competitionPlayerStatistics && competitionPlayerStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueDouble')"><span v-text="$t('registatsApp.competitionPlayerStatistic.valueDouble')">Value Double</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueLong')"><span v-text="$t('registatsApp.competitionPlayerStatistic.valueLong')">Value Long</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueString')"><span v-text="$t('registatsApp.competitionPlayerStatistic.valueString')">Value String</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.competitionPlayerStatistic.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.competitionPlayerStatistic.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.competitionPlayerStatistic.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('statistic.id')"><span v-text="$t('registatsApp.competitionPlayerStatistic.statistic')">Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.competitionPlayerStatistic.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitionPlayerStatistic in competitionPlayerStatistics"
                    :key="competitionPlayerStatistic.id">
                    <td>
                        <router-link :to="{name: 'CompetitionPlayerStatisticView', params: {competitionPlayerStatisticId: competitionPlayerStatistic.id}}">{{competitionPlayerStatistic.id}}</router-link>
                    </td>
                    <td>{{competitionPlayerStatistic.valueDouble}}</td>
                    <td>{{competitionPlayerStatistic.valueLong}}</td>
                    <td>{{competitionPlayerStatistic.valueString}}</td>
                    <td>{{competitionPlayerStatistic.description}}</td>
                    <td>{{competitionPlayerStatistic.name}}</td>
                    <td>
                        <div v-if="competitionPlayerStatistic.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionPlayerStatistic.competition.id}}">{{competitionPlayerStatistic.competition.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitionPlayerStatistic.statistic">
                            <router-link :to="{name: 'CompetitionStatisticItemView', params: {statisticId: competitionPlayerStatistic.statistic.id}}">{{competitionPlayerStatistic.statistic.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitionPlayerStatistic.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: competitionPlayerStatistic.player.id}}">{{competitionPlayerStatistic.player.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionPlayerStatisticView', params: {competitionPlayerStatisticId: competitionPlayerStatistic.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionPlayerStatisticEdit', params: {competitionPlayerStatisticId: competitionPlayerStatistic.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitionPlayerStatistic)"
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
            <span slot="modal-title"><span id="registatsApp.competitionPlayerStatistic.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitionPlayerStatistic-heading" v-bind:title="$t('registatsApp.competitionPlayerStatistic.delete.question')">Are you sure you want to delete this Competition Player Statistic?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitionPlayerStatistic" v-text="$t('entity.action.delete')" v-on:click="removeCompetitionPlayerStatistic()">Delete</button>
            </div>
        </b-modal>
        <div v-show="competitionPlayerStatistics && competitionPlayerStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-player-statistic.component.ts">
</script>
