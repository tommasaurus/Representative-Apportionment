package scripts;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RelevativeBenefitFormat extends ApportionmentFormat {
    private Apportionment apportionment;
    private DecimalApportionment benefitApportionment;
    private DecimalApportionment decimalApportionment;
    private double divisor;

    @Override
    public String getApportionmentString(Apportionment apportionment) {
        this.apportionment = apportionment;
        divisor = getDivisor();
        decimalApportionment = getDecimalApportionment();
        makeBenefitMap();
        return getBenefitApportionmentString();
    }

    private String getBenefitApportionmentString(){
        return benefitApportionment.decimalApportionmentMap.entrySet().stream()
                .sorted(Entry.<State,Double>comparingByValue().reversed())
                .map(this::getApportionmentStringForState)
                .collect(Collectors.joining("\n"));
    }

    private void makeBenefitMap() {
        benefitApportionment = new DecimalApportionment();
        for(State state : apportionment.getStateSet()) {
            int finalRep = apportionment.getRepresentativesForState(state);
            double raw = decimalApportionment.decimalApportionmentMap.get(state);
            double benefit = finalRep - raw;
            benefitApportionment.setDecimalApportionmentForState(state, benefit);
        }
    }

    private DecimalApportionment getDecimalApportionment() {
        DecimalApportionment decimalApportionment = new DecimalApportionment();
        for (State state : apportionment.getStateSet()) {
            double decimalRepresentatives = state.getPopulation() / divisor;
            decimalApportionment.setDecimalApportionmentForState(state, decimalRepresentatives);
        }
        return decimalApportionment;
    }

    private String getApportionmentStringForState(Entry<State,Double> state) {
        String stateName = state.getKey().getName();
        int stateReps = apportionment.getRepresentativesForState(state.getKey());
        double benefit = state.getValue();

        if(benefit > 0) {
            return stateName + " - " + stateReps + " - +" + String.format("%.3f",benefit);
        } else {
            return stateName + " - " + stateReps + " - " + String.format("%.3f",benefit);
        }
    }

    private int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : apportionment.getStateSet()) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

    private double getDivisor() {
        return (double) getTotalPopulation() / apportionment.getAllocatedRepresentatives();
    }
}
