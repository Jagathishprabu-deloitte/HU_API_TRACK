package data;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class TaskData {
    FileInputStream registerFile = new FileInputStream("src\\Tasks.xlsx");
    XSSFWorkbook workbook = new XSSFWorkbook(registerFile);
    XSSFSheet sheet = workbook.getSheetAt(0);
    XSSFRow row = null;

    public TaskData() throws IOException {
    }
    public int getLastRow(){
        return sheet.getLastRowNum();
    }
    public int getLastCell(int i){
        row = sheet.getRow(i);
        return row.getLastCellNum();
    }

    public String getUserTask(int i,int j){
        String task = sheet.getRow(i).getCell(j).getStringCellValue();
        return task;
    }
}
