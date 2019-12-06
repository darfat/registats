<template>
    <div >
        <div class="row justify-content-center">
        <h1>GO GO, 
            <span v-if="team">{{team.name}}</span>
        </h1>
        </div>
        <div class="row pad">
          <button type="button"  class="btn btn-success" v-on:click="saveLineup()">Save Lineup</button>
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
          <div class="col-sm-7">
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
              </div>
            </div>
            <div class="row justify-content-center">              
                <!-- <stats-location></stats-location> -->
            </div>
          </div>
        </div>

       
        <div class="row justify-content-center">
            <button type="button"  class="btn btn-primary" v-on:click="commitData()">Commit Data</button>
        </div>
    </div>
</template>

<script lang="ts" src="./workstats-team.component.ts">
</script>