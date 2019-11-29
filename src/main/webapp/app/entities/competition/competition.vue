<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competition.home.title')" id="competition-heading">Competitions</span>
            <router-link :to="{name: 'CompetitionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competition.home.createLabel')">
                    Create a new Competition
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
                            v-bind:placeholder="$t('registatsApp.competition.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitions && competitions.length === 0">
            <span v-text="$t('registatsApp.competition.home.notFound')">No competitions found</span>
        </div>
        <div class="table-responsive" v-if="competitions && competitions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('season')"><span v-text="$t('registatsApp.competition.season')">Season</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('slug')"><span v-text="$t('registatsApp.competition.slug')">Slug</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('startDate')"><span v-text="$t('registatsApp.competition.startDate')">Start Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('endDate')"><span v-text="$t('registatsApp.competition.endDate')">End Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competitionFormat')"><span v-text="$t('registatsApp.competition.competitionFormat')">Competition Format</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competitionName.id')"><span v-text="$t('registatsApp.competition.competitionName')">Competition Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competition in competitions"
                    :key="competition.id">
                    <td>
                        <router-link :to="{name: 'CompetitionView', params: {competitionId: competition.id}}">{{competition.id}}</router-link>
                    </td>
                    <td>{{competition.season}}</td>
                    <td>{{competition.slug}}</td>
                    <td>{{competition.startDate | formatDate}}</td>
                    <td>{{competition.endDate | formatDate}}</td>
                    <td v-text="$t('registatsApp.CompetitionFormat.' + competition.competitionFormat)">{{competition.competitionFormat}}</td>
                    <td>
                        <div v-if="competition.competitionName">
                            <router-link :to="{name: 'CompetitionNameView', params: {competitionNameId: competition.competitionName.id}}">{{competition.competitionName.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competition.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionEdit', params: {competitionId: competition.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competition)"
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
            <span slot="modal-title"><span id="registatsApp.competition.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competition-heading" v-bind:title="$t('registatsApp.competition.delete.question')">Are you sure you want to delete this Competition?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competition" v-text="$t('entity.action.delete')" v-on:click="removeCompetition()">Delete</button>
            </div>
        </b-modal>
        <div v-show="competitions && competitions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition.component.ts">
</script>
