public class QuantityMeasurementApp {

    // Enum with extended units (base: FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Generic Quantity Class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBase());
        }
    }

    // Main Method
    public static void main(String[] args) {
        Quantity q1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q2 = new Quantity(3.0, LengthUnit.FEET);
        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.CENTIMETER);

        System.out.println(q1.equals(q2)); // true
        System.out.println(q1.equals(q3)); // true
        System.out.println(q4.equals(new Quantity(0.393701, LengthUnit.INCH))); // true
    }
}