package scripts;

public class Main {
    public static void main(String[] args) {
        Configuration config = loadConfigurationFromArguments(args);
        runApportioner(config);
    }

    private static void runApportioner(Configuration config) {
        Apportioner apportioner = new Apportioner(config);
        apportioner.run();
    }

    private static Configuration loadConfigurationFromArguments(String[] args) {
        ArgumentsHandler argumentsHandler = new ArgumentsHandler(args);
        return argumentsHandler.getConfiguration();
    }
}