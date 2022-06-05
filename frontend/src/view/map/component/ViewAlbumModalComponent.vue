<template>
    <div class="view-album-modal-component flex flex-vertical align-items-center gap">
        <div class="photo-container flex text-centered">
            <img v-if="image !== null && !isLoadingPhoto" class="main-photo flex-auto" :src="image" @mousemove="onMouseEnter">
            <div class="main-photo-loading" v-else>
                <LoadingComponent />
            </div>
            <div class="title-text" :class="{ 'is-visible': isAlbumTitleVisible }">
                {{ currentPhoto.title }}
            </div>
        </div>
        <div v-if="album.photos.length > 1" class="album-container flex-auto flex">
            <div
                :key="photo.id"
                v-for="photo in album.photos"
                class="album-photo flex-auto"
                @click="onPhotoClick(photo)"
            >
                <img :src="photo.thumbnailUrl">
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from 'vue';

import LoadingComponent from '@/component/LoadingComponent.vue';

import { IAlbum } from '@/model/album.model';
import { IPhoto } from '@/model/photo.model';

export default defineComponent({
    name: 'AlbumModalComponent',

    components: {
        LoadingComponent,
    },

    props: {
        album: {
            type: Object as PropType<IAlbum>,
            required: true,
        },
    },

    setup(props) {
        const currentPhoto = ref<IPhoto | null>(null);
        const image = ref<string | null>(null);
        const isLoadingPhoto = ref<boolean>(false);

        const imageElement = new Image();
        imageElement.onload = function () {
            image.value = imageElement.src;
            isLoadingPhoto.value = false;
        };

        const setPhoto = function (photo: IPhoto) {
            currentPhoto.value = photo;
            imageElement.src = currentPhoto.value.url;
            isLoadingPhoto.value = true;
        };

        setPhoto(props.album.photos[0]);

        const isAlbumTitleVisible = ref<boolean>(false);

        const event = ref<number | null>(null);

        return {
            currentPhoto,
            image,
            isLoadingPhoto,
            isAlbumTitleVisible,

            onMouseEnter() {
                if (event.value !== null) {
                    clearTimeout(event.value);
                }

                isAlbumTitleVisible.value = true;

                event.value = setTimeout(() => {
                    isAlbumTitleVisible.value = false;
                }, 4000);
            },

            onPhotoClick(photo: IPhoto) {
                setPhoto(photo);
            },
        };
    },
});
</script>

<style lang="scss">
@use '@/style/variables' as vars;

.view-album-modal-component {
    height: 100%;

    .photo-container {
        position: relative;
        min-height: 0;
    }

    .title-text {
        position: absolute;
        bottom: 1rem;
        left: 50%;
        padding: 1rem;
        line-height: 1em;
        background-color: rgba(0, 0, 0, 0.5);
        color: #fff;
        text-shadow: 1px 1px 1px rgba(0, 0, 0, 1);
        transform: translateX(-50%);
        pointer-events: none;
        opacity: 0;

        @include vars.border-radius;
        @include vars.shadow-medium;

        &.is-visible {
            pointer-events: all;
            opacity: 1;
        }
    }

    .main-photo {
        width: auto;
        max-height: 100%;
        margin: auto;

        @include vars.shadow-medium;
    }

    .main-photo-loading {
        margin: auto;
    }

    .album-container {
        margin: -1rem;
        padding: 1rem;
        max-width: 100%;
        overflow-x: auto;
    }

    .album-photo {
        $border-width: 3px;

        width: 160px;
        height: 160px;
        margin: -$border-width;
        border: $border-width dashed transparent;
        cursor: pointer;

        @include vars.border-radius;

        & + .album-photo {
            margin-left: 1rem;
        }

        &:hover {
            border: $border-width dashed #176bc0;
        }
    }

    img {
        @include vars.border-radius;
        @include vars.shadow-small;
    }
}
</style>