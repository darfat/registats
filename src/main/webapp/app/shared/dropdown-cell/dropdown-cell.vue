<template>
    <div>
        <td v-if="!dataItem.inEdit" :class="className" width="120px">{{ dataItem[field]}}</td>
                <td v-else><select class="form-control" @change="change" v-model="dataItem[field]">
                <option v-for="opt in options" :key="opt">{{opt}}</option>
             </select></td>
    </div>
</template>

<script lang="ts" src="./dropdown-cell.component.ts">
</script>