<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.match.home.createOrEditLabel" v-text="$t('registatsApp.match.home.createOrEditLabel')">Create or edit a Match</h2>
                <div>
                    <div class="form-group" v-if="match.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="match.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.date')" for="match-date">Date</label>
                        <div class="d-flex">
                            <input id="match-date" type="datetime-local" class="form-control" name="date" :class="{'valid': !$v.match.date.$invalid, 'invalid': $v.match.date.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.match.date.$model)"
                            @change="updateInstantField('date', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.time')" for="match-time">Time</label>
                        <div class="d-flex">
                            <input id="match-time" type="datetime-local" class="form-control" name="time" :class="{'valid': !$v.match.time.$invalid, 'invalid': $v.match.time.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.match.time.$model)"
                            @change="updateInstantField('time', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.name')" for="match-name">Name</label>
                        <input type="text" class="form-control" name="name" id="match-name"
                            :class="{'valid': !$v.match.name.$invalid, 'invalid': $v.match.name.$invalid }" v-model="$v.match.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.description')" for="match-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-description"
                            :class="{'valid': !$v.match.description.$invalid, 'invalid': $v.match.description.$invalid }" v-model="$v.match.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.refree')" for="match-refree">Refree</label>
                        <input type="text" class="form-control" name="refree" id="match-refree"
                            :class="{'valid': !$v.match.refree.$invalid, 'invalid': $v.match.refree.$invalid }" v-model="$v.match.refree.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.liveStreamUrl')" for="match-liveStreamUrl">Live Stream Url</label>
                        <input type="text" class="form-control" name="liveStreamUrl" id="match-liveStreamUrl"
                            :class="{'valid': !$v.match.liveStreamUrl.$invalid, 'invalid': $v.match.liveStreamUrl.$invalid }" v-model="$v.match.liveStreamUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.wheater')" for="match-wheater">Wheater</label>
                        <input type="text" class="form-control" name="wheater" id="match-wheater"
                            :class="{'valid': !$v.match.wheater.$invalid, 'invalid': $v.match.wheater.$invalid }" v-model="$v.match.wheater.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.wind')" for="match-wind">Wind</label>
                        <input type="text" class="form-control" name="wind" id="match-wind"
                            :class="{'valid': !$v.match.wind.$invalid, 'invalid': $v.match.wind.$invalid }" v-model="$v.match.wind.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.analysis')" for="match-analysis">Analysis</label>
                        <input type="text" class="form-control" name="analysis" id="match-analysis"
                            :class="{'valid': !$v.match.analysis.$invalid, 'invalid': $v.match.analysis.$invalid }" v-model="$v.match.analysis.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.preMatchTalk')" for="match-preMatchTalk">Pre Match Talk</label>
                        <input type="text" class="form-control" name="preMatchTalk" id="match-preMatchTalk"
                            :class="{'valid': !$v.match.preMatchTalk.$invalid, 'invalid': $v.match.preMatchTalk.$invalid }" v-model="$v.match.preMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.match.postMatchTalk')" for="match-postMatchTalk">Post Match Talk</label>
                        <input type="text" class="form-control" name="postMatchTalk" id="match-postMatchTalk"
                            :class="{'valid': !$v.match.postMatchTalk.$invalid, 'invalid': $v.match.postMatchTalk.$invalid }" v-model="$v.match.postMatchTalk.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.match.venue')" for="match-venue">Venue</label>
                        <select class="form-control" id="match-venue" name="venue" v-model="match.venue">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="match.venue && venueOption.id === match.venue.id ? match.venue : venueOption" v-for="venueOption in venues" :key="venueOption.id">{{venueOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.match.homeTeam')" for="match-homeTeam">Home Team</label>
                        <select class="form-control" id="match-homeTeam" name="homeTeam" v-model="match.homeTeam">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="match.homeTeam && teamOption.id === match.homeTeam.id ? match.homeTeam : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.match.awayTeam')" for="match-awayTeam">Away Team</label>
                        <select class="form-control" id="match-awayTeam" name="awayTeam" v-model="match.awayTeam">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="match.awayTeam && teamOption.id === match.awayTeam.id ? match.awayTeam : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.match.competition')" for="match-competition">Competition</label>
                        <select class="form-control" id="match-competition" name="competition" v-model="match.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="match.competition && competitionOption.id === match.competition.id ? match.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.match.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-update.component.ts">
</script>
