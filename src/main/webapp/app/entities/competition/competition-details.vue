<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competition">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competition.detail.title')">Competition</span> {{competition.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competition.season')">Season</span>
                    </dt>
                    <dd>
                        <span>{{competition.season}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competition.slug')">Slug</span>
                    </dt>
                    <dd>
                        <span>{{competition.slug}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competition.startDate')">Start Date</span>
                    </dt>
                    <dd>
                        <span>{{competition.startDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competition.endDate')">End Date</span>
                    </dt>
                    <dd>
                        <span>{{competition.endDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competition.competitionFormat')">Competition Format</span>
                    </dt>
                    <dd>
                        <span v-text="$t('registatsApp.CompetitionFormat.' + competition.competitionFormat)">{{competition.competitionFormat}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competition.competitionName')">Competition Name</span>
                    </dt>
                    <dd>
                        <div v-if="competition.competitionName">
                            <router-link :to="{name: 'CompetitionNameView', params: {competitionNameId: competition.competitionName.id}}">{{competition.competitionName.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competition.id" :to="{name: 'CompetitionEdit', params: {competitionId: competition.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-details.component.ts">
</script>
