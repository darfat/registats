<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.globalTeamStatistic.home.createOrEditLabel" v-text="$t('registatsApp.globalTeamStatistic.home.createOrEditLabel')">Create or edit a GlobalTeamStatistic</h2>
                <div>
                    <div class="form-group" v-if="globalTeamStatistic.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="globalTeamStatistic.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalTeamStatistic.valueDouble')" for="global-team-statistic-valueDouble">Value Double</label>
                        <input type="number" class="form-control" name="valueDouble" id="global-team-statistic-valueDouble"
                            :class="{'valid': !$v.globalTeamStatistic.valueDouble.$invalid, 'invalid': $v.globalTeamStatistic.valueDouble.$invalid }" v-model.number="$v.globalTeamStatistic.valueDouble.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalTeamStatistic.valueLong')" for="global-team-statistic-valueLong">Value Long</label>
                        <input type="number" class="form-control" name="valueLong" id="global-team-statistic-valueLong"
                            :class="{'valid': !$v.globalTeamStatistic.valueLong.$invalid, 'invalid': $v.globalTeamStatistic.valueLong.$invalid }" v-model.number="$v.globalTeamStatistic.valueLong.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalTeamStatistic.valueString')" for="global-team-statistic-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="global-team-statistic-valueString"
                            :class="{'valid': !$v.globalTeamStatistic.valueString.$invalid, 'invalid': $v.globalTeamStatistic.valueString.$invalid }" v-model="$v.globalTeamStatistic.valueString.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalTeamStatistic.description')" for="global-team-statistic-description">Description</label>
                        <input type="text" class="form-control" name="description" id="global-team-statistic-description"
                            :class="{'valid': !$v.globalTeamStatistic.description.$invalid, 'invalid': $v.globalTeamStatistic.description.$invalid }" v-model="$v.globalTeamStatistic.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.globalTeamStatistic.name')" for="global-team-statistic-name">Name</label>
                        <input type="text" class="form-control" name="name" id="global-team-statistic-name"
                            :class="{'valid': !$v.globalTeamStatistic.name.$invalid, 'invalid': $v.globalTeamStatistic.name.$invalid }" v-model="$v.globalTeamStatistic.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.globalTeamStatistic.team')" for="global-team-statistic-team">Team</label>
                        <select class="form-control" id="global-team-statistic-team" name="team" v-model="globalTeamStatistic.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="globalTeamStatistic.team && teamOption.id === globalTeamStatistic.team.id ? globalTeamStatistic.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.globalTeamStatistic.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./global-team-statistic-update.component.ts">
</script>
