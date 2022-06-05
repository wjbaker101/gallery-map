import { App } from 'vue';

import ButtonComponent from '@wjb/vue/component/ButtonComponent.vue';
import DeleteButtonComponent from '@wjb/vue/component/DeleteButtonComponent.vue';
import IconComponent from '@wjb/vue/component/IconComponent.vue';

export const setupGlobalComponents = function (app: App<Element>) {

    app.component('ButtonComponent', ButtonComponent);
    app.component('DeleteButtonComponent', DeleteButtonComponent);
    app.component('IconComponent', IconComponent);
};