package scripts;

import java.util.List;

public class Apportioner {
    private final StateReader reader;
    private final ApportionmentStrategy strategy;
    private final int representatives;
    private final ApportionmentFormat format;

    public Apportioner(Configuration configuration) {
        reader = configuration.getStateReader();
        strategy = configuration.getApportionmentStrategy();
        representatives = configuration.getRepresentatives();
        format = configuration.getApportionmentFormat();
    }

    public void run() {
        List<State> stateList = reader.getStates();
        Apportionment apportionment = strategy.getApportionment(stateList, representatives);
        System.out.println(format.getApportionmentString(apportionment));
    }
}
