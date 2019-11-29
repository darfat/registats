<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="row">
                <div class="col-sm-12">
                    <div v-if="match">
                        <div>
                            <span v-if="match.homeTeam">{{match.homeTeam.name}}</span>
                            <button type="button" id="pick-home" class="btn btn-primary" v-on:click="pick('home')">
                                <font-awesome-icon icon="plus"></font-awesome-icon>
                            </button>
                        </div>
                        <div>
                            <span v-if="match.awayTeam">{{match.awayTeam.name}}</span>
                            <button type="button" id="pick-away" class="btn btn-primary" v-on:click="pick('away')">
                                <font-awesome-icon icon="plus"></font-awesome-icon>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row justify-content-center" v-if="match">
            <workstats-team v-if="side === 'home' && match.homeTeam" :team="match.homeTeam" :teamInfo="homeTeamInfo"></workstats-team>
        </div>
    </div>
</template>

<script lang="ts" src="./workstats.component.ts">
</script>