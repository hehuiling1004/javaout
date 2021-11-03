package com.lemon.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.pojo.CasePojo;

import java.io.File;
import java.util.List;

public class ExcelUtils {
    public static  final String EXCEL_Path = "src\\test\\resources\\api_testcases_futureloan_v2.xls";
    /**
     * 读取所有的数据
     * @param sheetNum
     * @return
     */
    public static List<CasePojo> readExcelSeetAllDatas(int sheetNum) {
        File file = new File(EXCEL_Path);
        ImportParams importParams = new ImportParams();
        //读取第几个表格的配置
        importParams.setStartSheetIndex(sheetNum-1);
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class,importParams);
        return listDatas;
    }
    public static List<CasePojo> readExcel(int sheetNum) {
        File file = new File(EXCEL_Path);
        ImportParams importParams = new ImportParams();
        //读取第几个表格的配置
        importParams.setStartSheetIndex(sheetNum-1);
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class,importParams);
        return listDatas;
    }

    /**
     * 从指定行开始，读取几行数据
     * @param sheetNum
     * @param startRow
     * @param rows
     * @return
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum, int startRow, int rows){
        File file = new File(EXCEL_Path);
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum-1);
        importParams.setStartRows(startRow-1);
        importParams.setReadRows(rows);
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class,importParams);
        return listDatas;
    }

    /**
     * 从指定行，读取到最后
     * @param sheetNum
     * @param startRow
     * @return
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum, int startRow){
        File file = new File("src\\test\\resources\\api_testcases_futureloan_v2.xls");
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheetNum-1);
        importParams.setStartRows(startRow-1);
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class,importParams);
        return listDatas;
    }

}
