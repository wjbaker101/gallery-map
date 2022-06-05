<template>
    <MapMarkerComponent
        :map="map"
        :longitude="album.photos[0].longitude"
        :latitude="album.photos[0].latitude"
        @click="$emit('open', album)"
    >
        <img :src="album.photos[0].thumbnailUrl" :width="size" :height="size">
    </MapMarkerComponent>
</template>

<script lang="ts">
import { defineComponent, PropType, ref } from 'vue';
import { Map } from 'mapbox-gl';

import MapMarkerComponent from '@/component/map/MapMarkerComponent.vue';

import { IAlbum } from '@/model/album.model';

export default defineComponent({
    name: 'AlbumMarkerComponent',

    components: {
        MapMarkerComponent,
    },

    props: {
        map: {
            type: Map,
            required: true,
        },
        album: {
            type: Object as PropType<IAlbum>,
            required: true,
        },
    },

    emits: [ 'open' ],

    setup(props) {
        const size = ref<number>(160);

        props.map.on('zoom', () => {
            size.value =(props.map.getZoom() < 6) ? 40 : 160;
        });

        return {
            size,
        };
    },
});
</script>

<style lang="scss">
</style>