<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.competitionStanding.home.createOrEditLabel" v-text="$t('registatsApp.competitionStanding.home.createOrEditLabel')">Create or edit a CompetitionStanding</h2>
                <div>
                    <div class="form-group" v-if="competitionStanding.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="competitionStanding.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.position')" for="competition-standing-position">Position</label>
                        <input type="number" class="form-control" name="position" id="competition-standing-position"
                            :class="{'valid': !$v.competitionStanding.position.$invalid, 'invalid': $v.competitionStanding.position.$invalid }" v-model.number="$v.competitionStanding.position.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.played')" for="competition-standing-played">Played</label>
                        <input type="number" class="form-control" name="played" id="competition-standing-played"
                            :class="{'valid': !$v.competitionStanding.played.$invalid, 'invalid': $v.competitionStanding.played.$invalid }" v-model.number="$v.competitionStanding.played.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.win')" for="competition-standing-win">Win</label>
                        <input type="number" class="form-control" name="win" id="competition-standing-win"
                            :class="{'valid': !$v.competitionStanding.win.$invalid, 'invalid': $v.competitionStanding.win.$invalid }" v-model.number="$v.competitionStanding.win.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.draw')" for="competition-standing-draw">Draw</label>
                        <input type="number" class="form-control" name="draw" id="competition-standing-draw"
                            :class="{'valid': !$v.competitionStanding.draw.$invalid, 'invalid': $v.competitionStanding.draw.$invalid }" v-model.number="$v.competitionStanding.draw.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.lose')" for="competition-standing-lose">Lose</label>
                        <input type="number" class="form-control" name="lose" id="competition-standing-lose"
                            :class="{'valid': !$v.competitionStanding.lose.$invalid, 'invalid': $v.competitionStanding.lose.$invalid }" v-model.number="$v.competitionStanding.lose.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.goal')" for="competition-standing-goal">Goal</label>
                        <input type="number" class="form-control" name="goal" id="competition-standing-goal"
                            :class="{'valid': !$v.competitionStanding.goal.$invalid, 'invalid': $v.competitionStanding.goal.$invalid }" v-model.number="$v.competitionStanding.goal.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.goalAgaints')" for="competition-standing-goalAgaints">Goal Againts</label>
                        <input type="number" class="form-control" name="goalAgaints" id="competition-standing-goalAgaints"
                            :class="{'valid': !$v.competitionStanding.goalAgaints.$invalid, 'invalid': $v.competitionStanding.goalAgaints.$invalid }" v-model.number="$v.competitionStanding.goalAgaints.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.point')" for="competition-standing-point">Point</label>
                        <input type="number" class="form-control" name="point" id="competition-standing-point"
                            :class="{'valid': !$v.competitionStanding.point.$invalid, 'invalid': $v.competitionStanding.point.$invalid }" v-model.number="$v.competitionStanding.point.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.competitionStanding.minusPoint')" for="competition-standing-minusPoint">Minus Point</label>
                        <input type="number" class="form-control" name="minusPoint" id="competition-standing-minusPoint"
                            :class="{'valid': !$v.competitionStanding.minusPoint.$invalid, 'invalid': $v.competitionStanding.minusPoint.$invalid }" v-model.number="$v.competitionStanding.minusPoint.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.competitionStanding.competition')" for="competition-standing-competition">Competition</label>
                        <select class="form-control" id="competition-standing-competition" name="competition" v-model="competitionStanding.competition">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitionStanding.competition && competitionOption.id === competitionStanding.competition.id ? competitionStanding.competition : competitionOption" v-for="competitionOption in competitions" :key="competitionOption.id">{{competitionOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.competitionStanding.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./competition-standing-update.component.ts">
</script>
