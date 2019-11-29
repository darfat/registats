<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.competitionStanding.home.title')" id="competition-standing-heading">Competition Standings</span>
            <router-link :to="{name: 'CompetitionStandingCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-competition-standing">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.competitionStanding.home.createLabel')">
                    Create a new Competition Standing
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
                            v-bind:placeholder="$t('registatsApp.competitionStanding.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && competitionStandings && competitionStandings.length === 0">
            <span v-text="$t('registatsApp.competitionStanding.home.notFound')">No competitionStandings found</span>
        </div>
        <div class="table-responsive" v-if="competitionStandings && competitionStandings.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('position')"><span v-text="$t('registatsApp.competitionStanding.position')">Position</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('played')"><span v-text="$t('registatsApp.competitionStanding.played')">Played</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('win')"><span v-text="$t('registatsApp.competitionStanding.win')">Win</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('draw')"><span v-text="$t('registatsApp.competitionStanding.draw')">Draw</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('lose')"><span v-text="$t('registatsApp.competitionStanding.lose')">Lose</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('goal')"><span v-text="$t('registatsApp.competitionStanding.goal')">Goal</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('goalAgaints')"><span v-text="$t('registatsApp.competitionStanding.goalAgaints')">Goal Againts</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('point')"><span v-text="$t('registatsApp.competitionStanding.point')">Point</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('minusPoint')"><span v-text="$t('registatsApp.competitionStanding.minusPoint')">Minus Point</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competition.id')"><span v-text="$t('registatsApp.competitionStanding.competition')">Competition</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="competitionStanding in competitionStandings"
                    :key="competitionStanding.id">
                    <td>
                        <router-link :to="{name: 'CompetitionStandingView', params: {competitionStandingId: competitionStanding.id}}">{{competitionStanding.id}}</router-link>
                    </td>
                    <td>{{competitionStanding.position}}</td>
                    <td>{{competitionStanding.played}}</td>
                    <td>{{competitionStanding.win}}</td>
                    <td>{{competitionStanding.draw}}</td>
                    <td>{{competitionStanding.lose}}</td>
                    <td>{{competitionStanding.goal}}</td>
                    <td>{{competitionStanding.goalAgaints}}</td>
                    <td>{{competitionStanding.point}}</td>
                    <td>{{competitionStanding.minusPoint}}</td>
                    <td>
                        <div v-if="competitionStanding.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionStanding.competition.id}}">{{competitionStanding.competition.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CompetitionStandingView', params: {competitionStandingId: competitionStanding.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CompetitionStandingEdit', params: {competitionStandingId: competitionStanding.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(competitionStanding)"
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
            <span slot="modal-title"><span id="registatsApp.competitionStanding.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-competitionStanding-heading" v-bind:title="$t('registatsApp.competitionStanding.delete.question')">Are you sure you want to delete this Competition Standing?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-competitionStanding" v-text="$t('entity.action.delete')" v-on:click="removeCompetitionStanding()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./competition-standing.component.ts">
</script>
