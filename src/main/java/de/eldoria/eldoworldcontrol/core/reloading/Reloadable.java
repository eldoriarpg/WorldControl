package de.eldoria.eldoworldcontrol.core.reloading;

public interface Reloadable extends Initializeable {

    /**
     * Reload the data of the object. The reload should be able to initialize the object as well.
     *
     * @param data data for reload and initialization
     */
    default void reload(SharedData data) {

    }

    @Override
    default void init(SharedData data) {
        reload(data);
    }
}
