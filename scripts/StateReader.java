package scripts;

import java.util.*;

public abstract class StateReader {
    protected List<State> stateList;

    public List<State> getStates() {
        if (stateList == null) {
            stateList = new ArrayList<>();
            readStates();
        }
        return stateList;
    }

    public abstract void readStates();

    protected void addState(State state) {
        stateList.add(state);
    }
}
