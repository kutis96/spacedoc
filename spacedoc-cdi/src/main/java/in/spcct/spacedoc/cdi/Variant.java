package in.spcct.spacedoc.cdi;

/**
 * Determines whether an injection variant can apply in this context.
 * <p>
 * The context is provided by the implementing class.
 */
@FunctionalInterface
public interface Variant {
    boolean isApplicable();

    default Variant inverse() {
        return new InverseVariant(this);
    }

    class InverseVariant implements Variant {

        private final Variant original;

        public InverseVariant(Variant original) {
            this.original = original;
        }

        @Override
        public boolean isApplicable() {
            return !original.isApplicable();
        }
    }
}
