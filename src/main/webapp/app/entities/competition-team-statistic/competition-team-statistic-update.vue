<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionTeamStatistic.home.createOrEditLabel" v-text="$t('registatsApp.competitionTeamStatistic.home.createOrEditLabel')">Create or edit a CompetitionTeamStatistic</h2>
                <div>
                    <div class="form-group" v-if="competitionTeamStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionTeamStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeamStatistic.valueDouble')" for="competition-team-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="competition-team-statistic-valueDouble"
                            :class="{'valid': !$v.competitionTeamStatistic.valueDouble.$invalid, 'invalid': $v.competitionTeamStatistic.valueDouble.$invalid }" v-model.number="$v.competitionTeamStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeamStatistic.valueLong')" for="competition-team-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="competition-team-statistic-valueLong"
                            :class="{'valid': !$v.competitionTeamStatistic.valueLong.$invalid, 'invalid': $v.competitionTeamStatistic.valueLong.$invalid }" v-model.number="$v.competitionTeamStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeamStatistic.valueString')" for="competition-team-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="competition-team-statistic-valueString"
                            :class="{'valid': !$v.competitionTeamStatistic.valueString.$invalid, 'invalid': $v.competitionTeamStatistic.valueString.$invalid }" v-model="$v.competitionTeamStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeamStatistic.description')" for="competition-team-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="competition-team-statistic-description"
                            :class="{'valid': !$v.competitionTeamStatistic.description.$invalid, 'invalid': $v.competitionTeamStatistic.description.$invalid }" v-model="$v.competitionTeamStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeamStatistic.name')" for="competition-team-statistic-name">Name</label>
                        <input type="text" class="form-control" name="name" id="competition-team-statistic-name"
                            :class="{'valid': !$v.competitionTeamStatistic.name.$invalid, 'invalid': $v.competitionTeamStatistic.name.$invalid }" v-model="$v.competitionTeamStatistic.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionTeamStatistic.competition')" for="competition-team-statistic-competition">Competition</label>
                        <select class="form-control" id="competition-team-statistic-competition" name="competition" v-model="competitionTeamStatistic.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionTeamStatistic.competition && competitionOption.id === competitionTeamStatistic.competition.id ? competitionTeamStatistic.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionTeamStatistic.statistic')" for="competition-team-statistic-statistic">Statistic</label>
                        <select class="form-control" id="competition-team-statistic-statistic" name="statistic" v-model="competitionTeamStatistic.statistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionTeamStatistic.statistic && competitionStatisticItemOption.id === competitionTeamStatistic.statistic.id ? competitionTeamStatistic.statistic : competitionStatisticItemOption" v-for="competitionStatisticItemOption in competitionStatisticItems" :key="competitionStatisticItemOption.id">{{competitionStatisticItemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionTeamStatistic.team')" for="competition-team-statistic-team">Team</label>
                        <select class="form-control" id="competition-team-statistic-team" name="team" v-model="competitionTeamStatistic.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionTeamStatistic.team && teamOption.id === competitionTeamStatistic.team.id ? competitionTeamStatistic.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionTeamStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-team-statistic-update.component.ts">
</script>
