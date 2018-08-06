package me.walker.apachepoidemo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class WriteExcelUtil {
    public static void writeExcel(Map<Integer,List<String>> data, String excelFilePath)throws Exception{
        Workbook workbook = getWorkbook(excelFilePath);
        Sheet sheet = workbook.createSheet("sheet1");

        int rowCount = 0;

        int totalCount = 0;

        for(int i = 0;i < 5;i++){
            for (List<String> record : data.values() ) {
                if (!CollectionUtils.isEmpty(record)) {
                    Row row = sheet.createRow(++rowCount);
                    writeRecord(record,row);
                    totalCount++;
                }
            }
        }
        System.out.println("数据总量为：" + totalCount);

        try(FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }

        if (workbook != null) {
            workbook.close();
        }

    }

    private static void writeRecord(List<String>record,Row row){
        for (int i = 0; i < record.size(); i++) {
            Cell cell = row.createCell(i + 1);
            cell.setCellValue(record.get(i));
        }
    }

    private static void createHeaderRow(Sheet sheet) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);

        Row row = sheet.createRow(0);
        Cell cellTitle = row.createCell(1);

        cellTitle.setCellStyle(cellStyle);
        cellTitle.setCellValue("Title");

        Cell cellAuthor = row.createCell(2);
        cellAuthor.setCellStyle(cellStyle);
        cellAuthor.setCellValue("Author");

        Cell cellPrice = row.createCell(3);
        cellPrice.setCellStyle(cellStyle);
        cellPrice.setCellValue("Price");
    }

    //兼容Excel2003和2007
    protected static Workbook getWorkbook(String fileLocation)throws Exception{
        Workbook workbook = null;

        if (fileLocation.endsWith("xlsx")) {//直接读取，节约内存
            workbook = new XSSFWorkbook();
        } else if (fileLocation.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }
}
