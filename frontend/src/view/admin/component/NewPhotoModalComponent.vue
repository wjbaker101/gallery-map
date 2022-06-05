<template>
    <h2>Add Photo</h2>
    <p>Adding to album: {{ album.title }}.</p>
    <label>
        <strong>Title</strong>
        <input type="text" v-model="title">
    </label>
    <p></p>
    <label>
        <strong>Photo</strong>
        <input ref="photoInput" type="file" @change="onPhotoUpload">
    </label>
    <p></p>
    <ButtonComponent class="primary" @click="onAdd">Add</ButtonComponent>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from 'vue';

import { useAppData } from '@/use/appData.use';

import { IAlbum } from '@/model/album.model';

export default defineComponent({
    name: 'NewPhotoModalComponent',

    props: {
        album: {
            type: Object as PropType<IAlbum>,
            required: true,
        },
    },

    setup(props) {
        const appData = useAppData();

        const photoInput = ref<HTMLInputElement | null>(null);

        const title = ref<string>('');

        const file = ref<Blob | null>(null);

        return {
            photoInput,
            title,
            file,

            onPhotoUpload() {
                if (!photoInput.value?.files)
                    return;

                file.value = photoInput.value.files[0];
            },

            async onAdd() {
                if (file.value === null)
                    return;

                await appData.photos.add(props.album.id, {
                    title: title.value,
                    file: file.value,
                });
            },
        };
    },
});
</script>

<style lang="scss">
</style>