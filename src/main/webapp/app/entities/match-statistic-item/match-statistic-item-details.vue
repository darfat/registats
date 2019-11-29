<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="matchStatisticItem">
                <h2 class="jh-entity-heading"><span v-text="$t('registatsApp.matchStatisticItem.detail.title')">MatchStatisticItem</span> {{matchStatisticItem.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('registatsApp.matchStatisticItem.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{matchStatisticItem.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatisticItem.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{matchStatisticItem.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('registatsApp.matchStatisticItem.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{matchStatisticItem.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="matchStatisticItem.id" :to="{name: 'MatchStatisticItemEdit', params: {matchStatisticItemId: matchStatisticItem.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./match-statistic-item-details.component.ts">
</script>
