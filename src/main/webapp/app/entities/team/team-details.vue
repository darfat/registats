<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="team">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.team.detail.title')">Team</span> {{team.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.team.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{team.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.team.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{team.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.team.logo')">Logo</span>
                    </dt>
                    <dd>
                        <span>{{team.logo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.team.managerName')">Manager Name</span>
                    </dt>
                    <dd>
                        <span>{{team.managerName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.team.headCoachName')">Head Coach Name</span>
                    </dt>
                    <dd>
                        <span>{{team.headCoachName}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="team.id" :to="{name: 'TeamEdit', params: {teamId: team.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./team-details.component.ts">
</script>
