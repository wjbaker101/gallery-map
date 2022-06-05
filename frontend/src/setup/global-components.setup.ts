import { App } from 'vue';

import ButtonComponent from '@wjb/vue/component/ButtonComponent.vue';
import DeleteButtonComponent from '@wjb/vue/component/DeleteButtonComponent.vue';
import IconComponent from '@wjb/vue/component/IconComponent.vue';

import CardComponent from '@/component/CardComponent.vue';

export const setupGlobalComponents = function (app: App<Element>) {

    app.component('ButtonComponent', ButtonComponent);
    app.component('DeleteButtonComponent', DeleteButtonComponent);
    app.component('IconComponent', IconComponent);

    app.component('CardComponent', CardComponent);
};