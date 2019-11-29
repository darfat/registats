<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.matchCommentary.home.title')" id="match-commentary-heading">Match Commentaries</span>
            <router-link :to="{name: 'MatchCommentaryCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-match-commentary">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.matchCommentary.home.createLabel')">
                    Create a new Match Commentary
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
                            v-bind:placeholder="$t('registatsApp.matchCommentary.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && matchCommentaries && matchCommentaries.length === 0">
            <span v-text="$t('registatsApp.matchCommentary.home.notFound')">No matchCommentaries found</span>
        </div>
        <div class="table-responsive" v-if="matchCommentaries && matchCommentaries.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('title')"><span v-text="$t('registatsApp.matchCommentary.title')">Title</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.matchCommentary.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('commentaryType')"><span v-text="$t('registatsApp.matchCommentary.commentaryType')">Commentary Type</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('minute')"><span v-text="$t('registatsApp.matchCommentary.minute')">Minute</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('logDate')"><span v-text="$t('registatsApp.matchCommentary.logDate')">Log Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('playerStatistic.id')"><span v-text="$t('registatsApp.matchCommentary.playerStatistic')">Player Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('matchStatistic.id')"><span v-text="$t('registatsApp.matchCommentary.matchStatistic')">Match Statistic</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('team.id')"><span v-text="$t('registatsApp.matchCommentary.team')">Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.matchCommentary.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('match.id')"><span v-text="$t('registatsApp.matchCommentary.match')">Match</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="matchCommentary in matchCommentaries"
                    :key="matchCommentary.id">
                    <td>
                        <router-link :to="{name: 'MatchCommentaryView', params: {matchCommentaryId: matchCommentary.id}}">{{matchCommentary.id}}</router-link>
                    </td>
                    <td>{{matchCommentary.title}}</td>
                    <td>{{matchCommentary.description}}</td>
                    <td v-text="$t('registatsApp.CommentaryType.' + matchCommentary.commentaryType)">{{matchCommentary.commentaryType}}</td>
                    <td>{{matchCommentary.minute}}</td>
                    <td>{{matchCommentary.logDate | formatDate}}</td>
                    <td>
                        <div v-if="matchCommentary.playerStatistic">
                            <router-link :to="{name: 'PlayerStatisticItemView', params: {playerStatisticId: matchCommentary.playerStatistic.id}}">{{matchCommentary.playerStatistic.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchCommentary.matchStatistic">
                            <router-link :to="{name: 'MatchStatisticItemView', params: {matchStatisticId: matchCommentary.matchStatistic.id}}">{{matchCommentary.matchStatistic.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchCommentary.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: matchCommentary.team.id}}">{{matchCommentary.team.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchCommentary.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: matchCommentary.player.id}}">{{matchCommentary.player.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="matchCommentary.match">
                            <router-link :to="{name: 'MatchView', params: {matchId: matchCommentary.match.id}}">{{matchCommentary.match.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MatchCommentaryView', params: {matchCommentaryId: matchCommentary.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MatchCommentaryEdit', params: {matchCommentaryId: matchCommentary.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(matchCommentary)"
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
            <span slot="modal-title"><span id="registatsApp.matchCommentary.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-matchCommentary-heading" v-bind:title="$t('registatsApp.matchCommentary.delete.question')">Are you sure you want to delete this Match Commentary?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-matchCommentary" v-text="$t('entity.action.delete')" v-on:click="removeMatchCommentary()">Delete</button>
            </div>
        </b-modal>
        <div v-show="matchCommentaries && matchCommentaries.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-commentary.component.ts">
</script>
