<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="player">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.player.detail.title')">Player</span> {{player.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.player.firstName')">First Name</span>
                    </dt>
                    <dd>
                        <span>{{player.firstName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.lastName')">Last Name</span>
                    </dt>
                    <dd>
                        <span>{{player.lastName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.fullName')">Full Name</span>
                    </dt>
                    <dd>
                        <span>{{player.fullName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.email')">Email</span>
                    </dt>
                    <dd>
                        <span>{{player.email}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.phoneNumber')">Phone Number</span>
                    </dt>
                    <dd>
                        <span>{{player.phoneNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.instagram')">Instagram</span>
                    </dt>
                    <dd>
                        <span>{{player.instagram}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.photo')">Photo</span>
                    </dt>
                    <dd>
                        <span>{{player.photo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.idCard')">Id Card</span>
                    </dt>
                    <dd>
                        <span>{{player.idCard}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.address')">Address</span>
                    </dt>
                    <dd>
                        <span>{{player.address}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.birthPlace')">Birth Place</span>
                    </dt>
                    <dd>
                        <span>{{player.birthPlace}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.birthDate')">Birth Date</span>
                    </dt>
                    <dd>
                        <span>{{player.birthDate | formatDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.player.position')">Position</span>
                    </dt>
                    <dd>
                        <div v-if="player.position">
                            <router-link :to="{name: 'PositionView', params: {positionId: player.position.id}}">{{player.position.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="player.id" :to="{name: 'PlayerEdit', params: {playerId: player.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-details.component.ts">
</script>
