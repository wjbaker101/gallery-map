<template>
    <router-view />
    <ModalComponent />
</template>

<script lang="ts">
import { defineComponent } from 'vue';

import ModalComponent from '@/component/modal/ModalComponent.vue';

export default defineComponent({
    name: 'App',

    components: {
        ModalComponent,
    },

    setup() {
        return {};
    },
});
</script>

<style lang="scss">
@use '~@wjb/styling/main';
@use '@/style/variables' as vars;

*,
*::before,
*::after {
    box-sizing: border-box;
    transition: all 0.2s;
}

html,
body,
#app {
    height: 100%;
}

body {
    margin: 0;
    font-family: 'Quicksand', 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
    line-height: 1.6em;
    letter-spacing: 0.4px;
}

img {
    max-width: 100%;
    height: auto;
    vertical-align: middle;
}
</style>