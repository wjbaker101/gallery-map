type EventName =
    'show-modal' |
    'hide-modal';

type EventFunction<T> = (details: T) => void;

const events: Record<EventName, Array<EventFunction<any>>> = {
    'show-modal': [],
    'hide-modal': [],
};

export const useEvents = function () {
    return {

        subscribe<T>(name: EventName, func: EventFunction<T>): void {
            events[name].push(func);
        },

        unsubscribe<T>(name: EventName, func: EventFunction<T>): void {
            events[name] = events[name].filter(x => x !== func);
        },

        publish<T>(name: EventName, details?: T): void {
            events[name].forEach(x => x(details));
        },

    };
};

export interface IShowModalEvent {
    name: 'new-photo' | 'new-album' | 'view-album';
    style?: 'centered' | 'filled';
    props?: object;
}