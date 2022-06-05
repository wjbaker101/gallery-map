<template>
    <CardComponent class="login-view flex">
        <CardComponent class="login-box" highlight="top" location="middle">
            <h2>Login</h2>
            <label>
                <strong>Username</strong>
                <input type="text">
            </label>
            <br>
            <label>
                <strong>Password</strong>
                <input type="password">
            </label>
            <p></p>
            <ButtonComponent class="primary">
                Log In
            </ButtonComponent>
        </CardComponent>
    </CardComponent>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'LoginView',

    setup() {
        return {};
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