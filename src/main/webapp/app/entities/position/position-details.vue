<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="position">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.position.detail.title')">Position</span> {{position.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.position.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{position.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.position.desription')">Desription</span>
                    </dt>
                    <dd>
                        <span>{{position.desription}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.position.category')">Category</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.PositionCategory.' + position.category)">{{position.category}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.position.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{position.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="position.id" :to="{name: 'PositionEdit', params: {positionId: position.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./position-details.component.ts">
</script>
