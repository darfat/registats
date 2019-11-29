<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competitionName.home.title')" id="competition-name-heading">Competition Names</span>
            <router-link :to="{name: 'CompetitionNameCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition-name">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competitionName.home.createLabel')">
                    Create a new Competition Name
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
                            v-bind:placeholder="$t('registatsApp.competitionName.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitionNames && competitionNames.length === 0">
            <span v-text="$t('registatsApp.competitionName.home.notFound')">No competitionNames found</span>
        </div>
        <div class="table-responsive" v-if="competitionNames && competitionNames.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('slug')"><span v-text="$t('registatsApp.competitionName.slug')">Slug</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('city')"><span v-text="$t('registatsApp.competitionName.city')">City</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('region')"><span v-text="$t('registatsApp.competitionName.region')">Region</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nation')"><span v-text="$t('registatsApp.competitionName.nation')">Nation</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.competitionName.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.competitionName.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('registatsApp.competitionName.active')">Active</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitionName in competitionNames"
                    :key="competitionName.id">
                    <td>
                        <router-link :to="{name: 'CompetitionNameView', params: {competitionNameId: competitionName.id}}">{{competitionName.id}}</router-link>
                    </td>
                    <td>{{competitionName.slug}}</td>
                    <td>{{competitionName.city}}</td>
                    <td>{{competitionName.region}}</td>
                    <td>{{competitionName.nation}}</td>
                    <td>{{competitionName.name}}</td>
                    <td>{{competitionName.description}}</td>
                    <td>{{competitionName.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionNameView', params: {competitionNameId: competitionName.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionNameEdit', params: {competitionNameId: competitionName.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitionName)"
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
            <span slot="modal-title"><span id="registatsApp.competitionName.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitionName-heading" v-bind:title="$t('registatsApp.competitionName.delete.question')">Are you sure you want to delete this Competition Name?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitionName" v-text="$t('entity.action.delete')" v-on:click="removeCompetitionName()">Delete</button>
            </div>
        </b-modal>
        <div v-show="competitionNames && competitionNames.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-name.component.ts">
</script>
