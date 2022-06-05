<template>
    <div class="modal-component" :class="{ 'is-visible': isVisible, [style]: true }" @click.self="onClose">
        <transition name="modal-transition">
            <CardComponent v-if="isVisible" class="content">
                <div class="close" @click="onClose">
                    <IconComponent icon="cross" />
                </div>
                <component :is="component" v-bind="eventProps" />
            </CardComponent>
        </transition>
    </div>
</template>

<script lang="ts">
import { Component, computed, defineComponent, onMounted, onUnmounted, ref } from 'vue';

import CardComponent from '@/component/CardComponent.vue';

import NewAlbumModalComponent from '@/view/admin/component/NewAlbumModalComponent.vue';
import NewPhotoModalComponent from '@/view/admin/component/NewPhotoModalComponent.vue';
import ViewAlbumModalComponent from '@/view/map/component/ViewAlbumModalComponent.vue';

import { IShowModalEvent, useEvents } from '@/use/events.use';

export default defineComponent({
    name: 'ModalComponent',

    components: {
        CardComponent,
    },

    setup() {
        const events = useEvents();

        const isVisible = ref<boolean>(false);
        const modalType = ref<string | null>(null);
        const eventProps = ref<object | null>(null);
        const style = ref<'centered' | 'filled'>('centered');

        const component = computed<Component | null>(() => {
            if (modalType.value === null)
                return null;

            const mapping: Record<string, Component> = {
                'new-album': NewAlbumModalComponent,
                'new-photo': NewPhotoModalComponent,
                'view-album': ViewAlbumModalComponent,
            };

            return mapping[modalType.value];
        });

        const onShowModal = function (event: IShowModalEvent) {
            isVisible.value = true;
            modalType.value = event.name;
            eventProps.value = event.props ?? null;
            style.value = event.style ?? 'centered';
        };

        const onHideModal = function () {
            isVisible.value = false;
        };

        onMounted(() => {
            events.subscribe<IShowModalEvent>('show-modal', onShowModal);
            events.subscribe('hide-modal', onHideModal);
        });

        onUnmounted(() => {
            events.unsubscribe<IShowModalEvent>('show-modal', onShowModal);
            events.unsubscribe('hide-modal', onHideModal);
        });

        return {
            isVisible,
            component,
            eventProps,
            style,

            onClose() {
                events.publish('hide-modal');
            },
        };
    },
});
</script>

<style lang="scss">
@use '@/style/variables' as vars;

.modal-component {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 10;
    opacity: 0;
    pointer-events: none;

    &.filled {
        & > .content {
            bottom: 1rem;
            right: 1rem;
        }
    }

    &.centered {
        & > .content {
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
    }

    &.is-visible {
        opacity: 1;
        pointer-events: all;
    }

    & > .content > .close {
        padding: 0.5rem;
        position: absolute;
        top: 0.5rem;
        right: 0.5rem;
        z-index: 10;
        line-height: 1em;
        background-color: #efefef;
        cursor: pointer;

        @include vars.border-radius;

        &:hover {
            @include vars.shadow-small;
        }
    }

    .modal-transition-enter-from {
        transform: translateY(-20rem) scale(0.2);
    }

    .modal-transition-leave-to {
        transform: translateY(20rem) scale(0.2);
    }

    &.centered {
        .modal-transition-enter-from {
            transform: translate(-50%, -50%) translateY(-20rem) scale(0.2);
        }

        .modal-transition-leave-to {
            transform: translate(-50%, -50%) translateY(20rem) scale(0.2);
        }
    }
}
</style>