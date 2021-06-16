package in.spcct.spacedoc.cdi;

import lombok.Data;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SillyCDI {

    private static final Map<Class<?>, Set<ObjectVariant<?>>> store = new HashMap<>();

    public static <T> void register(Class<T> clazz, int priority, Supplier<T> supplier) {
        register(clazz, priority, () -> true, supplier);
    }

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

    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> clazz) throws LookupException {
        if(store.get(clazz) == null)
            throw new LookupException("Null class provided");

        T result = (T) store.get(clazz)
                .stream()
                .filter(objectVariant -> objectVariant.variant.isApplicable())
                .sorted()
                .map(ObjectVariant::getSupplier)
                .findFirst()
                .orElseGet(() -> () -> null)
                .get();

        if(result == null)
            throw new LookupException(clazz);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> lookupAll(Class<T> clazz, int minPriority) throws LookupException {
        if(store.get(clazz) == null)
            throw new LookupException("Null class provided");

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
