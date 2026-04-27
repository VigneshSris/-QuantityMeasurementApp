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

        public double fromFeet(double feet) {
            return feet / toFeet;
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

        // 🔥 UC7: Addition with explicit target unit
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Null not allowed");
            }

            double sumInFeet = q1.toFeet() + q2.toFeet();

            double resultValue = targetUnit.fromFeet(sumInFeet);

            return new Quantity(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Quantity)) return false;

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

        System.out.println(Quantity.add(q1, q2, LengthUnit.FEET));   // 2.0 FEET
        System.out.println(Quantity.add(q1, q2, LengthUnit.INCH));   // 24.0 INCH
        System.out.println(Quantity.add(q1, q2, LengthUnit.YARD));  // ~0.667 YARD
    }
}