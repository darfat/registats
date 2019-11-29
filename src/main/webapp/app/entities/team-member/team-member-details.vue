<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="teamMember">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.teamMember.detail.title')">TeamMember</span> {{teamMember.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.number')">Number</span>
                    </dt>
                    <dd>
                        <span>{{teamMember.number}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.joinDate')">Join Date</span>
                    </dt>
                    <dd>
                        <span>{{teamMember.joinDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{teamMember.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{teamMember.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.team')">Team</span>
                    </dt>
                    <dd>
                        <div v-if="teamMember.team">
                            <router-link :to="{name: 'TeamView', params: {teamId: teamMember.team.id}}">{{teamMember.team.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.teamMember.player')">Player</span>
                    </dt>
                    <dd>
                        <div v-if="teamMember.player">
                            <router-link :to="{name: 'PlayerView', params: {playerId: teamMember.player.id}}">{{teamMember.player.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="teamMember.id" :to="{name: 'TeamMemberEdit', params: {teamMemberId: teamMember.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./team-member-details.component.ts">
</script>
