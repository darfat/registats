<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionPlayerStatistic.home.createOrEditLabel" v-text="$t('registatsApp.competitionPlayerStatistic.home.createOrEditLabel')">Create or edit a CompetitionPlayerStatistic</h2>
                <div>
                    <div class="form-group" v-if="competitionPlayerStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionPlayerStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionPlayerStatistic.valueDouble')" for="competition-player-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="competition-player-statistic-valueDouble"
                            :class="{'valid': !$v.competitionPlayerStatistic.valueDouble.$invalid, 'invalid': $v.competitionPlayerStatistic.valueDouble.$invalid }" v-model.number="$v.competitionPlayerStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionPlayerStatistic.valueLong')" for="competition-player-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="competition-player-statistic-valueLong"
                            :class="{'valid': !$v.competitionPlayerStatistic.valueLong.$invalid, 'invalid': $v.competitionPlayerStatistic.valueLong.$invalid }" v-model.number="$v.competitionPlayerStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionPlayerStatistic.valueString')" for="competition-player-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="competition-player-statistic-valueString"
                            :class="{'valid': !$v.competitionPlayerStatistic.valueString.$invalid, 'invalid': $v.competitionPlayerStatistic.valueString.$invalid }" v-model="$v.competitionPlayerStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionPlayerStatistic.description')" for="competition-player-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="competition-player-statistic-description"
                            :class="{'valid': !$v.competitionPlayerStatistic.description.$invalid, 'invalid': $v.competitionPlayerStatistic.description.$invalid }" v-model="$v.competitionPlayerStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionPlayerStatistic.name')" for="competition-player-statistic-name">Name</label>
                        <input type="text" class="form-control" name="name" id="competition-player-statistic-name"
                            :class="{'valid': !$v.competitionPlayerStatistic.name.$invalid, 'invalid': $v.competitionPlayerStatistic.name.$invalid }" v-model="$v.competitionPlayerStatistic.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionPlayerStatistic.competition')" for="competition-player-statistic-competition">Competition</label>
                        <select class="form-control" id="competition-player-statistic-competition" name="competition" v-model="competitionPlayerStatistic.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionPlayerStatistic.competition && competitionOption.id === competitionPlayerStatistic.competition.id ? competitionPlayerStatistic.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionPlayerStatistic.statistic')" for="competition-player-statistic-statistic">Statistic</label>
                        <select class="form-control" id="competition-player-statistic-statistic" name="statistic" v-model="competitionPlayerStatistic.statistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionPlayerStatistic.statistic && competitionStatisticItemOption.id === competitionPlayerStatistic.statistic.id ? competitionPlayerStatistic.statistic : competitionStatisticItemOption" v-for="competitionStatisticItemOption in competitionStatisticItems" :key="competitionStatisticItemOption.id">{{competitionStatisticItemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionPlayerStatistic.player')" for="competition-player-statistic-player">Player</label>
                        <select class="form-control" id="competition-player-statistic-player" name="player" v-model="competitionPlayerStatistic.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionPlayerStatistic.player && playerOption.id === competitionPlayerStatistic.player.id ? competitionPlayerStatistic.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionPlayerStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-player-statistic-update.component.ts">
</script>
