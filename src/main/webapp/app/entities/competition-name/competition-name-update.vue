<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionName.home.createOrEditLabel" v-text="$t('registatsApp.competitionName.home.createOrEditLabel')">Create or edit a CompetitionName</h2>
                <div>
                    <div class="form-group" v-if="competitionName.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionName.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.slug')" for="competition-name-slug">Slug</label>
                        <input type="text" class="form-control" name="slug" id="competition-name-slug"
                            :class="{'valid': !$v.competitionName.slug.$invalid, 'invalid': $v.competitionName.slug.$invalid }" v-model="$v.competitionName.slug.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.city')" for="competition-name-city">City</label>
                        <input type="text" class="form-control" name="city" id="competition-name-city"
                            :class="{'valid': !$v.competitionName.city.$invalid, 'invalid': $v.competitionName.city.$invalid }" v-model="$v.competitionName.city.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.region')" for="competition-name-region">Region</label>
                        <input type="text" class="form-control" name="region" id="competition-name-region"
                            :class="{'valid': !$v.competitionName.region.$invalid, 'invalid': $v.competitionName.region.$invalid }" v-model="$v.competitionName.region.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.nation')" for="competition-name-nation">Nation</label>
                        <input type="text" class="form-control" name="nation" id="competition-name-nation"
                            :class="{'valid': !$v.competitionName.nation.$invalid, 'invalid': $v.competitionName.nation.$invalid }" v-model="$v.competitionName.nation.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.name')" for="competition-name-name">Name</label>
                        <input type="text" class="form-control" name="name" id="competition-name-name"
                            :class="{'valid': !$v.competitionName.name.$invalid, 'invalid': $v.competitionName.name.$invalid }" v-model="$v.competitionName.name.$model"  required/>
                        <div v-if="$v.competitionName.name.$anyDirty && $v.competitionName.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.competitionName.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.description')" for="competition-name-description">Description</label>
                        <input type="text" class="form-control" name="description" id="competition-name-description"
                            :class="{'valid': !$v.competitionName.description.$invalid, 'invalid': $v.competitionName.description.$invalid }" v-model="$v.competitionName.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionName.active')" for="competition-name-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="competition-name-active"
                            :class="{'valid': !$v.competitionName.active.$invalid, 'invalid': $v.competitionName.active.$invalid }" v-model="$v.competitionName.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionName.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-name-update.component.ts">
</script>
