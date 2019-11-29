<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionStatisticItem">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionStatisticItem.detail.title')">CompetitionStatisticItem</span> {{competitionStatisticItem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionStatisticItem.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{competitionStatisticItem.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStatisticItem.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{competitionStatisticItem.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStatisticItem.category')">Category</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.CompetitionStatisticItemCategory.' + competitionStatisticItem.category)">{{competitionStatisticItem.category}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStatisticItem.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{competitionStatisticItem.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionStatisticItem.id" :to="{name: 'CompetitionStatisticItemEdit', params: {competitionStatisticItemId: competitionStatisticItem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-statistic-item-details.component.ts">
</script>
