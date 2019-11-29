<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.venue.home.createOrEditLabel" v-text="$t('registatsApp.venue.home.createOrEditLabel')">Create or edit a Venue</h2>
                <div>
                    <div class="form-group" v-if="venue.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="venue.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.venue.name')" for="venue-name">Name</label>
                        <input type="text" class="form-control" name="name" id="venue-name"
                            :class="{'valid': !$v.venue.name.$invalid, 'invalid': $v.venue.name.$invalid }" v-model="$v.venue.name.$model"  required/>
                        <div v-if="$v.venue.name.$anyDirty && $v.venue.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.venue.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.venue.description')" for="venue-description">Description</label>
                        <input type="text" class="form-control" name="description" id="venue-description"
                            :class="{'valid': !$v.venue.description.$invalid, 'invalid': $v.venue.description.$invalid }" v-model="$v.venue.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.venue.address')" for="venue-address">Address</label>
                        <input type="text" class="form-control" name="address" id="venue-address"
                            :class="{'valid': !$v.venue.address.$invalid, 'invalid': $v.venue.address.$invalid }" v-model="$v.venue.address.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.venue.phoneNumber')" for="venue-phoneNumber">Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" id="venue-phoneNumber"
                            :class="{'valid': !$v.venue.phoneNumber.$invalid, 'invalid': $v.venue.phoneNumber.$invalid }" v-model="$v.venue.phoneNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.venue.location')" for="venue-location">Location</label>
                        <input type="text" class="form-control" name="location" id="venue-location"
                            :class="{'valid': !$v.venue.location.$invalid, 'invalid': $v.venue.location.$invalid }" v-model="$v.venue.location.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.venue.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./venue-update.component.ts">
</script>
