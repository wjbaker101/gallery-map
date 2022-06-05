<template>
    <div ref="element" class="map-marker-component">
        <slot />
    </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { Map, Marker } from 'mapbox-gl';

export default defineComponent({
    name: 'MapMarkerComponent',

    props: {
        map: {
            type: Map,
            required: true,
        },
        longitude: {
            type: Number,
            required: true,
        },
        latitude: {
            type: Number,
            required: true,
        },
    },

    setup(props) {
        const element = ref<HTMLDivElement>();

        onMounted(() => {
            new Marker({
                element: element.value,
                anchor: 'bottom',
            })
            .setLngLat([props.longitude, props.latitude])
            .addTo(props.map);
        });

        return {
            element,
        };
    },
});
</script>

<style lang="scss">
@use '@/style/variables' as vars;

.map-marker-component {
    $pointer-size: 0.75rem;

    padding: 3px;
    background-color: #efefef;
    border-top: 4px solid #176bc0;
    top: -$pointer-size;
    cursor: pointer;

    @include vars.border-bottom-right-radius;
    @include vars.border-bottom-left-radius;
    @include vars.shadow-medium;

    &::after {
        width: $pointer-size;
        height: $pointer-size;
        content: '';
        position: absolute;
        bottom: $pointer-size * -0.5;
        left: 50%;
        background-color: #efefef;
        transform: translateX(-50%) rotate(45deg);
        border-radius: 0.15rem;
        clip-path: polygon(0 100%, 100% 0, 100% 100%, 0% 100%);
        z-index: 1;

        @include vars.shadow-medium;
    }

    & > img {
        vertical-align: middle;

        @include vars.border-radius;
    }
}
</style>