<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionTeam.home.createOrEditLabel" v-text="$t('registatsApp.competitionTeam.home.createOrEditLabel')">Create or edit a CompetitionTeam</h2>
                <div>
                    <div class="form-group" v-if="competitionTeam.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionTeam.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeam.status')" for="competition-team-status">Status</label>
                        <input type="text" class="form-control" name="status" id="competition-team-status"
                            :class="{'valid': !$v.competitionTeam.status.$invalid, 'invalid': $v.competitionTeam.status.$invalid }" v-model="$v.competitionTeam.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeam.description')" for="competition-team-description">Description</label>
                        <input type="text" class="form-control" name="description" id="competition-team-description"
                            :class="{'valid': !$v.competitionTeam.description.$invalid, 'invalid': $v.competitionTeam.description.$invalid }" v-model="$v.competitionTeam.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionTeam.active')" for="competition-team-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="competition-team-active"
                            :class="{'valid': !$v.competitionTeam.active.$invalid, 'invalid': $v.competitionTeam.active.$invalid }" v-model="$v.competitionTeam.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionTeam.competition')" for="competition-team-competition">Competition</label>
                        <select class="form-control" id="competition-team-competition" name="competition" v-model="competitionTeam.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionTeam.competition && competitionOption.id === competitionTeam.competition.id ? competitionTeam.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionTeam.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-team-update.component.ts">
</script>
