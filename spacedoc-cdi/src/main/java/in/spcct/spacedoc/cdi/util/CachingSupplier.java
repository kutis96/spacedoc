package in.spcct.spacedoc.cdi.util;

import java.util.function.Supplier;

public class CachingSupplier<T> implements Supplier<T> {

    private final Supplier<T> originalSupplier;
    private T cachedInstance = null;

    public CachingSupplier(Supplier<T> originalSupplier) {
        this.originalSupplier = originalSupplier;
    }

    @Override
    public T get() {
        if (cachedInstance == null)
            cachedInstance = originalSupplier.get();

        return cachedInstance;
    }

}
