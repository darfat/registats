<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competitionTeamStatistic.home.title')" id="competition-team-statistic-heading">Competition Team Statistics</span>
            <router-link :to="{name: 'CompetitionTeamStatisticCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition-team-statistic">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competitionTeamStatistic.home.createLabel')">
                    Create a new Competition Team Statistic
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
                            v-bind:placeholder="$t('registatsApp.competitionTeamStatistic.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitionTeamStatistics && competitionTeamStatistics.length === 0">
            <span v-text="$t('registatsApp.competitionTeamStatistic.home.notFound')">No competitionTeamStatistics found</span>
        </div>
        <div class="table-responsive" v-if="competitionTeamStatistics && competitionTeamStatistics.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueDouble')"><span v-text="$t('registatsApp.competitionTeamStatistic.valueDouble')">Value Double</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueLong')"><span v-text="$t('registatsApp.competitionTeamStatistic.valueLong')">Value Long</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valueString')"><span v-text="$t('registatsApp.competitionTeamStatistic.valueString')">Value String</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.competitionTeamStatistic.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.competitionTeamStatistic.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.competitionTeamStatistic.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('statistic.id')"><span v-text="$t('registatsApp.competitionTeamStatistic.statistic')">Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('team.id')"><span v-text="$t('registatsApp.competitionTeamStatistic.team')">Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitionTeamStatistic in competitionTeamStatistics"
                    :key="competitionTeamStatistic.id">
                    <td>
                        <router-link :to="{name: 'CompetitionTeamStatisticView', params: {competitionTeamStatisticId: competitionTeamStatistic.id}}">{{competitionTeamStatistic.id}}</router-link>
                    </td>
                    <td>{{competitionTeamStatistic.valueDouble}}</td>
                    <td>{{competitionTeamStatistic.valueLong}}</td>
                    <td>{{competitionTeamStatistic.valueString}}</td>
                    <td>{{competitionTeamStatistic.description}}</td>
                    <td>{{competitionTeamStatistic.name}}</td>
                    <td>
                        <div v-if="competitionTeamStatistic.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionTeamStatistic.competition.id}}">{{competitionTeamStatistic.competition.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitionTeamStatistic.statistic">
                            <router-link :to="{name: 'CompetitionStatisticItemView', params: {statisticId: competitionTeamStatistic.statistic.id}}">{{competitionTeamStatistic.statistic.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="competitionTeamStatistic.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: competitionTeamStatistic.team.id}}">{{competitionTeamStatistic.team.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionTeamStatisticView', params: {competitionTeamStatisticId: competitionTeamStatistic.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionTeamStatisticEdit', params: {competitionTeamStatisticId: competitionTeamStatistic.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitionTeamStatistic)"
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
            <span slot="modal-title"><span id="registatsApp.competitionTeamStatistic.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitionTeamStatistic-heading" v-bind:title="$t('registatsApp.competitionTeamStatistic.delete.question')">Are you sure you want to delete this Competition Team Statistic?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitionTeamStatistic" v-text="$t('entity.action.delete')" v-on:click="removeCompetitionTeamStatistic()">Delete</button>
            </div>
        </b-modal>
        <div v-show="competitionTeamStatistics && competitionTeamStatistics.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-team-statistic.component.ts">
</script>
