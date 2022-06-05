<template>
    <MapComponent :map="map">
        <template v-if="map !== null">
            <AlbumMarkerComponent
                :key="album.id"
                v-for="album in albumsToDisplay"
                :map="map"
                :album="album"
                @open="onAlbumOpen"
            />
            <CardComponent location="bottom-right" highlight="right">
                <strong>Longitude:</strong> {{ mouseLongitude.toFixed(4) }}
                <strong>Latitude:</strong> {{ mouseLatitude.toFixed(4) }}
                <strong>Zoom:</strong> {{ zoomLevel.toFixed(1) }}
            </CardComponent>
            <CardComponent location="top-left" highlight="left">
                <h3>Jump to:</h3>
                <div @click="onJumpToLocation(-26.6, 66.8, 43.0, 33.0)">Europe</div>
                <div @click="onJumpToLocation(65.8, 46.3, 158.1, -12.5)">Asia</div>
            </CardComponent>
        </template>
    </MapComponent>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, ref } from 'vue';
import { Map } from 'mapbox-gl';

import AlbumMarkerComponent from '@/component/album/AlbumMarkerComponent.vue';
import MapComponent from '@/component/map/MapComponent.vue';
import CardComponent from '@/component/CardComponent.vue';

import { config } from '@/config/config';
import { useAppData } from '@/use/appData.use';
import { useEvents } from '@/use/events.use';

import { IAlbum } from '@/model/album.model';

export default defineComponent({
    name: 'MapView',

    components: {
        AlbumMarkerComponent,
        MapComponent,
        CardComponent,
    },

    setup() {
        const appData = useAppData();
        const events = useEvents();

        const albums = appData.albums.value;
        const albumsToDisplay = computed(() => {
            return albums.value?.filter(x => x.photos.length > 0) ?? null;
        });

        const map = ref<Map | null>(null);

        const mouseLongitude = ref<number>(0);
        const mouseLatitude = ref<number>(0);
        const zoomLevel = ref<number>(0);

        const currentAlbum = ref<IAlbum | null>(null);

        onMounted(() => {
            map.value = new Map({
                container: 'map',
                accessToken: config.mapbox.accessToken,
                style: 'mapbox://styles/mapbox/satellite-streets-v11',
                attributionControl: false,
                zoom: 4,
                center: [0, 0],
            });

            map.value.fitBounds([-165, 82, 190, -60]);

            map.value.on('mousemove', (event) => {
                mouseLongitude.value = event.lngLat.lng;
                mouseLatitude.value = event.lngLat.lat;
            });

            map.value.on('zoom', () => {
                zoomLevel.value = map.value?.getZoom() ?? 0;
            });
        });

        return {
            albumsToDisplay,
            map,
            mouseLongitude,
            mouseLatitude,
            zoomLevel,
            currentAlbum,

            onJumpToLocation(longitude1: number, latitude1: number, longitude2: number, latitude2: number) {
                map.value?.fitBounds([ longitude1, latitude1, longitude2, latitude2 ]);
            },

            onAlbumOpen(album: IAlbum) {
                // currentAlbum.value = album;
                events.publish('show-modal', {
                    name: 'view-album',
                    style: 'filled',
                    props: {
                        album,
                    },
                });
            },
        };
    },
});
</script>

<style lang="scss">
</style>