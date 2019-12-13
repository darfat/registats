<template>
    <div >
        <div class="row justify-content-center">
        <h1>GO GO, 
            <span v-if="team">{{team.name}}</span>
        </h1>
        </div>
        <div class="row pad">
          <button type="button"  class="btn btn-success" v-on:click="saveLineup()">Save Lineup</button>
          <button type="button"  class="btn btn-info" v-on:click="lineupReady()">Ready!</button>
          <button type="button"  class="btn btn-primary" v-on:click="changeView('completeColumns')">Complete View</button>
          <button type="button"  class="btn btn-primary" v-on:click="changeView('minimumColumns')">Minimalistic View</button>
          <button type="button"  class="btn btn-primary" disabled="true" >Formation View</button>
        </div>
        <div class="row">
          <div class="col-sm-5">
             <Grid :style="{height: '100%'}"
                :data-items="lineups"
                :columns="columns"
                  :edit-field="'inEdit'"
                @rowclick="rowClick"  
                @itemchange="itemChange"
                >                
              </Grid>
          </div>
          <div class="col-sm-7" v-if="statsEnable">
            
            <div class="row justify-content-center" v-if="teamInfo">
                <div class="col-sm">
                   <stats-team 
                   :team="teamInfo"  
                  :label="'Corner'" 
                  :name="'CORNER_KICK'" ></stats-team>
                </div> 
                <div class="col-sm">
                   <!-- <stats-team></stats-team> -->
                </div>  
            </div>
            <div class="row">
              <h2 v-if="playerPicked">{{playerPicked.fullName}}</h2>
              <h2 v-else>No Player Selected</h2>
            </div>
            <div class="row justify-content-center">
              <div class="col-sm">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    DEFENSE
                  </div>
                </div>
                <div class="row justify-content-center">
                    <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Tackle'" 
                  :labelSuccess="'Win'" 
                  :labelFailed="'Missed'" 
                  :nameSuccess="'TACKLE_WIN'" 
                  :nameFailed="'TACKLE_MISSED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                    <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Intercepts / Clearences'" 
                  :labelSuccess="'Intercept'" 
                  :labelFailed="'Clearence'" 
                  :nameSuccess="'INTERCEPT'" 
                  :nameFailed="'CLEARENCE'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Save'" 
                  :labelSuccess="'Save'" 
                  :labelFailed="''" 
                  :nameSuccess="'SAVE'" 
                  :nameFailed="null" 
                  ></stats-player>
                </div>                
                <div class="row justify-content-center">
                    <!-- <stats-player :player="playerPicked" :label="'Cross'" ></stats-player> -->
                </div>
              </div>
              <div class="col-sm">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    GENERAL
                  </div>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Pass'" 
                  :labelSuccess="'Success'" 
                  :labelFailed="'Failed'" 
                  :nameSuccess="'PASS_SUCCESS'" 
                  :nameFailed="'PASS_MISSED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Foul'" 
                  :labelSuccess="'Foul'" 
                  :labelFailed="'Fouled'" 
                  :nameSuccess="'FOUL'" 
                  :nameFailed="'FOULED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Cross'" 
                  :labelSuccess="'Success'" 
                  :labelFailed="'Failed'" 
                  :nameSuccess="'CROSS_SUCCESS'" 
                  :nameFailed="'CROSS_FAILED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Offside'" 
                  :labelSuccess="'Offside'" 
                  :labelFailed="''" 
                  :nameSuccess="'OFFSIDE'" 
                  :nameFailed="null" 
                  ></stats-player>
                </div>
              </div>
              <div class="col-sm">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    ATTACK
                  </div>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Shoot'" 
                  :labelSuccess="'On Target'" 
                  :labelFailed="'Off Target'" 
                  :nameSuccess="'SHOOT_ON_TARGET'" 
                  :nameFailed="'SHOOT_OFF_TARGET'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Goal'" 
                  :labelSuccess="'Goal'" 
                  :labelFailed="'Assist'" 
                  :nameSuccess="'GOAL'" 
                  :nameFailed="'ASSIST'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Penalty'" 
                  :labelSuccess="'Success'" 
                  :labelFailed="'Missed'" 
                  :nameSuccess="'PENALTY_SUCCESS'" 
                  :nameFailed="'PENALTY_MISSED'" 
                  ></stats-player>
                </div>
              </div>
            </div>
            <div class="row justify-content-center">              
                <!-- <stats-location></stats-location> -->
            </div>
          </div>
        </div>

       
        <div class="row justify-content-center">
            <!-- <button type="button"  class="btn btn-primary" v-on:click="commitData()">Commit Data</button> -->
        </div>
    </div>
</template>

<script lang="ts" src="./workstats-team.component.ts">
</script>