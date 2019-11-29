<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.teamRegisteredPlayer.home.createOrEditLabel" v-text="$t('registatsApp.teamRegisteredPlayer.home.createOrEditLabel')">Create or edit a TeamRegisteredPlayer</h2>
                <div>
                    <div class="form-group" v-if="teamRegisteredPlayer.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="teamRegisteredPlayer.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamRegisteredPlayer.number')" for="team-registered-player-number">Number</label>
                        <input type="number" class="form-control" name="number" id="team-registered-player-number"
                            :class="{'valid': !$v.teamRegisteredPlayer.number.$invalid, 'invalid': $v.teamRegisteredPlayer.number.$invalid }" v-model.number="$v.teamRegisteredPlayer.number.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamRegisteredPlayer.status')" for="team-registered-player-status">Status</label>
                        <input type="text" class="form-control" name="status" id="team-registered-player-status"
                            :class="{'valid': !$v.teamRegisteredPlayer.status.$invalid, 'invalid': $v.teamRegisteredPlayer.status.$invalid }" v-model="$v.teamRegisteredPlayer.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamRegisteredPlayer.active')" for="team-registered-player-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="team-registered-player-active"
                            :class="{'valid': !$v.teamRegisteredPlayer.active.$invalid, 'invalid': $v.teamRegisteredPlayer.active.$invalid }" v-model="$v.teamRegisteredPlayer.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.teamRegisteredPlayer.team')" for="team-registered-player-team">Team</label>
                        <select class="form-control" id="team-registered-player-team" name="team" v-model="teamRegisteredPlayer.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="teamRegisteredPlayer.team && teamOption.id === teamRegisteredPlayer.team.id ? teamRegisteredPlayer.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.teamRegisteredPlayer.player')" for="team-registered-player-player">Player</label>
                        <select class="form-control" id="team-registered-player-player" name="player" v-model="teamRegisteredPlayer.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="teamRegisteredPlayer.player && playerOption.id === teamRegisteredPlayer.player.id ? teamRegisteredPlayer.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.teamRegisteredPlayer.competition')" for="team-registered-player-competition">Competition</label>
                        <select class="form-control" id="team-registered-player-competition" name="competition" v-model="teamRegisteredPlayer.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="teamRegisteredPlayer.competition && competitionOption.id === teamRegisteredPlayer.competition.id ? teamRegisteredPlayer.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.teamRegisteredPlayer.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./team-registered-player-update.component.ts">
</script>
