public class QuantityMeasurementApp {

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

        // 🔥 UC6 ADDITION METHOD
        public static Quantity add(Quantity q1, Quantity q2) {
            if (q1 == null || q2 == null) {
                throw new IllegalArgumentException("Null values not allowed");
            }

            double sumInFeet = q1.toFeet() + q2.toFeet();

            // convert result into unit of first operand
            double resultValue = sumInFeet / q1.unit.toFeet(1.0);

            return new Quantity(resultValue, q1.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(Quantity.add(q1, q2)); // Quantity(2.0, FEET)
    }
}