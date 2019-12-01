<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <h3>Location</h3>
        </div>
        <div class="row justify-content-center">
            <div class="col-sm">
                <button type="button" class="btn btn-success" 
                    v-on:click="onLocation('ONE')">
                        Z1
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./stats-location.component.ts">
</script>