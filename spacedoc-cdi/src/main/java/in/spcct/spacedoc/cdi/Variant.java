package in.spcct.spacedoc.cdi;

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
