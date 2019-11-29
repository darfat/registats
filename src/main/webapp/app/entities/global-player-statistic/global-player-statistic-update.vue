<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.globalPlayerStatistic.home.createOrEditLabel" v-text="$t('registatsApp.globalPlayerStatistic.home.createOrEditLabel')">Create or edit a GlobalPlayerStatistic</h2>
                <div>
                    <div class="form-group" v-if="globalPlayerStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="globalPlayerStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalPlayerStatistic.valueDouble')" for="global-player-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="global-player-statistic-valueDouble"
                            :class="{'valid': !$v.globalPlayerStatistic.valueDouble.$invalid, 'invalid': $v.globalPlayerStatistic.valueDouble.$invalid }" v-model.number="$v.globalPlayerStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalPlayerStatistic.valueLong')" for="global-player-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="global-player-statistic-valueLong"
                            :class="{'valid': !$v.globalPlayerStatistic.valueLong.$invalid, 'invalid': $v.globalPlayerStatistic.valueLong.$invalid }" v-model.number="$v.globalPlayerStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalPlayerStatistic.valueString')" for="global-player-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="global-player-statistic-valueString"
                            :class="{'valid': !$v.globalPlayerStatistic.valueString.$invalid, 'invalid': $v.globalPlayerStatistic.valueString.$invalid }" v-model="$v.globalPlayerStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalPlayerStatistic.description')" for="global-player-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="global-player-statistic-description"
                            :class="{'valid': !$v.globalPlayerStatistic.description.$invalid, 'invalid': $v.globalPlayerStatistic.description.$invalid }" v-model="$v.globalPlayerStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalPlayerStatistic.name')" for="global-player-statistic-name">Name</label>
                        <input type="text" class="form-control" name="name" id="global-player-statistic-name"
                            :class="{'valid': !$v.globalPlayerStatistic.name.$invalid, 'invalid': $v.globalPlayerStatistic.name.$invalid }" v-model="$v.globalPlayerStatistic.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.globalPlayerStatistic.player')" for="global-player-statistic-player">Player</label>
                        <select class="form-control" id="global-player-statistic-player" name="player" v-model="globalPlayerStatistic.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="globalPlayerStatistic.player && playerOption.id === globalPlayerStatistic.player.id ? globalPlayerStatistic.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.globalPlayerStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./global-player-statistic-update.component.ts">
</script>
