<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionName">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionName.detail.title')">CompetitionName</span> {{competitionName.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.slug')">Slug</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.slug}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.city')">City</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.city}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.region')">Region</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.nation')">Nation</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.nation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionName.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{competitionName.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionName.id" :to="{name: 'CompetitionNameEdit', params: {competitionNameId: competitionName.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-name-details.component.ts">
</script>
