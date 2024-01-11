package scripts;

import java.util.*;

public class HamiltonApportionmentStrategy extends ApportionmentStrategy{

    private List<State> stateList;
    private int targetRepresentatives;
    public double divisor;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment;

    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        return getIntegerApportionment();
    }


    private Apportionment getIntegerApportionment() {
        apportionment = getFirstPassApportionment();
        executeSecondPassApportionment();
        return apportionment;
    }

    public void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
        divisor = getDivisor();
        decimalApportionment = getDecimalApportionment();
    }

    public double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / targetRepresentatives;
    }

    public DecimalApportionment getDecimalApportionment() {
        DecimalApportionment decimalApportionment = new DecimalApportionment();
        for (State state : stateList) {
            double decimalRepresentatives = state.getPopulation() / divisor;
            decimalApportionment.setDecimalApportionmentForState(state, decimalRepresentatives);
        }
        return decimalApportionment;
    }

    public Apportionment getFirstPassApportionment() {
        return decimalApportionment.getRoundedDownApportionment();
    }

    private void executeSecondPassApportionment() {
        int repsLeftToAllocate = getRepsLeftToAllocate();
        Map<State, Double> remainderMap = decimalApportionment.getRemainderMap();
        remainderMap.entrySet().stream()
                .sorted((e1, e2) -> (Double.compare(e2.getValue(), e1.getValue())))
                .limit(repsLeftToAllocate)
                .map(Map.Entry::getKey)
                .forEach(state -> apportionment.addRepresentativesToState(state, 1));
    }

    public int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : stateList) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

    private int getRepsLeftToAllocate() {
        int allocatedRepresentatives = apportionment.getAllocatedRepresentatives();
        return targetRepresentatives - allocatedRepresentatives;
    }
}
