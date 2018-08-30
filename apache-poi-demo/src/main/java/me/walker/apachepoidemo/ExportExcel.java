package me.walker.apachepoidemo;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 */
public class ExportExcel<T> {

    public Workbook createWorkbook(String path) throws Exception {
        return getWorkbook(path);
    }

    /**
     * 创建表
     * @param title
     * @param headers
     * @param workbook
     * @return
     */
    public Sheet createSheet(String title, String[] headers, Workbook workbook) {
        //生成一个表格
        Sheet sheet = workbook.createSheet(title);
        //设置表格默认列宽度为30
        sheet.setDefaultColumnWidth((short) 30);
        //产生表格标题行
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        return sheet;
    }

    /**
     * 表格追加数据
     * @param sheet
     * @param rowIndex
     * @param rowDatas
     */
    public void updateSheetData(Sheet sheet, int rowIndex, List<Object> rowDatas) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < rowDatas.size(); i++) {
            Cell cell = row.createCell(i);

            Object value = rowDatas.get(i);
            //判断值得类型进行强制类型转换
            String textValue = null;
            if (value != null) {
                value = value.toString();
            }
            if (StringUtils.isNotBlank((String) value)) {
                //其他字符串都当作字符串简单处理
                textValue = value.toString();
            } else {
                textValue = "";
            }

            if (StringUtils.isNotBlank(textValue)) {
                cell.setCellValue(textValue);
            }

        }
    }

    public void updateSheetData(Sheet sheet, Map<Integer, List<Object>> datas) {
        for (Map.Entry<Integer, List<Object>> entry : datas.entrySet()) {
            Row row = sheet.createRow(entry.getKey());
            List<Object> rowDatas = entry.getValue();
            for (int i = 0; i < rowDatas.size(); i++) {
                Cell cell = row.createCell(i);
                Object value = rowDatas.get(i);
                //判断值得类型进行强制类型转换
                String textValue = null;
                if (value != null) {
                    value = value.toString();
                }
                if (StringUtils.isNotBlank((String) value)) {
                    //其他字符串都当作字符串简单处理
                    textValue = value.toString();
                } else {
                    textValue = "";
                }

                //如果不是图片数据，利用正则表达式判断textValue是否全部由数字组成
                if (StringUtils.isNotBlank(textValue)) {
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        //是数字当作数字处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        cell.setCellValue(textValue);
                    }
                }
            }
        }
    }

    /**
     * 兼容2003和2007
     *
     * @param fileLocation
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook(String fileLocation) throws Exception {
        Workbook workbook = null;

        if (fileLocation.endsWith("xlsx")) {
            workbook = new SXSSFWorkbook(null, 100, false);
        } else if (fileLocation.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The Specified file is not Excel file");
        }
        return workbook;
    }

}
