import { createRouter, createWebHistory } from 'vue-router';

import AdminView from '@/view/admin/AdminView.vue';
import MapView from '@/view/map/MapView.vue';

export const appRouter = createRouter({

    history: createWebHistory(),

    routes: [
        {
            path: '',
            component: MapView,
        },
        {
            path: '/admin',
            component: AdminView,
        },
    ],
});