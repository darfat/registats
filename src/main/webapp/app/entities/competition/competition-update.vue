<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competition.home.createOrEditLabel" v-text="$t('registatsApp.competition.home.createOrEditLabel')">Create or edit a Competition</h2>
                <div>
                    <div class="form-group" v-if="competition.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competition.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competition.season')" for="competition-season">Season</label>
                        <input type="text" class="form-control" name="season" id="competition-season"
                            :class="{'valid': !$v.competition.season.$invalid, 'invalid': $v.competition.season.$invalid }" v-model="$v.competition.season.$model"  required/>
                        <div v-if="$v.competition.season.$anyDirty && $v.competition.season.$invalid">
                            <small class="form-text text-danger" v-if="!$v.competition.season.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competition.slug')" for="competition-slug">Slug</label>
                        <input type="text" class="form-control" name="slug" id="competition-slug"
                            :class="{'valid': !$v.competition.slug.$invalid, 'invalid': $v.competition.slug.$invalid }" v-model="$v.competition.slug.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competition.startDate')" for="competition-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="competition-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.competition.startDate.$invalid, 'invalid': $v.competition.startDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.competition.startDate.$model)"
                            @change="updateInstantField('startDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competition.endDate')" for="competition-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="competition-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.competition.endDate.$invalid, 'invalid': $v.competition.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.competition.endDate.$model)"
                            @change="updateInstantField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competition.competitionFormat')" for="competition-competitionFormat">Competition Format</label>
                        <select class="form-control" name="competitionFormat" :class="{'valid': !$v.competition.competitionFormat.$invalid, 'invalid': $v.competition.competitionFormat.$invalid }" v-model="$v.competition.competitionFormat.$model" id="competition-competitionFormat" >
                            <option value="LEAGUE" v-bind:label="$t('registatsApp.CompetitionFormat.LEAGUE')">LEAGUE</option>
                            <option value="HALF_TOURNAMENT" v-bind:label="$t('registatsApp.CompetitionFormat.HALF_TOURNAMENT')">HALF_TOURNAMENT</option>
                            <option value="TOURNAMENT" v-bind:label="$t('registatsApp.CompetitionFormat.TOURNAMENT')">TOURNAMENT</option>
                            <option value="EXHIBITION" v-bind:label="$t('registatsApp.CompetitionFormat.EXHIBITION')">EXHIBITION</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competition.competitionName')" for="competition-competitionName">Competition Name</label>
                        <select class="form-control" id="competition-competitionName" name="competitionName" v-model="competition.competitionName">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competition.competitionName && competitionNameOption.id === competition.competitionName.id ? competition.competitionName : competitionNameOption" v-for="competitionNameOption in competitionNames" :key="competitionNameOption.id">{{competitionNameOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competition.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-update.component.ts">
</script>
