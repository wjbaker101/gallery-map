<template>
    <h2>Create Album</h2>
    <label>
        <strong>Title</strong>
        <input type="text" v-model="title">
    </label>
    <button @click="onCreate">Create</button>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

import { useAppData } from '@/use/appData.use';

export default defineComponent({
    name: 'NewAlbumModalComponent',

    setup() {
        const appData = useAppData();

        const title = ref<string>('');

        return {
            title,

            async onCreate() {
                await appData.albums.add({
                    title: title.value,
                });
            },
        };
    },
});
</script>

<style lang="scss">
</style>