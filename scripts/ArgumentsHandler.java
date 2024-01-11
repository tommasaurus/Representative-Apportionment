package scripts;

import java.util.*;
import java.io.*;
import org.apache.commons.cli.*;

public class ArgumentsHandler {

    public static final int FILENAME_INDEX = 0;
    public static final int REPRESENTATIVES_INDEX = 1;
    private final List<String> arguments;
    private Configuration config;

    public ArgumentsHandler(List<String> arguments) {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("Error: No arguments were included at runtime. Arguments expected\n" +
                    "statePopulationFilename [number of representatives] [--hamilton]");
        }
        this.arguments = arguments;
    }


    public ArgumentsHandler(String[] args) {
        this(Arrays.asList(args));
    }

    private void getFlagArgs() {
        Options options = new Options();
        Option reps = new Option("r","reps",true,"specifies the number of representatives");
        Option format = new Option("f","format",true,"specifies the format choices");
        Option algorithm = new Option("a","algorithm",true,"specifies the algorithm type");
        options.addOption(reps);
        options.addOption(format);
        options.addOption(algorithm);

        String[] args = arguments.stream().toArray(String[] :: new);
        StateReaderFactory statereader = new StateReaderFactory();
        config.setStateReader(statereader.getStateReader(args[0]));

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if(cmd.getArgList().size() > 1)
                handleCombinedFlags(cmd);
            else {
                if (cmd.hasOption(reps.getOpt()) || cmd.hasOption(reps.getLongOpt()))
                    configReps(cmd.getOptionValue(reps.getOpt()));

                if (cmd.hasOption(format.getOpt()) || cmd.hasOption(format.getLongOpt()))
                    configFormat(cmd, cmd.getOptionValue(format.getOpt()));

                if (cmd.hasOption(algorithm.getOpt()) || cmd.hasOption(algorithm.getLongOpt()))
                    configAlgorithm(cmd, cmd.getOptionValue(algorithm.getOpt()));
            }
        }
        catch (ParseException e) {
            throw new IllegalArgumentException("Command line input contains an invalid flag or argument. " + e.getMessage());
        }
    }

    private void configReps(String reps) {
        try {
            int arg = Integer.parseInt(reps);
            if (arg > 0) {
                config.setRepresentatives(arg);
            }
            else {
                throw new IllegalArgumentException("the input number of representatives is invalid (less than or equal to zero)");
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("representative input is not a number");
        }
    }

    private void configFormat(CommandLine cmd, String format) {
        ApportionmentFormatFactory factory = new ApportionmentFormatFactory();
        config.setApportionmentFormat(factory.getFormat(format));
    }

    private void configAlgorithm(CommandLine cmd, String algorithm) {
        ApportionmentStrategyFactory factory = new ApportionmentStrategyFactory();
        config.setApportionmentStrategy(factory.getStrategy(algorithm));
    }

    private void handleCombinedFlags(CommandLine cmd) {
        int argLen = cmd.getArgList().size();
        String[] arr = new String[argLen-1];

        for(Option option: cmd.getOptions()) {
            arr[0] = option.getOpt();
            arr[1] = cmd.getOptionValue(option).substring(0,1);
            if(argLen-1 == 3) {
                arr[2] = cmd.getOptionValue(option).substring(1,2);
            }
        }
        List<String> list = cmd.getArgList();
        for(int i=0; i<arr.length; i++) {
            switch(arr[i]) {
                case "r":
                    configReps(list.get(i+1));
                    break;
                case "f":
                    configFormat(cmd, list.get(i+1));
                    break;
                case "a":
                    configAlgorithm(cmd, list.get(i+1));
                    break;
            }
        }
    }

    public Configuration getConfiguration() {
        setDefaultConfiguration();
        getFlagArgs();
        configureStateReader();
        checkForRepresentativeCount();
        return config;
    }

    private void setDefaultConfiguration() {
        config = new Configuration();
        config.setApportionmentStrategy(new HuntingtonHillApportionmentStrategy());
        config.setRepresentatives(435);
        config.setApportionmentFormat(new AlphabeticalApportionmentFormat());
    }

    private void configureStateReader() {
        String filename = arguments.get(FILENAME_INDEX);
        setStateReaderFromFilename(filename);
    }

    private void checkForRepresentativeCount() {
        if (arguments.size() < 2) {
            return;
        }
        try {
            int representativeCount = Integer.parseInt(arguments.get(REPRESENTATIVES_INDEX));
            if (representativeCount <= 0) {
                throw new IllegalArgumentException("Error: Invalid representative count : " + representativeCount + " - number must be positive");
            }
            config.setRepresentatives(representativeCount);
        } catch (NumberFormatException ignored) {
        }
    }

    private void setStateReaderFromFilename(String filename) {
        if (filename.toLowerCase().endsWith(".csv")) {
            setConfigurationToCSVReader(filename);
        } else if (filename.toLowerCase().endsWith(".xlsx")) {
            setConfigurationToXLSXReader(filename);
        } else {
            throw new IllegalArgumentException("Error: invalid file type. The system currently supports:\n" +
                    "\t.csv, .xlsx");
        }
    }

    private boolean filenameExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private void setConfigurationToCSVReader(String filename) {
        config.setStateReader(new CSVStateReader(filename));
    }

    private void setConfigurationToXLSXReader(String filename) {
        config.setStateReader(new ExcelStateReader(filename));
    }
}
