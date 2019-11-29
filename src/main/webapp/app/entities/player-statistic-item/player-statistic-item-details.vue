<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="playerStatisticItem">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.playerStatisticItem.detail.title')">PlayerStatisticItem</span> {{playerStatisticItem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.playerStatisticItem.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{playerStatisticItem.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerStatisticItem.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{playerStatisticItem.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.playerStatisticItem.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{playerStatisticItem.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="playerStatisticItem.id" :to="{name: 'PlayerStatisticItemEdit', params: {playerStatisticItemId: playerStatisticItem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./player-statistic-item-details.component.ts">
</script>
