package scripts;

import java.util.HashMap;
import java.util.Map;

public class DecimalApportionment {
    Map<State, Double> decimalApportionmentMap = new HashMap<>();

    public void setDecimalApportionmentForState(State state, double decimal) {
        decimalApportionmentMap.put(state, decimal);
    }

    public Apportionment getRoundedDownApportionment() {
        Apportionment apportionment = new Apportionment();
        for (State state : decimalApportionmentMap.keySet()) {
            int roundedDownReps = (int) Math.floor(decimalApportionmentMap.get(state));
            apportionment.addRepresentativesToState(state, roundedDownReps);
        }
        return apportionment;
    }

    public Map<State, Double> getRemainderMap() {
        Map<State, Double> remainderMap = new HashMap<>();
        for (State state : decimalApportionmentMap.keySet()) {
            double remainder = getRemainderForState(state);
            remainderMap.put(state, remainder);
        }
        return remainderMap;
    }

    public double getRemainderForState(State state) {
        return decimalApportionmentMap.get(state) - getRoundedDownApportionmentForState(state);
    }

    public int getRoundedDownApportionmentForState(State state) {
        return (int) Math.floor(decimalApportionmentMap.get(state));
    }
}
