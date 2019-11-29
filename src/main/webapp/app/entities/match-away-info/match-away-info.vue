<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.matchAwayInfo.home.title')" id="match-away-info-heading">Match Away Infos</span>
            <router-link :to="{name: 'MatchAwayInfoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-match-away-info">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.matchAwayInfo.home.createLabel')">
                    Create a new Match Away Info
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
                            v-bind:placeholder="$t('registatsApp.matchAwayInfo.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && matchAwayInfos && matchAwayInfos.length === 0">
            <span v-text="$t('registatsApp.matchAwayInfo.home.notFound')">No matchAwayInfos found</span>
        </div>
        <div class="table-responsive" v-if="matchAwayInfos && matchAwayInfos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.matchAwayInfo.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('formation')"><span v-text="$t('registatsApp.matchAwayInfo.formation')">Formation</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('analysis')"><span v-text="$t('registatsApp.matchAwayInfo.analysis')">Analysis</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('preMatchTalk')"><span v-text="$t('registatsApp.matchAwayInfo.preMatchTalk')">Pre Match Talk</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('postMatchTalk')"><span v-text="$t('registatsApp.matchAwayInfo.postMatchTalk')">Post Match Talk</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('match.id')"><span v-text="$t('registatsApp.matchAwayInfo.match')">Match</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('team.id')"><span v-text="$t('registatsApp.matchAwayInfo.team')">Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="matchAwayInfo in matchAwayInfos"
                    :key="matchAwayInfo.id">
                    <td>
                        <router-link :to="{name: 'MatchAwayInfoView', params: {matchAwayInfoId: matchAwayInfo.id}}">{{matchAwayInfo.id}}</router-link>
                    </td>
                    <td>{{matchAwayInfo.description}}</td>
                    <td v-text="$t('registatsApp.Formation.' + matchAwayInfo.formation)">{{matchAwayInfo.formation}}</td>
                    <td>{{matchAwayInfo.analysis}}</td>
                    <td>{{matchAwayInfo.preMatchTalk}}</td>
                    <td>{{matchAwayInfo.postMatchTalk}}</td>
                    <td>
                        <div v-if="matchAwayInfo.match">
                            <router-link :to="{name: 'MatchView', params: {matchId: matchAwayInfo.match.id}}">{{matchAwayInfo.match.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchAwayInfo.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchAwayInfo.team.id}}">{{matchAwayInfo.team.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MatchAwayInfoView', params: {matchAwayInfoId: matchAwayInfo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MatchAwayInfoEdit', params: {matchAwayInfoId: matchAwayInfo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(matchAwayInfo)"
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
            <span slot="modal-title"><span id="registatsApp.matchAwayInfo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-matchAwayInfo-heading" v-bind:title="$t('registatsApp.matchAwayInfo.delete.question')">Are you sure you want to delete this Match Away Info?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-matchAwayInfo" v-text="$t('entity.action.delete')" v-on:click="removeMatchAwayInfo()">Delete</button>
            </div>
        </b-modal>
        <div v-show="matchAwayInfos && matchAwayInfos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-away-info.component.ts">
</script>
