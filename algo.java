import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class algo {
    private final Map<Double, Map<Double, ShooterValues>> table = new TreeMap<>();

    public algo() {
        // test data
        table.put(2.0, new TreeMap<>());
        table.get(2.0).put(2.0, new ShooterValues(20, 20, 20));
        //
        table.put(1.0, new TreeMap<>());
        table.get(1.0).put(2.0, new ShooterValues(20, 20, 20));
        //
        table.put(2.0, new TreeMap<>());
        table.get(1.0).put(2.0, new ShooterValues(20, 20, 20));
        //
        table.put(1.0, new TreeMap<>());
        table.get(2.0).put(2.0, new ShooterValues(20, 20, 20));
    }

    public ShooterValues compute(double location) {
        Values<Map<Double, ShooterValues>> ypoints = new Values<Map<Double, ShooterValues>>();
        ypoints.findCloset(table, location);
        Values<ShooterValues> pointsHigh = new Values<ShooterValues>();
        pointsHigh.findCloset(ypoints.high, location);
        Values<ShooterValues> pointsLow = new Values<ShooterValues>();
        pointsLow.findCloset(ypoints.low, location);
        // we have points now calc time
        ShooterValues high = pointsHigh.high.interpolateOther(pointsHigh.low, pointsHigh.highDataDistance, pointsHigh.lowDataDistance);
        ShooterValues low = pointsLow.high.interpolateOther(pointsLow.low, pointsLow.highDataDistance, pointsLow.lowDataDistance);
        return new ShooterValues(ShooterValues.interpolate(high.angle, ypoints.highDataDistance, low.angle, ypoints.lowDataDistance),
        ShooterValues.interpolate(high.rpm, ypoints.highDataDistance, low.rpm, ypoints.lowDataDistance),
        ShooterValues.interpolate(high.robotAngle, ypoints.highDataDistance, low.robotAngle, ypoints.lowDataDistance));
    }

    public static void main(String[] args) {
        System.out.println(new algo().compute(2.2));
    }
}

class ShooterValues {
    public double angle;
    public double rpm;
    public double robotAngle;

    public ShooterValues interpolateOther(ShooterValues other, double selfDistance, double otherDistance) {
        return new ShooterValues(interpolate(this.angle, selfDistance, other.angle, otherDistance),
        interpolate(this.rpm, selfDistance, other.rpm, otherDistance),
        interpolate(this.robotAngle, selfDistance, other.robotAngle, otherDistance));
     }

    public static double interpolate(double highData, double highDataDistance, double lowData, double lowDataDistance) {
        return ((highData / highDataDistance) + Math.abs(lowData / lowDataDistance)) *
                Math.abs(highDataDistance * lowDataDistance);
    }
    public ShooterValues(double angle, double rpm, double offset) {
        this.angle = angle;
        this.rpm = rpm;
        this.robotAngle = offset;
    }

    public String toString() {
        return "angle: " + angle + " rpm: " + rpm + " robotAngle: " + robotAngle;
    }
}

class Values<T> {
    public T high;
    public T low;
    public double highDataDistance = Double.POSITIVE_INFINITY;
    public double  lowDataDistance = Double.NEGATIVE_INFINITY;
    public Values(T high, T low) {
        this.high = high;
        this.low = low;
    }

    public Values() {
    }

    public void findCloset(Map<Double, T> data, double location) {
        T lowData = null;
        T highData = null;
        for (Map.Entry<Double, T> entry : data.entrySet()) {
            double key = entry.getKey();
            T value = entry.getValue();
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
        this.high = highData;
        this.low = lowData;
    }
}