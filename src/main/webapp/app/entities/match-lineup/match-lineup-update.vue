<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchLineup.home.createOrEditLabel" v-text="$t('registatsApp.matchLineup.home.createOrEditLabel')">Create or edit a MatchLineup</h2>
                <div>
                    <div class="form-group" v-if="matchLineup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchLineup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.number')" for="match-lineup-number">Number</label>
                        <input type="number" class="form-control" name="number" id="match-lineup-number"
                            :class="{'valid': !$v.matchLineup.number.$invalid, 'invalid': $v.matchLineup.number.$invalid }" v-model.number="$v.matchLineup.number.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.role')" for="match-lineup-role">Role</label>
                        <input type="text" class="form-control" name="role" id="match-lineup-role"
                            :class="{'valid': !$v.matchLineup.role.$invalid, 'invalid': $v.matchLineup.role.$invalid }" v-model="$v.matchLineup.role.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.firstHalfPlay')" for="match-lineup-firstHalfPlay">First Half Play</label>
                        <input type="checkbox" class="form-check" name="firstHalfPlay" id="match-lineup-firstHalfPlay"
                            :class="{'valid': !$v.matchLineup.firstHalfPlay.$invalid, 'invalid': $v.matchLineup.firstHalfPlay.$invalid }" v-model="$v.matchLineup.firstHalfPlay.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.secondHalfPlay')" for="match-lineup-secondHalfPlay">Second Half Play</label>
                        <input type="checkbox" class="form-check" name="secondHalfPlay" id="match-lineup-secondHalfPlay"
                            :class="{'valid': !$v.matchLineup.secondHalfPlay.$invalid, 'invalid': $v.matchLineup.secondHalfPlay.$invalid }" v-model="$v.matchLineup.secondHalfPlay.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.status')" for="match-lineup-status">Status</label>
                        <input type="text" class="form-control" name="status" id="match-lineup-status"
                            :class="{'valid': !$v.matchLineup.status.$invalid, 'invalid': $v.matchLineup.status.$invalid }" v-model="$v.matchLineup.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.minuteIn')" for="match-lineup-minuteIn">Minute In</label>
                        <input type="number" class="form-control" name="minuteIn" id="match-lineup-minuteIn"
                            :class="{'valid': !$v.matchLineup.minuteIn.$invalid, 'invalid': $v.matchLineup.minuteIn.$invalid }" v-model.number="$v.matchLineup.minuteIn.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchLineup.minoteOut')" for="match-lineup-minoteOut">Minote Out</label>
                        <input type="number" class="form-control" name="minoteOut" id="match-lineup-minoteOut"
                            :class="{'valid': !$v.matchLineup.minoteOut.$invalid, 'invalid': $v.matchLineup.minoteOut.$invalid }" v-model.number="$v.matchLineup.minoteOut.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchLineup.position')" for="match-lineup-position">Position</label>
                        <select class="form-control" id="match-lineup-position" name="position" v-model="matchLineup.position">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchLineup.position && positionOption.id === matchLineup.position.id ? matchLineup.position : positionOption" v-for="positionOption in positions" :key="positionOption.id">{{positionOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchLineup.player')" for="match-lineup-player">Player</label>
                        <select class="form-control" id="match-lineup-player" name="player" v-model="matchLineup.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchLineup.player && playerOption.id === matchLineup.player.id ? matchLineup.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchLineup.matchHomeInfo')" for="match-lineup-matchHomeInfo">Match Home Info</label>
                        <select class="form-control" id="match-lineup-matchHomeInfo" name="matchHomeInfo" v-model="matchLineup.matchHomeInfo">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchLineup.matchHomeInfo && matchHomeInfoOption.id === matchLineup.matchHomeInfo.id ? matchLineup.matchHomeInfo : matchHomeInfoOption" v-for="matchHomeInfoOption in matchHomeInfos" :key="matchHomeInfoOption.id">{{matchHomeInfoOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchLineup.matchAwayInfo')" for="match-lineup-matchAwayInfo">Match Away Info</label>
                        <select class="form-control" id="match-lineup-matchAwayInfo" name="matchAwayInfo" v-model="matchLineup.matchAwayInfo">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchLineup.matchAwayInfo && matchAwayInfoOption.id === matchLineup.matchAwayInfo.id ? matchLineup.matchAwayInfo : matchAwayInfoOption" v-for="matchAwayInfoOption in matchAwayInfos" :key="matchAwayInfoOption.id">{{matchAwayInfoOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchLineup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-lineup-update.component.ts">
</script>
