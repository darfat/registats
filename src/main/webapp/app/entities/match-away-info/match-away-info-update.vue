<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchAwayInfo.home.createOrEditLabel" v-text="$t('registatsApp.matchAwayInfo.home.createOrEditLabel')">Create or edit a MatchAwayInfo</h2>
                <div>
                    <div class="form-group" v-if="matchAwayInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchAwayInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchAwayInfo.description')" for="match-away-info-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-away-info-description"
                            :class="{'valid': !$v.matchAwayInfo.description.$invalid, 'invalid': $v.matchAwayInfo.description.$invalid }" v-model="$v.matchAwayInfo.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchAwayInfo.formation')" for="match-away-info-formation">Formation</label>
                        <select class="form-control" name="formation" :class="{'valid': !$v.matchAwayInfo.formation.$invalid, 'invalid': $v.matchAwayInfo.formation.$invalid }" v-model="$v.matchAwayInfo.formation.$model" id="match-away-info-formation" >
                            <option value="FOURFOURTWO" v-bind:label="$t('registatsApp.Formation.FOURFOURTWO')">FOURFOURTWO</option>
                            <option value="FOURONETWOTHREE" v-bind:label="$t('registatsApp.Formation.FOURONETWOTHREE')">FOURONETWOTHREE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchAwayInfo.analysis')" for="match-away-info-analysis">Analysis</label>
                        <input type="text" class="form-control" name="analysis" id="match-away-info-analysis"
                            :class="{'valid': !$v.matchAwayInfo.analysis.$invalid, 'invalid': $v.matchAwayInfo.analysis.$invalid }" v-model="$v.matchAwayInfo.analysis.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchAwayInfo.preMatchTalk')" for="match-away-info-preMatchTalk">Pre Match Talk</label>
                        <input type="text" class="form-control" name="preMatchTalk" id="match-away-info-preMatchTalk"
                            :class="{'valid': !$v.matchAwayInfo.preMatchTalk.$invalid, 'invalid': $v.matchAwayInfo.preMatchTalk.$invalid }" v-model="$v.matchAwayInfo.preMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchAwayInfo.postMatchTalk')" for="match-away-info-postMatchTalk">Post Match Talk</label>
                        <input type="text" class="form-control" name="postMatchTalk" id="match-away-info-postMatchTalk"
                            :class="{'valid': !$v.matchAwayInfo.postMatchTalk.$invalid, 'invalid': $v.matchAwayInfo.postMatchTalk.$invalid }" v-model="$v.matchAwayInfo.postMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchAwayInfo.match')" for="match-away-info-match">Match</label>
                        <select class="form-control" id="match-away-info-match" name="match" v-model="matchAwayInfo.match">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchAwayInfo.match && matchOption.id === matchAwayInfo.match.id ? matchAwayInfo.match : matchOption" v-for="matchOption in matches" :key="matchOption.id">{{matchOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchAwayInfo.team')" for="match-away-info-team">Team</label>
                        <select class="form-control" id="match-away-info-team" name="team" v-model="matchAwayInfo.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchAwayInfo.team && teamOption.id === matchAwayInfo.team.id ? matchAwayInfo.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchAwayInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-away-info-update.component.ts">
</script>
