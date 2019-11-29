<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchHomeInfo.home.createOrEditLabel" v-text="$t('registatsApp.matchHomeInfo.home.createOrEditLabel')">Create or edit a MatchHomeInfo</h2>
                <div>
                    <div class="form-group" v-if="matchHomeInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchHomeInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchHomeInfo.description')" for="match-home-info-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-home-info-description"
                            :class="{'valid': !$v.matchHomeInfo.description.$invalid, 'invalid': $v.matchHomeInfo.description.$invalid }" v-model="$v.matchHomeInfo.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchHomeInfo.formation')" for="match-home-info-formation">Formation</label>
                        <select class="form-control" name="formation" :class="{'valid': !$v.matchHomeInfo.formation.$invalid, 'invalid': $v.matchHomeInfo.formation.$invalid }" v-model="$v.matchHomeInfo.formation.$model" id="match-home-info-formation" >
                            <option value="FOURFOURTWO" v-bind:label="$t('registatsApp.Formation.FOURFOURTWO')">FOURFOURTWO</option>
                            <option value="FOURONETWOTHREE" v-bind:label="$t('registatsApp.Formation.FOURONETWOTHREE')">FOURONETWOTHREE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchHomeInfo.analysis')" for="match-home-info-analysis">Analysis</label>
                        <input type="text" class="form-control" name="analysis" id="match-home-info-analysis"
                            :class="{'valid': !$v.matchHomeInfo.analysis.$invalid, 'invalid': $v.matchHomeInfo.analysis.$invalid }" v-model="$v.matchHomeInfo.analysis.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchHomeInfo.preMatchTalk')" for="match-home-info-preMatchTalk">Pre Match Talk</label>
                        <input type="text" class="form-control" name="preMatchTalk" id="match-home-info-preMatchTalk"
                            :class="{'valid': !$v.matchHomeInfo.preMatchTalk.$invalid, 'invalid': $v.matchHomeInfo.preMatchTalk.$invalid }" v-model="$v.matchHomeInfo.preMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchHomeInfo.postMatchTalk')" for="match-home-info-postMatchTalk">Post Match Talk</label>
                        <input type="text" class="form-control" name="postMatchTalk" id="match-home-info-postMatchTalk"
                            :class="{'valid': !$v.matchHomeInfo.postMatchTalk.$invalid, 'invalid': $v.matchHomeInfo.postMatchTalk.$invalid }" v-model="$v.matchHomeInfo.postMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchHomeInfo.match')" for="match-home-info-match">Match</label>
                        <select class="form-control" id="match-home-info-match" name="match" v-model="matchHomeInfo.match">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchHomeInfo.match && matchOption.id === matchHomeInfo.match.id ? matchHomeInfo.match : matchOption" v-for="matchOption in matches" :key="matchOption.id">{{matchOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchHomeInfo.team')" for="match-home-info-team">Team</label>
                        <select class="form-control" id="match-home-info-team" name="team" v-model="matchHomeInfo.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchHomeInfo.team && teamOption.id === matchHomeInfo.team.id ? matchHomeInfo.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchHomeInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-home-info-update.component.ts">
</script>
