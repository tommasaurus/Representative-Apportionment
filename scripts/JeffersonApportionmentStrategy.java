package scripts;
import java.util.*;

public class JeffersonApportionmentStrategy extends ApportionmentStrategy {
    private List<State> stateList;
    private Apportionment apportionment;
    private DecimalApportionment decimalApportionment;
    private HamiltonApportionmentStrategy jefferson;
    private int targetRepresentatives;
    private double divisor;

    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        divisor = jefferson.getDivisor();
        decimalApportionment = jefferson.getDecimalApportionment();
        apportionment = decimalApportionment.getRoundedDownApportionment();

        return binarySearch();
    }

    public Apportionment binarySearch() {
        Apportionment roundDown = apportionment;
        int numOfCurrentRep = apportionment.getAllocatedRepresentatives();
        double Divisor_left = 0;
        double Divisor_right = divisor;
        while (numOfCurrentRep != targetRepresentatives) {
            double medianOfDivisor = (Divisor_left + Divisor_right) / 2;
            roundDown = newDecimalMap(medianOfDivisor).getRoundedDownApportionment();
            numOfCurrentRep = roundDown.getAllocatedRepresentatives();
            if(numOfCurrentRep > targetRepresentatives) {
                Divisor_left = medianOfDivisor;
            }
            else if(numOfCurrentRep < targetRepresentatives) {
                Divisor_right = medianOfDivisor;
            }
        }

        return roundDown;
    }

    private DecimalApportionment newDecimalMap(double medianOfDivisor) {
        for(State state: stateList) {
            int StatePop = state.getPopulation();
            double Rep = StatePop/medianOfDivisor;
            decimalApportionment.setDecimalApportionmentForState(state, Rep);
        }
        return decimalApportionment;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
        jefferson = new HamiltonApportionmentStrategy();
        jefferson.initializeFields(stateList, representatives);
    }
}