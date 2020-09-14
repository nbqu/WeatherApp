package api_data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class MidFcstRetrievingCoordinate {
    private String address;
    private XSSFCell coordinate;

    public MidFcstRetrievingCoordinate(String des) throws FileNotFoundException, IOException {
        this.address = des;
    }

    public XSSFCell GettingCoordinate() throws IOException {
         FileInputStream file = new FileInputStream("/Users/TRECE/Desktop/Git/WeatherApp/MidFcstCoordinateDataSet/MidFcstData.xlsx");
         XSSFWorkbook workbook = new XSSFWorkbook(file);

         int rowindex = 0;
         int columnindex = 1;
         boolean flag = false;
         XSSFSheet sheet = workbook.getSheetAt(0);

         int rows = sheet.getPhysicalNumberOfRows();
         for (rowindex = 4; rowindex < rows; rowindex++) {
             XSSFRow row = sheet.getRow(rowindex);
             if (row != null) {
                 XSSFCell cell = row.getCell(columnindex);
                 String value = "";
                 if (cell == null) continue;
                 if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                     value = cell.getStringCellValue() + "";
                     if (value.equals(address)) {
                         coordinate = row.getCell(2);
                         flag = true;
                     }
                 }
             }
             if (flag) break;
         }

         //if found the corresponding address
         if (flag)
             return coordinate;

        return null;
    }

}
