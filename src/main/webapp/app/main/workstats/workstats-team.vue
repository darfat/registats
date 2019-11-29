<template>
    <div class="row justify-content-center">
        <div>
        <h1>Hello Team, 
            <span v-if="team">{{team.name}}</span>
        </h1>
        </div>
        <Grid :style="{height: '280px'}"
          :data-items="lineups"
          :columns="columns"
            :edit-field="'inEdit'"
          @rowclick="rowClick"
          @itemchange="itemChange"
          >
        </Grid>
        <div>
            <button type="button"  class="btn btn-primary" v-on:click="showData()">Show Data</button>
        </div>
    </div>
</template>

<script lang="ts" src="./workstats-team.component.ts">
</script>