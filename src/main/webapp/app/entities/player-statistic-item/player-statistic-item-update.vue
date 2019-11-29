<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.playerStatisticItem.home.createOrEditLabel" v-text="$t('registatsApp.playerStatisticItem.home.createOrEditLabel')">Create or edit a PlayerStatisticItem</h2>
                <div>
                    <div class="form-group" v-if="playerStatisticItem.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="playerStatisticItem.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerStatisticItem.name')" for="player-statistic-item-name">Name</label>
                        <input type="text" class="form-control" name="name" id="player-statistic-item-name"
                            :class="{'valid': !$v.playerStatisticItem.name.$invalid, 'invalid': $v.playerStatisticItem.name.$invalid }" v-model="$v.playerStatisticItem.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerStatisticItem.description')" for="player-statistic-item-description">Description</label>
                        <input type="text" class="form-control" name="description" id="player-statistic-item-description"
                            :class="{'valid': !$v.playerStatisticItem.description.$invalid, 'invalid': $v.playerStatisticItem.description.$invalid }" v-model="$v.playerStatisticItem.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerStatisticItem.active')" for="player-statistic-item-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="player-statistic-item-active"
                            :class="{'valid': !$v.playerStatisticItem.active.$invalid, 'invalid': $v.playerStatisticItem.active.$invalid }" v-model="$v.playerStatisticItem.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.playerStatisticItem.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./player-statistic-item-update.component.ts">
</script>
