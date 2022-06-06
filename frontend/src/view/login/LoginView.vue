<template>
    <CardComponent class="login-view flex">
        <CardComponent class="login-box" highlight="top" location="middle">
            <h2>Login</h2>
            <label>
                <strong>Username</strong>
                <input type="text" v-model="username">
            </label>
            <br>
            <label>
                <strong>Password</strong>
                <input type="password" v-model="password">
            </label>
            <p></p>
            <ButtonComponent class="primary" @click="onLogIn">
                Log In
            </ButtonComponent>
        </CardComponent>
    </CardComponent>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

import { useAppData } from '@/use/appData.use';

export default defineComponent({
    name: 'LoginView',

    setup() {
        const appData = useAppData();
        const router = useRouter();

        const username = ref<string>('');
        const password = ref<string>('');

        return {
            username,
            password,

            async onLogIn() {
                await appData.auth.logIn({
                    username: username.value,
                    password: password.value,
                });

                router.push({
                    path: '/admin',
                });
            },
        };
    },
});
</script>

<style lang="scss">
.login-view {
    bottom: 1rem;
    right: 1rem;

    .login-box {
        margin: auto;
    }
}
</style>