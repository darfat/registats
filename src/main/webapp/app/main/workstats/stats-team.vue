<template>
    <div class="container-fluid">
        <div class="col-sm">
            <div class="row justify-content-center">
                <div class="col-sm"  v-if="name">
                    <button type="button" class="btn btn-success" 
                    :disabled="team === null"
                    v-on:click="add()">
                        {{label}}
                    </button>
                </div>                
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stats-team.component.ts">
</script>