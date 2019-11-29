<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionStatisticItem.home.createOrEditLabel" v-text="$t('registatsApp.competitionStatisticItem.home.createOrEditLabel')">Create or edit a CompetitionStatisticItem</h2>
                <div>
                    <div class="form-group" v-if="competitionStatisticItem.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionStatisticItem.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStatisticItem.name')" for="competition-statistic-item-name">Name</label>
                        <input type="text" class="form-control" name="name" id="competition-statistic-item-name"
                            :class="{'valid': !$v.competitionStatisticItem.name.$invalid, 'invalid': $v.competitionStatisticItem.name.$invalid }" v-model="$v.competitionStatisticItem.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStatisticItem.description')" for="competition-statistic-item-description">Description</label>
                        <input type="text" class="form-control" name="description" id="competition-statistic-item-description"
                            :class="{'valid': !$v.competitionStatisticItem.description.$invalid, 'invalid': $v.competitionStatisticItem.description.$invalid }" v-model="$v.competitionStatisticItem.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStatisticItem.category')" for="competition-statistic-item-category">Category</label>
                        <select class="form-control" name="category" :class="{'valid': !$v.competitionStatisticItem.category.$invalid, 'invalid': $v.competitionStatisticItem.category.$invalid }" v-model="$v.competitionStatisticItem.category.$model" id="competition-statistic-item-category" >
                            <option value="TEAM" v-bind:label="$t('registatsApp.CompetitionStatisticItemCategory.TEAM')">TEAM</option>
                            <option value="PLAYER" v-bind:label="$t('registatsApp.CompetitionStatisticItemCategory.PLAYER')">PLAYER</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStatisticItem.active')" for="competition-statistic-item-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="competition-statistic-item-active"
                            :class="{'valid': !$v.competitionStatisticItem.active.$invalid, 'invalid': $v.competitionStatisticItem.active.$invalid }" v-model="$v.competitionStatisticItem.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionStatisticItem.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-statistic-item-update.component.ts">
</script>
