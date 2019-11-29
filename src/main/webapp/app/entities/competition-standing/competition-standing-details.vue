<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitionStanding">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.competitionStanding.detail.title')">CompetitionStanding</span> {{competitionStanding.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.position')">Position</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.position}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.played')">Played</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.played}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.win')">Win</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.win}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.draw')">Draw</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.draw}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.lose')">Lose</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.lose}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.goal')">Goal</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.goal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.goalAgaints')">Goal Againts</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.goalAgaints}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.point')">Point</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.point}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.minusPoint')">Minus Point</span>
                    </dt>
                    <dd>
                        <span>{{competitionStanding.minusPoint}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.competitionStanding.competition')">Competition</span>
                    </dt>
                    <dd>
                        <div v-if="competitionStanding.competition">
                            <router-link :to="{name: 'CompetitionView', params: {competitionId: competitionStanding.competition.id}}">{{competitionStanding.competition.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitionStanding.id" :to="{name: 'CompetitionStandingEdit', params: {competitionStandingId: competitionStanding.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competition-standing-details.component.ts">
</script>
