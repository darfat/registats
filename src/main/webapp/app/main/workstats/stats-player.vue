<template>
    <div class="container-fluid">
        <div class="col-sm">
            <div class="row justify-content-center">
                <div class="col-sm">
                    {{label}}
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-sm">
                    <button type="button" class="btn btn-success" 
                    :disabled="player === null"
                    v-on:click="success()">
                        {{labelSuccess}}
                    </button>
                </div>
                <div class="col-sm">
                    <button type="button" class="btn btn-danger"
                    :disabled="player === null"
                    v-on:click="missed()">
                        {{labelFailed}}
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stats-player.component.ts">
</script>