<template>
    <CardComponent class="admin-view">
        <h2>Admin</h2>
        <ButtonComponent class="add-album primary" @click="onCreateAlbum">
            <IconComponent icon="plus" gap="right" />
            <span>Album</span>
        </ButtonComponent>
        <details class="album-container" :key="album.id" v-for="album in albums">
            <summary class="flex gap">
                <div class="flex-auto">
                    <IconComponent class="open" icon="arrow-triangle-right" />
                    <IconComponent class="closed" icon="arrow-triangle-down" />
                </div>
                <div>{{ album.title }}</div>
                <div class="flex-auto">({{ album.photos.length }})</div>
            </summary>
            <div class="photos-container flex gap-small">
                <div class="add-photo photo-container flex-auto flex align-items-center" @click="onAddPhoto(album)">
                    <div class="flex-auto text-centered">+<br>Photo</div>
                </div>
                <div class="photo-container flex-auto" :key="photo.id" v-for="photo in album.photos">
                    <img :src="photo.thumbnailUrl">
                    <DeleteButtonComponent class="delete" onlyIcon @delete="onDeletePhoto(album, photo)" />
                </div>
            </div>
        </details>
    </CardComponent>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

import CardComponent from '@/component/CardComponent.vue';

import { useAppData } from '@/use/appData.use';
import { useEvents } from '@/use/events.use';

import { IAlbum } from '@/model/album.model';
import { IPhoto } from '@/model/photo.model';

export default defineComponent({
    name: 'AdminView',

    components: {
        CardComponent,
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

            onAddPhoto(album: IAlbum) {
                events.publish('show-modal', {
                    name: 'new-photo',
                    props: {
                        album,
                    },
                });
            },

            async onDeletePhoto(album: IAlbum, photo: IPhoto) {
                await appData.photos.delete(album.id, photo.id);
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

    $border-width: 3px;

    .add-album {
        display: table;
        margin: 0 auto 1rem auto;
    }

    .album-container {
        summary {
            padding: 0.5rem;
            margin-bottom: 0.5rem;
            cursor: pointer;
            border: $border-width dashed transparent;
            user-select: none;

            @include vars.border-radius;
            @include vars.shadow-small;

            &:hover {
                border: $border-width dashed #176bc0;
            }
        }

        .closed {
            display: none;
        }

        &[open] {
            .closed {
                display: unset;
            }

            .open {
                display: none;
            }
        }

        & + .album-container {
            margin-top: 1rem;
        }
    }

    .photos-container {
        flex-wrap: wrap;
    }

    .photo-container {
        width: 160px;
        height: 160px;
        position: relative;
        border: $border-width dashed transparent;

        @include vars.border-radius;

        img {
            @include vars.border-radius;
            @include vars.shadow-small;
        }

        &:hover {
            border: $border-width dashed #176bc0;
        }

        .delete {
            position: absolute;
            bottom: 0.5rem;
            right: 0.5rem;
        }
    }

    .add-photo {
        cursor: pointer;

        @include vars.shadow-small;

        & > div {
            margin: auto;
        }
    }
}
</style>