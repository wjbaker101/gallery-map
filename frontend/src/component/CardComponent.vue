<template>
    <div class="card-component" :class="{ [location]: true, [`highlight-${highlight}`]: true }">
        <slot />
    </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';

export default defineComponent({
    name: 'CardComponent',

    props: {
        location: {
            type: String as PropType<'top-left' | 'top-right' | 'bottom-left' | 'bottom-right' | 'none'>,
            required: false,
            default: 'top-left',
        },
        highlight: {
            type: String as PropType<'top' | 'right' | 'bottom' | 'left' | 'none'>,
            required: false,
            default: 'none',
        },
    },

    setup() {},
});
</script>

<style lang="scss">
@use '@/style/variables.scss' as vars;

.card-component {
    position: relative;
    padding: 1rem;
    position: fixed;
    background-color: #efefef;
    z-index: 2;

    @include vars.border-radius;
    @include vars.shadow-large;

    $offset: 1rem;

    &.top-left {
        top: $offset;
        left: $offset;
    }

    &.top-right {
        top: $offset;
        right: $offset;
    }

    &.bottom-left {
        bottom: $offset;
        left: $offset;
    }

    &.bottom-right {
        bottom: $offset;
        right: $offset;
    }

    $highlight-thickness: 3px;

    &.highlight-top {
        border-top: $highlight-thickness solid #176bc0;
        border-top-right-radius: 0;
        border-top-left-radius: 0;
    }

    &.highlight-right {
        border-right: $highlight-thickness solid #176bc0;
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
    }

    &.highlight-bottom {
        border-bottom: $highlight-thickness solid #176bc0;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
    }

    &.highlight-left {
        border-left: $highlight-thickness solid #176bc0;
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
    }

    & > :first-child {
        margin-top: 0;
    }

    & > :last-child {
        margin-bottom: 0;
    }
}
</style>