<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.player.home.title')" id="player-heading">Players</span>
            <router-link :to="{name: 'PlayerCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-player">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.player.home.createLabel')">
                    Create a new Player
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
                            v-bind:placeholder="$t('registatsApp.player.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && players && players.length === 0">
            <span v-text="$t('registatsApp.player.home.notFound')">No players found</span>
        </div>
        <div class="table-responsive" v-if="players && players.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('firstName')"><span v-text="$t('registatsApp.player.firstName')">First Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('lastName')"><span v-text="$t('registatsApp.player.lastName')">Last Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fullName')"><span v-text="$t('registatsApp.player.fullName')">Full Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('email')"><span v-text="$t('registatsApp.player.email')">Email</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('phoneNumber')"><span v-text="$t('registatsApp.player.phoneNumber')">Phone Number</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('instagram')"><span v-text="$t('registatsApp.player.instagram')">Instagram</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('photo')"><span v-text="$t('registatsApp.player.photo')">Photo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('idCard')"><span v-text="$t('registatsApp.player.idCard')">Id Card</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('address')"><span v-text="$t('registatsApp.player.address')">Address</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('birthPlace')"><span v-text="$t('registatsApp.player.birthPlace')">Birth Place</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('birthDate')"><span v-text="$t('registatsApp.player.birthDate')">Birth Date</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('position.id')"><span v-text="$t('registatsApp.player.position')">Position</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="player in players"
                    :key="player.id">
                    <td>
                        <router-link :to="{name: 'PlayerView', params: {playerId: player.id}}">{{player.id}}</router-link>
                    </td>
                    <td>{{player.firstName}}</td>
                    <td>{{player.lastName}}</td>
                    <td>{{player.fullName}}</td>
                    <td>{{player.email}}</td>
                    <td>{{player.phoneNumber}}</td>
                    <td>{{player.instagram}}</td>
                    <td>{{player.photo}}</td>
                    <td>{{player.idCard}}</td>
                    <td>{{player.address}}</td>
                    <td>{{player.birthPlace}}</td>
                    <td>{{player.birthDate | formatDate}}</td>
                    <td>
                        <div v-if="player.position">
                            <router-link :to="{name: 'PositionView', params: {positionId: player.position.id}}">{{player.position.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PlayerView', params: {playerId: player.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PlayerEdit', params: {playerId: player.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(player)"
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
            <span slot="modal-title"><span id="registatsApp.player.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-player-heading" v-bind:title="$t('registatsApp.player.delete.question')">Are you sure you want to delete this Player?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-player" v-text="$t('entity.action.delete')" v-on:click="removePlayer()">Delete</button>
            </div>
        </b-modal>
        <div v-show="players && players.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player.component.ts">
</script>
