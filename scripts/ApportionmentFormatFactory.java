package scripts;

public class ApportionmentFormatFactory {
    public ApportionmentFormat getFormat(String formatName) {
        if(formatName.equals("alpha")) {
            AlphabeticalApportionmentFormat alpha = new AlphabeticalApportionmentFormat();
            return alpha;
        } else if(formatName.equals("benefit")) {
            RelevativeBenefitFormat benefit = new RelevativeBenefitFormat();
            return benefit;
        } else {
            throw new Error("The format you inputted is of the incorrect type");
        }
    }
}
