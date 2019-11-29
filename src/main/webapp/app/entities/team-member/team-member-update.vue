<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.teamMember.home.createOrEditLabel" v-text="$t('registatsApp.teamMember.home.createOrEditLabel')">Create or edit a TeamMember</h2>
                <div>
                    <div class="form-group" v-if="teamMember.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="teamMember.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamMember.number')" for="team-member-number">Number</label>
                        <input type="number" class="form-control" name="number" id="team-member-number"
                            :class="{'valid': !$v.teamMember.number.$invalid, 'invalid': $v.teamMember.number.$invalid }" v-model.number="$v.teamMember.number.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamMember.joinDate')" for="team-member-joinDate">Join Date</label>
                        <div class="d-flex">
                            <input id="team-member-joinDate" type="datetime-local" class="form-control" name="joinDate" :class="{'valid': !$v.teamMember.joinDate.$invalid, 'invalid': $v.teamMember.joinDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.teamMember.joinDate.$model)"
                            @change="updateInstantField('joinDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamMember.status')" for="team-member-status">Status</label>
                        <input type="text" class="form-control" name="status" id="team-member-status"
                            :class="{'valid': !$v.teamMember.status.$invalid, 'invalid': $v.teamMember.status.$invalid }" v-model="$v.teamMember.status.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamMember.active')" for="team-member-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="team-member-active"
                            :class="{'valid': !$v.teamMember.active.$invalid, 'invalid': $v.teamMember.active.$invalid }" v-model="$v.teamMember.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.teamMember.team')" for="team-member-team">Team</label>
                        <select class="form-control" id="team-member-team" name="team" v-model="teamMember.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="teamMember.team && teamOption.id === teamMember.team.id ? teamMember.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.teamMember.player')" for="team-member-player">Player</label>
                        <select class="form-control" id="team-member-player" name="player" v-model="teamMember.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="teamMember.player && playerOption.id === teamMember.player.id ? teamMember.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.teamMember.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./team-member-update.component.ts">
</script>
