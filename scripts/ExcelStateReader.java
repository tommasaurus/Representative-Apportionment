package scripts;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class ExcelStateReader extends StateReader{
    public static final int NAME_COLUMN_INDEX = 0;
    public static final int POPULATION_COLUMN_INDEX = 1;
    private String filename;
    private Iterator<Row> iterator;
    public ExcelStateReader(String filename) {
        if (!filename.toLowerCase().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Error: cannot open non xlsx file " + filename);
        }
        this.filename = filename;
    }

    @Override
    public void readStates() {
        try {
            getIterator();
            getStatesFromXlsx();

            if(stateList.isEmpty()) {
                throw new Error("Your .xlsx file contains no valid states");
            }
        } catch(IOException e) {
            throw new Error("Consider what command-line inputs could cause errors, and handle them accordingly.");
        }
    }

    private void getIterator() throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        iterator = sheet.iterator();
    }

    private void getStatesFromXlsx() {
        while(iterator.hasNext()) {
            Row row = iterator.next();
            isValidStateAndPop(row);
        }
    }

    private void isValidStateAndPop(Row row) {
        Cell stateCell = row.getCell(NAME_COLUMN_INDEX);
        Cell popCell = row.getCell(POPULATION_COLUMN_INDEX);

        if(stateCell.getCellType() == CellType.STRING && !stateCell.getStringCellValue().equals("")
                && popCell.getCellType() == CellType.NUMERIC && popCell.getNumericCellValue() >= 0)
            addStateFromRow(stateCell,popCell);
    }

    private void addStateFromRow(Cell stateCell, Cell popCell) {
        State newState = new State(stateCell.getStringCellValue(), (int)popCell.getNumericCellValue());
        stateList.add(newState);
    }
}
