<template>
    <div class="container-fluid">
        <div class="row justify-content-center"  v-if="match">
            <!-- <div class="row" > -->
                <div class="col-sm-6" v-on:click="pick('home')">                    
                        <div class="btn-primary">
                            <button type="button" id="pick-home" class="btn" >
                                <span style="color:green" v-if="homeReady"><font-awesome-icon icon="plus"></font-awesome-icon></span>
                                <span style="color:gray"  v-else><font-awesome-icon icon="plus"></font-awesome-icon></span>
                                <span v-if="match.homeTeam">
                                   {{match.homeTeam.name}}
                                </span>
                            </button>
                        </div>                                            
                </div>
                <div class="col-sm-6" v-on:click="pick('away')">
                    <div class="btn-danger">
                        <button type="button" id="pick-away" class="btn" >
                            <font-awesome-icon icon="plus"></font-awesome-icon>
                            <span v-if="match.awayTeam">{{match.awayTeam.name}}</span>
                        </button>
                    </div>
                </div>
            <!-- </div> -->
        </div>
        <div class="row justify-content-center pad"  >
            <button type="button" :disabled="sessionStarted" class="btn  btn-info" v-on:click="startSession()">Start Session</button>
        </div>
        <div class="row justify-content-center pad" v-if="sessionStarted">
            <div class="col-sm">
                <div class="row justify-content-md-center">
                    <button type="button" :disabled="!isTeamReady()" id="round1" :class="getClassRound1()" v-on:click="setRound1()"  >
                            1st Half
                    </button>
                    <button type="button" :disabled="!isTeamReady()" id="round2" :class="getClassRound2()" v-on:click="setRound2()" >
                            2nd Half
                    </button>
                </div>
            </div>
            <div class="col-sm">
                 <div class="row justify-content-md-center">
                    <button type="button" :disabled="!isTeamReady()" class="btn btn-success pad" v-on:click="start()">Start</button>
                    <button type="button" :disabled="!isTeamReady()" class="btn btn-warning pad" v-on:click="stop()">Pause</button>
                    <button type="button" :disabled="!isTeamReady()" class="btn btn-danger pad" v-on:click="reset()">Clear</button>            
                </div>    
            </div>
            <div class="col-sm">
                <div class="row justify-content-md-center" v-if="isTeamReady()">
                    <button type="button"  class="btn  btn-info" v-on:click="saveBeforeEndMatch()">Save Match</button>
                    <button type="button"  class="btn  btn-danger" v-on:click="endSession()">End Match</button>
                </div>
            </div>
        </div>
        <div class="row justify-content-center" v-if="sessionStarted">
            <div>
                <h4 class="time">{{ time }}</h4>
            </div>
        </div>    
           
        <div class="row justify-content-center" v-if="match && sessionStarted">
            <workstats-team v-if="side === 'home' && match.homeTeam" :team="match.homeTeam" :teamInfo="homeTeamInfo"></workstats-team>
        </div>
        <div class="row" v-if="match && match.commentaries">
            <div class="col-sm">
                <div class="row" v-for="commentary in match.commentaries" :key="commentary.idx">
                    <span><i>{{commentary.minute}}'  {{commentary.title}}</i></span>
                    <!-- <span><i>{{commentary.description}}</i></span> -->

                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./workstats.component.ts">
</script>