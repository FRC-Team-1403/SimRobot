import java.util.Map;
import java.util.TreeMap;

public class algo {
    private final Map<Double, Double> table = new TreeMap<>();

    public algo() {
        table.put(1.0, 2.0);
        table.put(3.0, 6.0);
        table.put(5.0, 10.0);
        table.put(7.0, 14.0);
        table.put(9.0, 18.0);
        table.put(11.0, 22.0);
        table.put(13.0, 26.0);
        table.put(15.0, 30.0);
        table.put(17.0, 34.0);
        table.put(19.0, 38.0);
        table.put(21.0, 42.0);
        table.put(23.0, 46.0);
        table.put(25.0, 50.0);
        table.put(27.0, 54.0);
        table.put(29.0, 58.0);
        table.put(31.0, 62.0);
        table.put(33.0, 66.0);
        table.put(35.0, 70.0);
        table.put(37.0, 74.0);
        table.put(39.0, 78.0);
        table.put(41.0, 82.0);
        table.put(43.0, 86.0);
        table.put(45.0, 90.0);
        table.put(47.0, 94.0);
        table.put(49.0, 98.0);
        table.put(51.0, 102.0);
        table.put(53.0, 106.0);
        table.put(55.0, 110.0);
        table.put(57.0, 114.0);
        table.put(59.0, 118.0);
    }

    public double compute(double location) {
        double lowData = 0;
        double highData = 0;
        double lowDataDistance = Double.NEGATIVE_INFINITY;
        double highDataDistance = Double.POSITIVE_INFINITY;

        for (Map.Entry<Double, Double> entry : table.entrySet()) {
            double key = entry.getKey();
            double value = entry.getValue();
            double check = key - location;

            if (check > 0 && check > lowDataDistance) {
                lowDataDistance = check;
                lowData = value;
            } else if (check < 0 && check < highDataDistance) {
                highDataDistance = check;
                highData = value;
            } else if (check == 0) {
                lowData = highData = value;
                break;
            }
        }

        System.out.println("Low Data: " + lowData + ", High Data: " + highData +
                           ", Low Data Dist: " + lowDataDistance + ", High Data Dist: " + highDataDistance);

        return (Math.abs(highData / highDataDistance) + Math.abs(lowData / lowDataDistance)) *
               Math.abs((highDataDistance * lowDataDistance) / 2);
    }

    public static void main(String[] args) {
        System.out.println(new algo().compute(10));
    }
}
