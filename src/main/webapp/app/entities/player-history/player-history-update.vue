<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.playerHistory.home.createOrEditLabel" v-text="$t('registatsApp.playerHistory.home.createOrEditLabel')">Create or edit a PlayerHistory</h2>
                <div>
                    <div class="form-group" v-if="playerHistory.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="playerHistory.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.startDate')" for="player-history-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="player-history-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.playerHistory.startDate.$invalid, 'invalid': $v.playerHistory.startDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.playerHistory.startDate.$model)"
                            @change="updateInstantField('startDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.endDate')" for="player-history-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="player-history-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.playerHistory.endDate.$invalid, 'invalid': $v.playerHistory.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.playerHistory.endDate.$model)"
                            @change="updateInstantField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.appearance')" for="player-history-appearance">Appearance</label>
                        <input type="number" class="form-control" name="appearance" id="player-history-appearance"
                            :class="{'valid': !$v.playerHistory.appearance.$invalid, 'invalid': $v.playerHistory.appearance.$invalid }" v-model.number="$v.playerHistory.appearance.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.minutePlayed')" for="player-history-minutePlayed">Minute Played</label>
                        <input type="number" class="form-control" name="minutePlayed" id="player-history-minutePlayed"
                            :class="{'valid': !$v.playerHistory.minutePlayed.$invalid, 'invalid': $v.playerHistory.minutePlayed.$invalid }" v-model.number="$v.playerHistory.minutePlayed.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.goals')" for="player-history-goals">Goals</label>
                        <input type="number" class="form-control" name="goals" id="player-history-goals"
                            :class="{'valid': !$v.playerHistory.goals.$invalid, 'invalid': $v.playerHistory.goals.$invalid }" v-model.number="$v.playerHistory.goals.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.assists')" for="player-history-assists">Assists</label>
                        <input type="number" class="form-control" name="assists" id="player-history-assists"
                            :class="{'valid': !$v.playerHistory.assists.$invalid, 'invalid': $v.playerHistory.assists.$invalid }" v-model.number="$v.playerHistory.assists.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.playerHistory.averageRating')" for="player-history-averageRating">Average Rating</label>
                        <input type="number" class="form-control" name="averageRating" id="player-history-averageRating"
                            :class="{'valid': !$v.playerHistory.averageRating.$invalid, 'invalid': $v.playerHistory.averageRating.$invalid }" v-model.number="$v.playerHistory.averageRating.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.playerHistory.player')" for="player-history-player">Player</label>
                        <select class="form-control" id="player-history-player" name="player" v-model="playerHistory.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="playerHistory.player && playerOption.id === playerHistory.player.id ? playerHistory.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.playerHistory.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./player-history-update.component.ts">
</script>
