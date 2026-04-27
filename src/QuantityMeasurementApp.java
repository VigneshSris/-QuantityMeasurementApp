public class QuantityMeasurementApp {

    // Enum with conversion factors (base: FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Quantity Class (UC3/UC4 reused)
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // 🔥 UC5: Conversion API
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid input for conversion");
        }

        double valueInFeet = source.toFeet(value);
        return valueInFeet / target.toFeet(1.0);
    }

    public static void main(String[] args) {

        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCH));        // 12.0
        System.out.println(convert(3.0, LengthUnit.YARD, LengthUnit.FEET));        // 9.0
        System.out.println(convert(36.0, LengthUnit.INCH, LengthUnit.YARD));       // 1.0
        System.out.println(convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH));  // ~0.3937
    }
}