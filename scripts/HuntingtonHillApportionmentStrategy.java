package scripts;

import java.util.*;

public class HuntingtonHillApportionmentStrategy extends ApportionmentStrategy {
    private List<State> stateList;
    private int repsRemaining;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment;
    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        firstPassApportionment();
        secondPassApportionment();
        return apportionment;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        if(representatives < stateList.size()) {
            throw new IllegalArgumentException("For Huntington-Hill apportionment, there must be, at minimum, a representative for every state.");
        }
        this.stateList = stateList;
        repsRemaining = representatives;
        apportionment = new Apportionment();
        decimalApportionment = new DecimalApportionment();
    }

    private void firstPassApportionment() {
        for (State state: stateList) {
            apportionment.addRepresentativesToState(state, 1);
            double priority = getStatePriority(state);
            decimalApportionment.setDecimalApportionmentForState(state, priority);
            repsRemaining--;
        }
    }
    private double getStatePriority(State state) {
        int statePop = state.getPopulation();
        int stateRep = apportionment.getRepresentativesForState(state);
        return statePop/Math.sqrt(stateRep * (stateRep+1));
    }

    private void secondPassApportionment() {
        while(repsRemaining > 0) {
            State maxState = allocateRepToState();
            double priority = getStatePriority(maxState);
            decimalApportionment.setDecimalApportionmentForState(maxState, priority);
        }
    }

    private State allocateRepToState() {
        double maxPriority = -1;
        State maxState = null;
        for(State set : decimalApportionment.decimalApportionmentMap.keySet()) {
            double statePriority = decimalApportionment.decimalApportionmentMap.get(set);
            if(statePriority > maxPriority) {
                maxPriority = statePriority;
                maxState = set;
            }
        }
        apportionment.addRepresentativesToState(maxState, 1);
        repsRemaining--;
        return maxState;
    }
}