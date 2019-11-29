<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competitionTeam.home.title')" id="competition-team-heading">Competition Teams</span>
            <router-link :to="{name: 'CompetitionTeamCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition-team">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competitionTeam.home.createLabel')">
                    Create a new Competition Team
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
                            v-bind:placeholder="$t('registatsApp.competitionTeam.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitionTeams && competitionTeams.length === 0">
            <span v-text="$t('registatsApp.competitionTeam.home.notFound')">No competitionTeams found</span>
        </div>
        <div class="table-responsive" v-if="competitionTeams && competitionTeams.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('registatsApp.competitionTeam.status')">Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.competitionTeam.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('registatsApp.competitionTeam.active')">Active</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.competitionTeam.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitionTeam in competitionTeams"
                    :key="competitionTeam.id">
                    <td>
                        <router-link :to="{name: 'CompetitionTeamView', params: {competitionTeamId: competitionTeam.id}}">{{competitionTeam.id}}</router-link>
                    </td>
                    <td>{{competitionTeam.status}}</td>
                    <td>{{competitionTeam.description}}</td>
                    <td>{{competitionTeam.active}}</td>
                    <td>
                        <div v-if="competitionTeam.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionTeam.competition.id}}">{{competitionTeam.competition.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionTeamView', params: {competitionTeamId: competitionTeam.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionTeamEdit', params: {competitionTeamId: competitionTeam.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitionTeam)"
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
            <span slot="modal-title"><span id="registatsApp.competitionTeam.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitionTeam-heading" v-bind:title="$t('registatsApp.competitionTeam.delete.question')">Are you sure you want to delete this Competition Team?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitionTeam" v-text="$t('entity.action.delete')" v-on:click="removeCompetitionTeam()">Delete</button>
            </div>
        </b-modal>
        <div v-show="competitionTeams && competitionTeams.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-team.component.ts">
</script>
