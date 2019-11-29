<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.player.home.createOrEditLabel" v-text="$t('registatsApp.player.home.createOrEditLabel')">Create or edit a Player</h2>
                <div>
                    <div class="form-group" v-if="player.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="player.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.firstName')" for="player-firstName">First Name</label>
                        <input type="text" class="form-control" name="firstName" id="player-firstName"
                            :class="{'valid': !$v.player.firstName.$invalid, 'invalid': $v.player.firstName.$invalid }" v-model="$v.player.firstName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.lastName')" for="player-lastName">Last Name</label>
                        <input type="text" class="form-control" name="lastName" id="player-lastName"
                            :class="{'valid': !$v.player.lastName.$invalid, 'invalid': $v.player.lastName.$invalid }" v-model="$v.player.lastName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.fullName')" for="player-fullName">Full Name</label>
                        <input type="text" class="form-control" name="fullName" id="player-fullName"
                            :class="{'valid': !$v.player.fullName.$invalid, 'invalid': $v.player.fullName.$invalid }" v-model="$v.player.fullName.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.email')" for="player-email">Email</label>
                        <input type="text" class="form-control" name="email" id="player-email"
                            :class="{'valid': !$v.player.email.$invalid, 'invalid': $v.player.email.$invalid }" v-model="$v.player.email.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.phoneNumber')" for="player-phoneNumber">Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" id="player-phoneNumber"
                            :class="{'valid': !$v.player.phoneNumber.$invalid, 'invalid': $v.player.phoneNumber.$invalid }" v-model="$v.player.phoneNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.instagram')" for="player-instagram">Instagram</label>
                        <input type="text" class="form-control" name="instagram" id="player-instagram"
                            :class="{'valid': !$v.player.instagram.$invalid, 'invalid': $v.player.instagram.$invalid }" v-model="$v.player.instagram.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.photo')" for="player-photo">Photo</label>
                        <input type="text" class="form-control" name="photo" id="player-photo"
                            :class="{'valid': !$v.player.photo.$invalid, 'invalid': $v.player.photo.$invalid }" v-model="$v.player.photo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.idCard')" for="player-idCard">Id Card</label>
                        <input type="text" class="form-control" name="idCard" id="player-idCard"
                            :class="{'valid': !$v.player.idCard.$invalid, 'invalid': $v.player.idCard.$invalid }" v-model="$v.player.idCard.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.address')" for="player-address">Address</label>
                        <input type="text" class="form-control" name="address" id="player-address"
                            :class="{'valid': !$v.player.address.$invalid, 'invalid': $v.player.address.$invalid }" v-model="$v.player.address.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.birthPlace')" for="player-birthPlace">Birth Place</label>
                        <input type="text" class="form-control" name="birthPlace" id="player-birthPlace"
                            :class="{'valid': !$v.player.birthPlace.$invalid, 'invalid': $v.player.birthPlace.$invalid }" v-model="$v.player.birthPlace.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.player.birthDate')" for="player-birthDate">Birth Date</label>
                        <div class="d-flex">
                            <input id="player-birthDate" type="datetime-local" class="form-control" name="birthDate" :class="{'valid': !$v.player.birthDate.$invalid, 'invalid': $v.player.birthDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.player.birthDate.$model)"
                            @change="updateInstantField('birthDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.player.position')" for="player-position">Position</label>
                        <select class="form-control" id="player-position" name="position" v-model="player.position">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="player.position && positionOption.id === player.position.id ? player.position : positionOption" v-for="positionOption in positions" :key="positionOption.id">{{positionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.player.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./player-update.component.ts">
</script>
