<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.position.home.createOrEditLabel" v-text="$t('registatsApp.position.home.createOrEditLabel')">Create or edit a Position</h2>
                <div>
                    <div class="form-group" v-if="position.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="position.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.position.name')" for="position-name">Name</label>
                        <input type="text" class="form-control" name="name" id="position-name"
                            :class="{'valid': !$v.position.name.$invalid, 'invalid': $v.position.name.$invalid }" v-model="$v.position.name.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.position.desription')" for="position-desription">Desription</label>
                        <input type="text" class="form-control" name="desription" id="position-desription"
                            :class="{'valid': !$v.position.desription.$invalid, 'invalid': $v.position.desription.$invalid }" v-model="$v.position.desription.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.position.category')" for="position-category">Category</label>
                        <select class="form-control" name="category" :class="{'valid': !$v.position.category.$invalid, 'invalid': $v.position.category.$invalid }" v-model="$v.position.category.$model" id="position-category" >
                            <option value="GOALKEEPER" v-bind:label="$t('registatsApp.PositionCategory.GOALKEEPER')">GOALKEEPER</option>
                            <option value="DEFENDER" v-bind:label="$t('registatsApp.PositionCategory.DEFENDER')">DEFENDER</option>
                            <option value="MIDFIELDER" v-bind:label="$t('registatsApp.PositionCategory.MIDFIELDER')">MIDFIELDER</option>
                            <option value="FORWARD" v-bind:label="$t('registatsApp.PositionCategory.FORWARD')">FORWARD</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.position.active')" for="position-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="position-active"
                            :class="{'valid': !$v.position.active.$invalid, 'invalid': $v.position.active.$invalid }" v-model="$v.position.active.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.position.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./position-update.component.ts">
</script>
