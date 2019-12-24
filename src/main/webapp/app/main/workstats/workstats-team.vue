<template>
    <div >
        <div class="row justify-content-center">
        <span><b> 
            <span v-if="team">{{team.name}}</span>
            </b>
        </span>
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
            <div class="row">
              <span v-if="playerPicked"><b>{{playerPicked.fullName}}</b></span>
              <span v-else><b>No Player Selected</b></span>
            </div>
            <div class="row justify-content-center">
              <div class="col-sm-3">
                  <div class="row justify-content-center" v-if="teamInfo">
                    <div class="col-sm">
                      TEAM
                    </div>
                  </div>
                  <div class="row justify-content-center" >
                    <div class="row justify-content-center">
                      <div class="col-sm">
                        Corner
                      </div>
                    </div>
                        <stats-team 
                        :team="teamInfo"  
                        :label="'Corner'" 
                        :name="'CORNER_KICK'" >
                        </stats-team>
                  </div>  

                  <!-- individu -->   
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
                  <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Cards'" 
                  :labelSuccess="'Y'" 
                  :labelFailed="'R'" 
                  :nameSuccess="'YELLOW_CARD'" 
                  :nameFailed="'RED_CARD'" 
                  ></stats-player>
                </div>                 
              </div>
              <div class="col-sm-3">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    GENERAL
                  </div>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Pass'" 
                  :labelSuccess="'O'" 
                  :labelFailed="'X'" 
                  :nameSuccess="'PASS_SUCCESS'" 
                  :nameFailed="'PASS_MISSED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Foul(ed)'" 
                  :labelSuccess="'Fouled'" 
                  :labelFailed="'Foul'" 
                  :nameSuccess="'FOULED'" 
                  :nameFailed="'FOUL'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Cross'" 
                  :labelSuccess="'O'" 
                  :labelFailed="'X'" 
                  :nameSuccess="'CROSS_SUCCESS'" 
                  :nameFailed="'CROSS_FAILED'" 
                  ></stats-player>
                </div>
                
                
              </div>

              <div class="col-sm-3">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    DEFENSE
                  </div>
                </div>
                <div class="row justify-content-center">
                    <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Tackle'" 
                  :labelSuccess="'O'" 
                  :labelFailed="'X'" 
                  :nameSuccess="'TACKLE_WIN'" 
                  :nameFailed="'TACKLE_MISSED'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                    <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'In_ / Clear'" 
                  :labelSuccess="'In_'" 
                  :labelFailed="'Clear'" 
                  :nameSuccess="'INTERCEPT'" 
                  :nameFailed="'CLEARENCE'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Save'" 
                  :labelSuccess="'O'" 
                  :labelFailed="''" 
                  :nameSuccess="'SAVE'" 
                  :nameFailed="null" 
                  ></stats-player>
                </div>                
                <div class="row justify-content-center">
                    <!-- <stats-player :player="playerPicked" :label="'Cross'" ></stats-player> -->
                </div>
              </div>
              
              <div class="col-sm-3">
                <div class="row justify-content-center">
                  <div class="col-sm">
                    ATTACK
                  </div>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Shoot'" 
                  :labelSuccess="'ON'" 
                  :labelFailed="'OFF'" 
                  :nameSuccess="'SHOOT_ON_TARGET'" 
                  :nameFailed="'SHOOT_OFF_TARGET'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Goal'" 
                  :labelSuccess="'Goal'" 
                  :labelFailed="'Ast'" 
                  :nameSuccess="'GOAL'" 
                  :nameFailed="'ASSIST'" 
                  ></stats-player>
                </div>
                <div class="row justify-content-center">
                  <stats-player
                  :player="lineupPlayerPicked"  
                  :label="'Penalty'" 
                  :labelSuccess="'O'" 
                  :labelFailed="'X'" 
                  :nameSuccess="'PENALTY_SUCCESS'" 
                  :nameFailed="'PENALTY_MISSED'" 
                  ></stats-player>
                </div>
                
              </div>
            </div>
            <div class="row justify-content-center" >  
              <div class="col-sm-12">            
                 <stats-location></stats-location>
              </div>
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