//based on distance x, determine y (time at max power)
import java.util.HashMap;
import java.util.Map;

public class algo {
    public algo() {
        table.put(9.0, 20.0);
        table.put(11.0, 30.0);
    }
    private HashMap<Double, Double> table = new HashMap<Double, Double>();
    public  double compute(double location) {
        double lowData = 0;
        double highData = 0;
        double lowDataDistance = -1000000000;
        double highDataDistance = 100000000;
         for (Map.Entry<Double, Double> entry : table.entrySet()) {
            double key = entry.getKey();
            double value = entry.getValue();
            double check =  key - location;
            if (location  - key > 0){
               if (check > lowDataDistance) {
                lowDataDistance = check;
                lowData = value;
               }
            }else if (location == key) {
                lowData = value;
                highData = value;
                break;
            }else {
                if (check < highDataDistance) {
                highDataDistance = check;
                highData = value;
               }
            }
        }
        System.out.println("low Data: " +  lowData + " high data  " + highData + " Low data dist " + lowDataDistance + " high data dist " + highDataDistance);
        return (Math.abs(highData / highDataDistance) + Math.abs(lowData / lowDataDistance)) * Math.abs((highDataDistance * lowDataDistance) / 2);
    }
    public static void main(String[] args) {
        System.out.println(new algo().compute(10));
    } 
}