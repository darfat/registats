<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchStatisticItem.home.createOrEditLabel" v-text="$t('registatsApp.matchStatisticItem.home.createOrEditLabel')">Create or edit a MatchStatisticItem</h2>
                <div>
                    <div class="form-group" v-if="matchStatisticItem.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchStatisticItem.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatisticItem.name')" for="match-statistic-item-name">Name</label>
                        <input type="text" class="form-control" name="name" id="match-statistic-item-name"
                            :class="{'valid': !$v.matchStatisticItem.name.$invalid, 'invalid': $v.matchStatisticItem.name.$invalid }" v-model="$v.matchStatisticItem.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatisticItem.description')" for="match-statistic-item-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-statistic-item-description"
                            :class="{'valid': !$v.matchStatisticItem.description.$invalid, 'invalid': $v.matchStatisticItem.description.$invalid }" v-model="$v.matchStatisticItem.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatisticItem.active')" for="match-statistic-item-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="match-statistic-item-active"
                            :class="{'valid': !$v.matchStatisticItem.active.$invalid, 'invalid': $v.matchStatisticItem.active.$invalid }" v-model="$v.matchStatisticItem.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchStatisticItem.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-statistic-item-update.component.ts">
</script>
