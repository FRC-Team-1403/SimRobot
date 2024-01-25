//based on distance x, determine y (time at max power)

public class sampleAlgo {
    private HashMap<Double, Double> table = new HashMap<Double, Double>();
    public sampleAlgo() {
        //add values here
        table.put(null, null);
    }
    public double compute(double location) {
        double lowData = 0;
        double highData = 0;
        double lowDataDistance = 1000000000;
        double highDataDistance = 100000000;
         for (Map.Entry<Double, Double> entry : table.entrySet()) {
            double key = entry.getKey();
            double value = entry.getValue();
            double check = location - key;
            if (location  - key > 0){
               if (check > highDataDistance) {
                highDataDistance = check;
                lowData = value;
                highData = value;
               }
            }else if (location == key) {
                lowData = value;
                highData = value;
                break;
            }else {
                if (check < lowDataDistance) {
                lowDataDistance = check;
                lowData = value;
                highData = value;
               }
            }
        }
        return ((highData / highDataDistance) + (lowData / lowDataDistance)) * ((highDataDistance * lowDataDistance) / 2);
    }
    public static void main(String[] args) {
        
    } 
}