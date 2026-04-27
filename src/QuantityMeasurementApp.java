public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factorToFeet;

    LengthUnit(double factorToFeet) {
        this.factorToFeet = factorToFeet;
    }

    // Convert value in this unit → FEET (base unit)
    public double convertToBaseUnit(double value) {
        return value * factorToFeet;
    }

    // Convert value in FEET → this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factorToFeet;
    }
}


class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.value = value;
        this.unit = unit;
    }

    // Convert to any target unit
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double baseValue = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(baseValue);
        return new QuantityLength(result, targetUnit);
    }

    // UC6: default target unit = first operand unit
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // UC7: explicit target unit
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Null not allowed");
        }

        double sumInFeet =
                this.unit.convertToBaseUnit(this.value)
                        + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(sumInFeet);

        return new QuantityLength(result, targetUnit);
    }

    // Equality based on base unit (FEET)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

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