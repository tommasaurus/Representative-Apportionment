package scripts;

public class Configuration {
    private ApportionmentStrategy apportionmentStrategy;
    private StateReader stateReader;
    private int representatives;
    private ApportionmentFormat apportionmentFormat;
    public ApportionmentStrategy getApportionmentStrategy() {
        return apportionmentStrategy;
    }

    public void setApportionmentStrategy(ApportionmentStrategy apportionmentStrategy) {
        this.apportionmentStrategy = apportionmentStrategy;
    }

    public StateReader getStateReader() {
        return stateReader;
    }

    public void setStateReader(StateReader stateReader) {
        this.stateReader = stateReader;
    }

    public int getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(int representatives) {
        this.representatives = representatives;
    }

    public ApportionmentFormat getApportionmentFormat() {
        return apportionmentFormat;
    }

    public void setApportionmentFormat(ApportionmentFormat apportionmentFormat) {
        this.apportionmentFormat = apportionmentFormat;
    }
}
