package me.walker.test;

import me.walker.apachepoidemo.ReadExcelUtil;
import me.walker.apachepoidemo.WriteExcelUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ExcelTest {
    @Test
    public void testReadExcel() throws Exception{
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
        Map<Integer,List<String>> data = readExcelUtil.readExcel("D:\\SampleData.xlsx");
        System.out.println("data:" + data);

        WriteExcelUtil writeExcelUtl = new WriteExcelUtil();
        String excelFilePath = "D:/sample.xlsx";
        writeExcelUtl.writeExcel(data,excelFilePath);
    }
}
