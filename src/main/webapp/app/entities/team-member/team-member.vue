<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.teamMember.home.title')" id="team-member-heading">Team Members</span>
            <router-link :to="{name: 'TeamMemberCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-team-member">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.teamMember.home.createLabel')">
                    Create a new Team Member
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
                            v-bind:placeholder="$t('registatsApp.teamMember.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && teamMembers && teamMembers.length === 0">
            <span v-text="$t('registatsApp.teamMember.home.notFound')">No teamMembers found</span>
        </div>
        <div class="table-responsive" v-if="teamMembers && teamMembers.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('number')"><span v-text="$t('registatsApp.teamMember.number')">Number</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('joinDate')"><span v-text="$t('registatsApp.teamMember.joinDate')">Join Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('registatsApp.teamMember.status')">Status</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('registatsApp.teamMember.active')">Active</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('team.id')"><span v-text="$t('registatsApp.teamMember.team')">Team</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('player.id')"><span v-text="$t('registatsApp.teamMember.player')">Player</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="teamMember in teamMembers"
                    :key="teamMember.id">
                    <td>
                        <router-link :to="{name: 'TeamMemberView', params: {teamMemberId: teamMember.id}}">{{teamMember.id}}</router-link>
                    </td>
                    <td>{{teamMember.number}}</td>
                    <td>{{teamMember.joinDate | formatDate}}</td>
                    <td>{{teamMember.status}}</td>
                    <td>{{teamMember.active}}</td>
                    <td>
                        <div v-if="teamMember.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: teamMember.team.id}}">{{teamMember.team.id}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="teamMember.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: teamMember.player.id}}">{{teamMember.player.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'TeamMemberView', params: {teamMemberId: teamMember.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'TeamMemberEdit', params: {teamMemberId: teamMember.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(teamMember)"
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
            <span slot="modal-title"><span id="registatsApp.teamMember.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-teamMember-heading" v-bind:title="$t('registatsApp.teamMember.delete.question')">Are you sure you want to delete this Team Member?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-teamMember" v-text="$t('entity.action.delete')" v-on:click="removeTeamMember()">Delete</button>
            </div>
        </b-modal>
        <div v-show="teamMembers && teamMembers.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./team-member.component.ts">
</script>
