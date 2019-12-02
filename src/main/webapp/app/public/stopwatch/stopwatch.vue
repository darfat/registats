<template>
    <div>
        <div class="row justify-content-center pad">
        <h3 class="time">{{ time }}</h3>
        </div>
        <div class="row justify-content-center pad">
            <button type="button"  class="btn btn-success pad" v-on:click="start()">Start</button>
            <button type="button"  class="btn btn-warning pad" v-on:click="stop()">Stop</button>
            <button type="button"  class="btn btn-danger pad" v-on:click="reset()">Clear</button>
        </div>
    </div>
</template>

<script lang="ts" src="./stopwatch.component.ts">
</script>