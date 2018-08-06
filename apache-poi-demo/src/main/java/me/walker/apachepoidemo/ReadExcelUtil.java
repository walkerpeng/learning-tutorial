package me.walker.apachepoidemo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadExcelUtil {
    public static Map<Integer,List<String>> readExcel(String fileLocation)throws Exception{

        Map<Integer,List<String>> data = new ConcurrentHashMap<>();
        //将file转换成Workbook
        Workbook workbook = getWorkbook(fileLocation);
        //获取表格
        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row row : sheet) {
            data.put(i,new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellTypeEnum()) {//对一行中的单元格进行类型判断
                    case STRING:
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add((int)cell.getNumericCellValue() + "");
                        }
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    default:
                        data.get(i).add("");
                }
            }
            //读取下一行
            i++;
        }
        if (workbook != null) {
            workbook.close();
        }
        return data;
    }

    //兼容Excel2003和2007
    protected static Workbook getWorkbook(String fileLocation)throws Exception{
        Workbook workbook = null;

        if (fileLocation.endsWith("xlsx")) {//直接读取，节约内存
            workbook = new XSSFWorkbook(new File(fileLocation));
        } else if (fileLocation.endsWith("xls")) {
            FileInputStream file = new FileInputStream(new File(fileLocation));
            workbook = new HSSFWorkbook(file);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }
}
