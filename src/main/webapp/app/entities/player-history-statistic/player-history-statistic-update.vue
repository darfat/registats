<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.playerHistoryStatistic.home.createOrEditLabel" v-text="$t('registatsApp.playerHistoryStatistic.home.createOrEditLabel')">Create or edit a PlayerHistoryStatistic</h2>
                <div>
                    <div class="form-group" v-if="playerHistoryStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="playerHistoryStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistoryStatistic.value')" for="player-history-statistic-value">Value</label>
                        <input type="number" class="form-control" name="value" id="player-history-statistic-value"
                            :class="{'valid': !$v.playerHistoryStatistic.value.$invalid, 'invalid': $v.playerHistoryStatistic.value.$invalid }" v-model.number="$v.playerHistoryStatistic.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistoryStatistic.description')" for="player-history-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="player-history-statistic-description"
                            :class="{'valid': !$v.playerHistoryStatistic.description.$invalid, 'invalid': $v.playerHistoryStatistic.description.$invalid }" v-model="$v.playerHistoryStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.playerHistoryStatistic.playerHistory')" for="player-history-statistic-playerHistory">Player History</label>
                        <select class="form-control" id="player-history-statistic-playerHistory" name="playerHistory" v-model="playerHistoryStatistic.playerHistory">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="playerHistoryStatistic.playerHistory && playerHistoryOption.id === playerHistoryStatistic.playerHistory.id ? playerHistoryStatistic.playerHistory : playerHistoryOption" v-for="playerHistoryOption in playerHistories" :key="playerHistoryOption.id">{{playerHistoryOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.playerHistoryStatistic.statistic')" for="player-history-statistic-statistic">Statistic</label>
                        <select class="form-control" id="player-history-statistic-statistic" name="statistic" v-model="playerHistoryStatistic.statistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="playerHistoryStatistic.statistic && playerStatisticItemOption.id === playerHistoryStatistic.statistic.id ? playerHistoryStatistic.statistic : playerStatisticItemOption" v-for="playerStatisticItemOption in playerStatisticItems" :key="playerStatisticItemOption.id">{{playerStatisticItemOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.playerHistoryStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./player-history-statistic-update.component.ts">
</script>
