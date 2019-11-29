<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.team.home.createOrEditLabel" v-text="$t('registatsApp.team.home.createOrEditLabel')">Create or edit a Team</h2>
                <div>
                    <div class="form-group" v-if="team.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="team.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.team.name')" for="team-name">Name</label>
                        <input type="text" class="form-control" name="name" id="team-name"
                            :class="{'valid': !$v.team.name.$invalid, 'invalid': $v.team.name.$invalid }" v-model="$v.team.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.team.description')" for="team-description">Description</label>
                        <input type="text" class="form-control" name="description" id="team-description"
                            :class="{'valid': !$v.team.description.$invalid, 'invalid': $v.team.description.$invalid }" v-model="$v.team.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.team.logo')" for="team-logo">Logo</label>
                        <input type="text" class="form-control" name="logo" id="team-logo"
                            :class="{'valid': !$v.team.logo.$invalid, 'invalid': $v.team.logo.$invalid }" v-model="$v.team.logo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.team.managerName')" for="team-managerName">Manager Name</label>
                        <input type="text" class="form-control" name="managerName" id="team-managerName"
                            :class="{'valid': !$v.team.managerName.$invalid, 'invalid': $v.team.managerName.$invalid }" v-model="$v.team.managerName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.team.headCoachName')" for="team-headCoachName">Head Coach Name</label>
                        <input type="text" class="form-control" name="headCoachName" id="team-headCoachName"
                            :class="{'valid': !$v.team.headCoachName.$invalid, 'invalid': $v.team.headCoachName.$invalid }" v-model="$v.team.headCoachName.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.team.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./team-update.component.ts">
</script>
