package scripts;

public class ApportionmentStrategyFactory {
    public ApportionmentStrategy getStrategy(String algorithmName) {
        if(algorithmName.equals("hamilton")) {
            HamiltonApportionmentStrategy hamilton = new HamiltonApportionmentStrategy();
            return hamilton;
        } else if(algorithmName.equals("jefferson")) {
            JeffersonApportionmentStrategy jefferson = new JeffersonApportionmentStrategy();
            return jefferson;
        } else if(algorithmName.equals("huntington")) {
            HuntingtonHillApportionmentStrategy huntington = new HuntingtonHillApportionmentStrategy();
            return huntington;
        } else {
            throw new Error("The algorithm you inputted is of the incorrect type");
        }
    }
}
