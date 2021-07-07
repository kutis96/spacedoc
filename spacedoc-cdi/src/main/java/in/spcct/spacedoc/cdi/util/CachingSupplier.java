package in.spcct.spacedoc.cdi.util;

import java.util.function.Supplier;

/**
 * An extension of {@link Supplier} that should only supply a single instance of an object.
 * <p>
 * This supplier will only {@link Supplier#get()} an object from the original supplier when asked to {@link #get()} an object for the first time.
 * <p>
 * It will then return only this single instance of the gotten object for its lifetime.
 *
 * @param <T> class to be supplied
 */
public class CachingSupplier<T> implements Supplier<T> {

    private final Supplier<T> originalSupplier;
    private T cachedInstance = null;

    /**
     * @param originalSupplier original supplier for providing the initial instance to be cached and held.
     */
    public CachingSupplier(Supplier<T> originalSupplier) {
        this.originalSupplier = originalSupplier;
    }

    /**
     * @return single instance provided by the {@link #originalSupplier}.
     */
    @Override
    public T get() {
        if (cachedInstance == null)
            cachedInstance = originalSupplier.get();

        return cachedInstance;
    }

}
