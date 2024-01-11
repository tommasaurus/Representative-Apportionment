package scripts;

import java.util.*;
import java.util.Map.*;
import java.util.stream.Collectors;

public class Apportionment {
    Map<State, Integer> apportionmentMap;

    protected Apportionment(Map<State,Integer> apportionmentMap) {
        this.apportionmentMap = apportionmentMap;
    }

    public Apportionment() {
        this(new HashMap<>());
    }

    public void addRepresentativesToState(State state, int newRepresentatives) {
        int currentRepresentatives = getRepresentativesForState(state);
        apportionmentMap.put(state, currentRepresentatives + newRepresentatives);
    }

    public int getRepresentativesForState(State state) {
        return apportionmentMap.getOrDefault(state, 0);
    }

    public Set<State> getStateSet() {
        return apportionmentMap.keySet();
    }

    public int getAllocatedRepresentatives() {
        return apportionmentMap.values().stream()
                .mapToInt(x -> x)
                .sum();
    }
}
