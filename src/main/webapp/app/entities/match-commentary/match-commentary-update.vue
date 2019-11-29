<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="registatsApp.matchCommentary.home.createOrEditLabel" v-text="$t('registatsApp.matchCommentary.home.createOrEditLabel')">Create or edit a MatchCommentary</h2>
                <div>
                    <div class="form-group" v-if="matchCommentary.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="matchCommentary.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.title')" for="match-commentary-title">Title</label>
                        <input type="text" class="form-control" name="title" id="match-commentary-title"
                            :class="{'valid': !$v.matchCommentary.title.$invalid, 'invalid': $v.matchCommentary.title.$invalid }" v-model="$v.matchCommentary.title.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.description')" for="match-commentary-description">Description</label>
                        <input type="text" class="form-control" name="description" id="match-commentary-description"
                            :class="{'valid': !$v.matchCommentary.description.$invalid, 'invalid': $v.matchCommentary.description.$invalid }" v-model="$v.matchCommentary.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.commentaryType')" for="match-commentary-commentaryType">Commentary Type</label>
                        <select class="form-control" name="commentaryType" :class="{'valid': !$v.matchCommentary.commentaryType.$invalid, 'invalid': $v.matchCommentary.commentaryType.$invalid }" v-model="$v.matchCommentary.commentaryType.$model" id="match-commentary-commentaryType" >
                            <option value="HIGHLIGHT" v-bind:label="$t('registatsApp.CommentaryType.HIGHLIGHT')">HIGHLIGHT</option>
                            <option value="FULL" v-bind:label="$t('registatsApp.CommentaryType.FULL')">FULL</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.minute')" for="match-commentary-minute">Minute</label>
                        <input type="number" class="form-control" name="minute" id="match-commentary-minute"
                            :class="{'valid': !$v.matchCommentary.minute.$invalid, 'invalid': $v.matchCommentary.minute.$invalid }" v-model.number="$v.matchCommentary.minute.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.logDate')" for="match-commentary-logDate">Log Date</label>
                        <div class="d-flex">
                            <input id="match-commentary-logDate" type="datetime-local" class="form-control" name="logDate" :class="{'valid': !$v.matchCommentary.logDate.$invalid, 'invalid': $v.matchCommentary.logDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.matchCommentary.logDate.$model)"
                            @change="updateInstantField('logDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.playerStatistic')" for="match-commentary-playerStatistic">Player Statistic</label>
                        <select class="form-control" id="match-commentary-playerStatistic" name="playerStatistic" v-model="matchCommentary.playerStatistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchCommentary.playerStatistic && playerStatisticItemOption.id === matchCommentary.playerStatistic.id ? matchCommentary.playerStatistic : playerStatisticItemOption" v-for="playerStatisticItemOption in playerStatistics" :key="playerStatisticItemOption.id">{{playerStatisticItemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('registatsApp.matchCommentary.matchStatistic')" for="match-commentary-matchStatistic">Match Statistic</label>
                        <select class="form-control" id="match-commentary-matchStatistic" name="matchStatistic" v-model="matchCommentary.matchStatistic">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchCommentary.matchStatistic && matchStatisticItemOption.id === matchCommentary.matchStatistic.id ? matchCommentary.matchStatistic : matchStatisticItemOption" v-for="matchStatisticItemOption in matchStatistics" :key="matchStatisticItemOption.id">{{matchStatisticItemOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchCommentary.team')" for="match-commentary-team">Team</label>
                        <select class="form-control" id="match-commentary-team" name="team" v-model="matchCommentary.team">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchCommentary.team && teamOption.id === matchCommentary.team.id ? matchCommentary.team : teamOption" v-for="teamOption in teams" :key="teamOption.id">{{teamOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchCommentary.player')" for="match-commentary-player">Player</label>
                        <select class="form-control" id="match-commentary-player" name="player" v-model="matchCommentary.player">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchCommentary.player && playerOption.id === matchCommentary.player.id ? matchCommentary.player : playerOption" v-for="playerOption in players" :key="playerOption.id">{{playerOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('registatsApp.matchCommentary.match')" for="match-commentary-match">Match</label>
                        <select class="form-control" id="match-commentary-match" name="match" v-model="matchCommentary.match">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="matchCommentary.match && matchOption.id === matchCommentary.match.id ? matchCommentary.match : matchOption" v-for="matchOption in matches" :key="matchOption.id">{{matchOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.matchCommentary.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./match-commentary-update.component.ts">
</script>
