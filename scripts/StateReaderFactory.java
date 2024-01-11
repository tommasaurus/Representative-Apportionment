package scripts;

import org.apache.commons.io.FilenameUtils;
public class StateReaderFactory {
    public StateReader getStateReader(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);

        if(fileExtension.equals("xlsx")) {
            ExcelStateReader xlsxStateReader = new ExcelStateReader(filename);
            return xlsxStateReader;
        } else if(fileExtension.equals("csv")) {
            CSVStateReader csvStateReader = new CSVStateReader(filename);
            return csvStateReader;
        } else {
            throw new Error("The file you inputted is of the incorrect type");
        }
    }
}
