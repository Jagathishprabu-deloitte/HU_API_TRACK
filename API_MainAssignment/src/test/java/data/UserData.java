package data;

import org.apache.groovy.json.internal.IO;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserData {

    static FileInputStream registerFile;

    static {
        try {
            registerFile = new FileInputStream("src\\RegisterData.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static XSSFWorkbook workbook;

    static {
        try {
            workbook = new XSSFWorkbook(registerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static XSSFSheet sheet = workbook.getSheetAt(0);
    static XSSFRow row = null;
    static XSSFCell cell = null;

    public UserData() throws IOException {

    }

    public String getName() throws IOException {
        String name = sheet.getRow(1).getCell(0).getStringCellValue();
        return name;
    }

    public String getEmail() throws IOException {
        String email = sheet.getRow(1).getCell(1).getStringCellValue();
        return email;
    }

    public String getPassword() throws IOException {
        String password = sheet.getRow(1).getCell(2).getStringCellValue();
        return password;
    }

    public int getAge() throws IOException {
        int age = (int) sheet.getRow(1).getCell(3).getNumericCellValue();
        return age;
    }

    public void writeToken(String token) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src\\RegisterData.xlsx");
        row = sheet.getRow(1);
        cell = row.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(token);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public void writeOwnerId(String userId) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src\\RegisterData.xlsx");
        row = sheet.getRow(1);
        cell = row.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(userId);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    public static  String getToken() throws IOException {
        String token = sheet.getRow(1).getCell(4).getStringCellValue();
        return token;
    }

    public static String getOwnerId() throws IOException {
        String ownerId = sheet.getRow(1).getCell(5).getStringCellValue();
        return ownerId;
    }
}
