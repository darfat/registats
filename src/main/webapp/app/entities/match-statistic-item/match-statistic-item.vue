<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('registatsApp.matchStatisticItem.home.title')" id="match-statistic-item-heading">Match Statistic Items</span>
            <router-link :to="{name: 'MatchStatisticItemCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-match-statistic-item">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('registatsApp.matchStatisticItem.home.createLabel')">
                    Create a new Match Statistic Item
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
                            v-bind:placeholder="$t('registatsApp.matchStatisticItem.home.search')"
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
        <div class="alert alert-warning" v-if="!isFetching && matchStatisticItems && matchStatisticItems.length === 0">
            <span v-text="$t('registatsApp.matchStatisticItem.home.notFound')">No matchStatisticItems found</span>
        </div>
        <div class="table-responsive" v-if="matchStatisticItems && matchStatisticItems.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('registatsApp.matchStatisticItem.name')">Name</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('registatsApp.matchStatisticItem.description')">Description</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('active')"><span v-text="$t('registatsApp.matchStatisticItem.active')">Active</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="matchStatisticItem in matchStatisticItems"
                    :key="matchStatisticItem.id">
                    <td>
                        <router-link :to="{name: 'MatchStatisticItemView', params: {matchStatisticItemId: matchStatisticItem.id}}">{{matchStatisticItem.id}}</router-link>
                    </td>
                    <td>{{matchStatisticItem.name}}</td>
                    <td>{{matchStatisticItem.description}}</td>
                    <td>{{matchStatisticItem.active}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MatchStatisticItemView', params: {matchStatisticItemId: matchStatisticItem.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MatchStatisticItemEdit', params: {matchStatisticItemId: matchStatisticItem.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(matchStatisticItem)"
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
            <span slot="modal-title"><span id="registatsApp.matchStatisticItem.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-matchStatisticItem-heading" v-bind:title="$t('registatsApp.matchStatisticItem.delete.question')">Are you sure you want to delete this Match Statistic Item?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-matchStatisticItem" v-text="$t('entity.action.delete')" v-on:click="removeMatchStatisticItem()">Delete</button>
            </div>
        </b-modal>
        <div v-show="matchStatisticItems && matchStatisticItems.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-statistic-item.component.ts">
</script>
