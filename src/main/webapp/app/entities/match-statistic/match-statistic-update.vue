<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchStatistic.home.createOrEditLabel" v-text="$t('registatsApp.matchStatistic.home.createOrEditLabel')">Create or edit a MatchStatistic</h2>
                <div>
                    <div class="form-group" v-if="matchStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatistic.description')" for="match-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-statistic-description"
                            :class="{'valid': !$v.matchStatistic.description.$invalid, 'invalid': $v.matchStatistic.description.$invalid }" v-model="$v.matchStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatistic.value')" for="match-statistic-value">Value</label>
                        <input type="number" class="form-control" name="value" id="match-statistic-value"
                            :class="{'valid': !$v.matchStatistic.value.$invalid, 'invalid': $v.matchStatistic.value.$invalid }" v-model.number="$v.matchStatistic.value.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatistic.valueDouble')" for="match-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="match-statistic-valueDouble"
                            :class="{'valid': !$v.matchStatistic.valueDouble.$invalid, 'invalid': $v.matchStatistic.valueDouble.$invalid }" v-model.number="$v.matchStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatistic.valueLong')" for="match-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="match-statistic-valueLong"
                            :class="{'valid': !$v.matchStatistic.valueLong.$invalid, 'invalid': $v.matchStatistic.valueLong.$invalid }" v-model.number="$v.matchStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchStatistic.valueString')" for="match-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="match-statistic-valueString"
                            :class="{'valid': !$v.matchStatistic.valueString.$invalid, 'invalid': $v.matchStatistic.valueString.$invalid }" v-model="$v.matchStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchStatistic.team')" for="match-statistic-team">Team</label>
                        <select class="form-control" id="match-statistic-team" name="team" v-model="matchStatistic.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchStatistic.team && teamOption.id === matchStatistic.team.id ? matchStatistic.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchStatistic.statistic')" for="match-statistic-statistic">Statistic</label>
                        <select class="form-control" id="match-statistic-statistic" name="statistic" v-model="matchStatistic.statistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchStatistic.statistic && matchStatisticItemOption.id === matchStatistic.statistic.id ? matchStatistic.statistic : matchStatisticItemOption" v-for="matchStatisticItemOption in matchStatisticItems" :key="matchStatisticItemOption.id">{{matchStatisticItemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchStatistic.matchHomeInfo')" for="match-statistic-matchHomeInfo">Match Home Info</label>
                        <select class="form-control" id="match-statistic-matchHomeInfo" name="matchHomeInfo" v-model="matchStatistic.matchHomeInfo">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchStatistic.matchHomeInfo && matchHomeInfoOption.id === matchStatistic.matchHomeInfo.id ? matchStatistic.matchHomeInfo : matchHomeInfoOption" v-for="matchHomeInfoOption in matchHomeInfos" :key="matchHomeInfoOption.id">{{matchHomeInfoOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchStatistic.matchAwayInfo')" for="match-statistic-matchAwayInfo">Match Away Info</label>
                        <select class="form-control" id="match-statistic-matchAwayInfo" name="matchAwayInfo" v-model="matchStatistic.matchAwayInfo">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchStatistic.matchAwayInfo && matchAwayInfoOption.id === matchStatistic.matchAwayInfo.id ? matchStatistic.matchAwayInfo : matchAwayInfoOption" v-for="matchAwayInfoOption in matchAwayInfos" :key="matchAwayInfoOption.id">{{matchAwayInfoOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-statistic-update.component.ts">
</script>
