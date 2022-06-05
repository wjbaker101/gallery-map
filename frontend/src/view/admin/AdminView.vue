<template>
    <CardComponent class="admin-view">
        <h2>Admin</h2>
        <ButtonComponent class="add-album primary" @click="onCreateAlbum">
            <IconComponent icon="plus" gap="right" />
            <span>Album</span>
        </ButtonComponent>
        <AlbumDetailsComponent :key="album.id" v-for="album in albums" :album="album" />
    </CardComponent>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

import CardComponent from '@/component/CardComponent.vue';
import AlbumDetailsComponent from '@/view/admin/component/AlbumDetailsComponent.vue';

import { useAppData } from '@/use/appData.use';
import { useEvents } from '@/use/events.use';

export default defineComponent({
    name: 'AdminView',

    components: {
        CardComponent,
        AlbumDetailsComponent,
    },

    setup() {
        const appData = useAppData();
        const events = useEvents();

        const albums = appData.albums.value;

        const isCreatingNewAlbum = ref<boolean>(false);
        const isAddingNewPhoto = ref<boolean>(false);

        return {
            albums,
            isCreatingNewAlbum,
            isAddingNewPhoto,

            onCreateAlbum() {
                events.publish('show-modal', {
                    name: 'new-album',
                });
            },
        };
    },
});
</script>

<style lang="scss">
@use '@/style/variables' as vars;

.admin-view {
    bottom: 1rem;
    right: 1rem;

    .add-album {
        display: table;
        margin: 0 auto 1rem auto;
    }
}
</style>