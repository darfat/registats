<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.playerMatchStatistic.home.createOrEditLabel" v-text="$t('registatsApp.playerMatchStatistic.home.createOrEditLabel')">Create or edit a PlayerMatchStatistic</h2>
                <div>
                    <div class="form-group" v-if="playerMatchStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="playerMatchStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerMatchStatistic.description')" for="player-match-statistic-description">Description</label>
                        <input type="number" class="form-control" name="description" id="player-match-statistic-description"
                            :class="{'valid': !$v.playerMatchStatistic.description.$invalid, 'invalid': $v.playerMatchStatistic.description.$invalid }" v-model.number="$v.playerMatchStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerMatchStatistic.value')" for="player-match-statistic-value">Value</label>
                        <input type="number" class="form-control" name="value" id="player-match-statistic-value"
                            :class="{'valid': !$v.playerMatchStatistic.value.$invalid, 'invalid': $v.playerMatchStatistic.value.$invalid }" v-model.number="$v.playerMatchStatistic.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerMatchStatistic.valueDouble')" for="player-match-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="player-match-statistic-valueDouble"
                            :class="{'valid': !$v.playerMatchStatistic.valueDouble.$invalid, 'invalid': $v.playerMatchStatistic.valueDouble.$invalid }" v-model.number="$v.playerMatchStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerMatchStatistic.valueLong')" for="player-match-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="player-match-statistic-valueLong"
                            :class="{'valid': !$v.playerMatchStatistic.valueLong.$invalid, 'invalid': $v.playerMatchStatistic.valueLong.$invalid }" v-model.number="$v.playerMatchStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerMatchStatistic.valueString')" for="player-match-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="player-match-statistic-valueString"
                            :class="{'valid': !$v.playerMatchStatistic.valueString.$invalid, 'invalid': $v.playerMatchStatistic.valueString.$invalid }" v-model="$v.playerMatchStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.playerMatchStatistic.matchLineup')" for="player-match-statistic-matchLineup">Match Lineup</label>
                        <select class="form-control" id="player-match-statistic-matchLineup" name="matchLineup" v-model="playerMatchStatistic.matchLineup">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="playerMatchStatistic.matchLineup && matchLineupOption.id === playerMatchStatistic.matchLineup.id ? playerMatchStatistic.matchLineup : matchLineupOption" v-for="matchLineupOption in matchLineups" :key="matchLineupOption.id">{{matchLineupOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.playerMatchStatistic.statistic')" for="player-match-statistic-statistic">Statistic</label>
                        <select class="form-control" id="player-match-statistic-statistic" name="statistic" v-model="playerMatchStatistic.statistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="playerMatchStatistic.statistic && playerStatisticItemOption.id === playerMatchStatistic.statistic.id ? playerMatchStatistic.statistic : playerStatisticItemOption" v-for="playerStatisticItemOption in playerStatisticItems" :key="playerStatisticItemOption.id">{{playerStatisticItemOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.playerMatchStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./player-match-statistic-update.component.ts">
</script>
