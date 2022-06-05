<template>
    <details class="album-details">
        <summary class="flex gap">
            <div class="flex-auto">
                <IconComponent class="open" icon="arrow-triangle-right" />
                <IconComponent class="closed" icon="arrow-triangle-down" />
            </div>
            <div>{{ album.title }}</div>
            <div class="flex-auto">({{ album.photos.length }})</div>
        </summary>
        <VueDraggable class="photos-container flex gap-small" tag="div" v-model="displayPhotos" item-key="id">
            <template #header>
                <div class="add-photo photo-container flex-auto flex align-items-center" @click="onAddPhoto(album)">
                    <div class="flex-auto text-centered">+<br>Photo</div>
                </div>
            </template>
            <template #item="{element}">
                <div class="photo-container flex-auto">
                    <img :src="element.thumbnailUrl">
                    <DeleteButtonComponent class="delete" onlyIcon @delete="onDeletePhoto(album, element)" />
                </div>
            </template>
        </VueDraggable>
    </details>
</template>

<script lang="ts">
import { computed, defineComponent, PropType } from 'vue';
import VueDraggable from 'vuedraggable';

import { useAppData } from '@/use/appData.use';
import { useEvents } from '@/use/events.use';

import { IAlbum } from '@/model/album.model';
import { IPhoto } from '@/model/photo.model';

export default defineComponent({
    name: 'AlbumDetailsComponent',

    props: {
        album: {
            type: Object as PropType<IAlbum>,
            required: true,
        },
    },

    components: {
        VueDraggable,
    },

    setup(props) {
        const appData = useAppData();
        const events = useEvents();

        const displayPhotos = computed({
            get() {
                return props.album.photos;
            },
            async set(value: Array<IPhoto>) {
                const request: Record<number, number> = {};
                let index = 1;

                for (const key of value) {
                    request[key.id] = index++;
                }

                appData.photos.reorder(props.album.id, request);
            },
        });

        return {
            displayPhotos,

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

.album-details {
    $border-width: 3px;

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