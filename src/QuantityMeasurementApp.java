public class QuantityMeasurementApp {

    // ===================== ENUM =====================
    public enum WeightUnit {

        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factorToKilogram;

        WeightUnit(double factorToKilogram) {
            this.factorToKilogram = factorToKilogram;
        }

        public double convertToBaseUnit(double value) {
            return value * factorToKilogram; // to KILOGRAM
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factorToKilogram; // from KILOGRAM
        }
    }

    // ===================== CLASS =====================
    public static class QuantityWeight {

        private final double value;
        private final WeightUnit unit;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid value or unit");
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityWeight convertTo(WeightUnit targetUnit) {
            double base = unit.convertToBaseUnit(value);
            double result = targetUnit.convertFromBaseUnit(base);
            return new QuantityWeight(result, targetUnit);
        }

        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Null not allowed");
            }

            double sumInKg =
                    this.unit.convertToBaseUnit(this.value)
                            + other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(sumInKg);

            return new QuantityWeight(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityWeight)) return false;

            QuantityWeight other = (QuantityWeight) obj;

            return Double.compare(
                    this.unit.convertToBaseUnit(this.value),
                    other.unit.convertToBaseUnit(other.value)
            ) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ===================== MAIN =====================
    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2)); // true

        System.out.println("Convert: " + w1.convertTo(WeightUnit.GRAM));

        System.out.println("Add: " + w1.add(w2));
    }
}