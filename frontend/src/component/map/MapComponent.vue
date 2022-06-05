<template>
    <div id="map">
        <slot />
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { Map } from 'mapbox-gl';

export default defineComponent({
    name: 'MapComponent',

    props: {
        map: {
            type: Map,
            required: true,
        },
    },

    setup() {
        return {};
    },
});
</script>

<style lang="scss">
@import '~mapbox-gl/dist/mapbox-gl.css';

#map {
    height: 100%;
}
</style>