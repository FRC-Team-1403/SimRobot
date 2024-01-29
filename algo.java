import java.util.Map;
import java.util.TreeMap;

class algo {
    private final Map<Double, ShooterValues> table = new TreeMap<>();

    public algo() {
        table.put(2.0, new ShooterValues(15, 20, 0));
        table.put(3.0, new ShooterValues(20, 30, 1));
        table.put(4.0, new ShooterValues(25, 40, 2));
        table.put(5.0, new ShooterValues(30, 50, 3));
        table.put(6.0, new ShooterValues(35, 60, 4));
        table.put(7.0, new ShooterValues(40, 70, 5));
        table.put(8.0, new ShooterValues(45, 80, 6));
        table.put(9.0, new ShooterValues(50, 90, 6));

    }

    public ShooterValues compute(double location) {
        ShooterValues lowData = null;
        ShooterValues highData = null;
        double lowDataDistance = Double.NEGATIVE_INFINITY;
        double highDataDistance = Double.POSITIVE_INFINITY;

        for (Map.Entry<Double, ShooterValues> entry : table.entrySet()) {
            double key = entry.getKey();
            ShooterValues value = entry.getValue();
            double check = key - location;
            // if key negative then it lower
            if (check < 0 && check > lowDataDistance) {
                lowDataDistance = check;
                lowData = value;
            } else if (check > 0 && check < highDataDistance) {
                highDataDistance = check;
                highData = value;
            } else if (check == 0) {
                lowData = value;
                highData = value;
                break;
            }
        }

        System.out.println("Low Data: " + lowData + ", High Data: " + highData +
                           ", Low Data Dist: " + lowDataDistance + ", High Data Dist: " + highDataDistance);
        return new ShooterValues(interpolate(highData.angle, highDataDistance, lowData.angle, lowDataDistance),
                interpolate(highData.rpm, highDataDistance, lowData.rpm, lowDataDistance),
                interpolate(highData.offset, highDataDistance, lowData.offset, lowDataDistance));
    }

    private double interpolate(double highData, double highDataDistance, double lowData, double lowDataDistance) {
        return ((highData / highDataDistance) + Math.abs(lowData / lowDataDistance)) *
                Math.abs(highDataDistance * lowDataDistance);
    }

    public static void main(String[] args) {
        System.out.println(new algo().compute(2.5));
    }
}

class ShooterValues {
    public double angle;
    public double rpm;
    public double offset;

    public ShooterValues(double angle, double rpm, double offset) {
        this.angle = angle;
        this.rpm = rpm;
        this.offset = offset;
    }

    public String toString() {
        return "angle: " + angle + " rpm: " + rpm + " offset: " + offset;
    }
}