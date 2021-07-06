package in.spcct.spacedoc.cdi;

import in.spcct.spacedoc.cdi.util.CachingSupplier;
import lombok.Data;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * A weird store to decouple instance lookup from its generation.
 */
public class SillyCDI {

    private static final Map<Class<?>, Set<ObjectVariant<?>>> store = new HashMap<>();

    /**
     * Registers a class for later lookup.
     *
     * @param clazz
     * @param priority
     * @param supplier
     * @param <T>
     */
    public static <T> void register(Class<T> clazz, int priority, Supplier<T> supplier) {
        register(clazz, priority, () -> true, supplier);
    }

    /**
     * Registers a class for later lookup.
     *
     * @param clazz
     * @param priority
     * @param variant
     * @param supplier
     * @param <T>
     */
    public static <T> void register(Class<T> clazz, int priority, Variant variant, Supplier<T> supplier) {
        if (!store.containsKey(clazz))
            store.put(clazz, new HashSet<>());

        store.get(clazz)
                .add(new ObjectVariant<>(
                        supplier,
                        variant,
                        priority
                ));
    }

    /**
     * Registers a class for later lookup.
     * <p>
     * Instantiates the instance at most once.
     *
     * @param clazz
     * @param priority
     * @param supplier
     * @param <T>
     */
    public static <T> void registerCaching(Class<T> clazz, int priority, Supplier<T> supplier) {
        register(clazz, priority, new CachingSupplier<>(supplier));
    }


    /**
     * Registers a class for later lookup.
     * <p>
     * Instantiates the instance at most once.
     *
     * @param clazz
     * @param priority
     * @param variant
     * @param supplier
     * @param <T>
     */
    public static <T> void registerCaching(Class<T> clazz, int priority, Variant variant, Supplier<T> supplier) {
        register(clazz, priority, variant, new CachingSupplier<>(supplier));
    }

    /**
     * Attempts to look-up the highest-priority instance of the given class in this store.
     * <p>
     * Only this class will get instantiated through the registered supplier.
     *
     * @param clazz instances of this class are to be returned
     * @param <T>   type of instances to be returned
     * @return found class
     * @throws LookupException when no class is found
     */
    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> clazz) throws LookupException {
        if (store.get(clazz) == null)
            throw new LookupException("Null class provided");

        T result = (T) store.get(clazz)
                .stream()
                .filter(objectVariant -> objectVariant.variant.isApplicable())
                .sorted()
                .map(ObjectVariant::getSupplier)
                .findFirst()
                .orElseGet(() -> () -> null)
                .get();

        if (result == null)
            throw new LookupException(clazz);

        return result;
    }

    /**
     * Attempts to look-up all instances of the given class in this store, of at least the specified priority level.
     * <p>
     * Only these classes will get instantiated through the registered supplier.
     * <p>
     * If no instance of the specified class exists, empty list is returned.
     *
     * @param clazz       instances of this class are to be returned
     * @param minPriority minimum priority level of returned instances
     * @param <T>         type of instances to be returned
     * @return list of found instances or an empty list.
     * @throws LookupException when the specified class is null
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> lookupAll(Class<T> clazz, int minPriority) throws LookupException {
        if (clazz == null)
            throw new LookupException("Null class provided");
        if (store.get(clazz) == null)
            return Collections.emptyList();

        return (List<T>) store.get(clazz)
                .stream()
                .filter(objectVariant -> objectVariant.variant.isApplicable())
                .sorted()
                .filter(objectVariant -> objectVariant.priority >= minPriority)
                .map(ObjectVariant::getSupplier)
                .map(Supplier::get)
                .collect(Collectors.toList());

    }

    @Data
    private static class ObjectVariant<T> implements Comparable<ObjectVariant<T>> {
        private final Supplier<T> supplier;
        private final Variant variant;
        private final int priority;

        public ObjectVariant(Supplier<T> supplier, Variant variant, int priority) {
            this.supplier = supplier;
            this.variant = variant;
            this.priority = priority;
        }

        @Override
        public int compareTo(ObjectVariant other) {
            return Integer.compare(other.priority, this.priority);
        }
    }

}
