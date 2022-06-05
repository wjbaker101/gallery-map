import { createApp } from 'vue';

import App from '@/App.vue';
import { appRouter } from '@/appRouter';

import { setupGlobalComponents } from '@/setup/global-components.setup';

const app = createApp(App);
setupGlobalComponents(app);
app.use(appRouter);
app.mount('#app');