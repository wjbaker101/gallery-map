<template>
    <div class="loading-component">
        <svg
            xmlns="http://www.w3.org/2000/svg"
            width="80px"
            height="80px"
            viewBox="0 0 100 100"
        >
            <circle cx="50" cy="50" fill="none" stroke="#176bc0" stroke-width="11" r="35" stroke-dasharray="100 70" stroke-linecap="round">
                <animateTransform attributeName="transform" type="rotate" repeatCount="indefinite" dur="1.2s" values="0 50 50;360 50 50" keyTimes="0;1"></animateTransform>
            </circle>
        </svg>
        <small v-if="hasText"><strong>LOADING</strong></small>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'LoadingComponent',

    props: {
        hasText: {
            type: Boolean,
            required: false,
            default: true,
        },
    },

    setup() {
        return {};
    },
});
</script>

<style lang="scss">
.loading-component {
    svg {
        display: block;
    }
}
</style>