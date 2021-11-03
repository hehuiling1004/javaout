package com.lemon.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.pojo.CasePojo;

import java.io.File;
import java.util.List;

/**
 * @program: javaApi
 * @description:
 * @author: hhl
 * @create: 2021-08-18 16:55
 **/
public class ExcelUtils1 {
    public static  final String EXCEL_PATH="";
   public List<CasePojo> readExel(int sheet){
       File file = new File(EXCEL_PATH);
       ImportParams importParams = new ImportParams();
       importParams.setStartSheetIndex(sheet-1);
       List<CasePojo> listDatas = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
       return listDatas;
   }
   public List<CasePojo> readExcelSpecifyDatas(int sheet, int startrow){
       File file = new File(EXCEL_PATH);
       ImportParams importParams = new ImportParams();
       importParams.setStartSheetIndex(sheet-1);
       importParams.setStartRows(startrow-1);
       List<CasePojo> listDatas = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
       return listDatas;
   }
    public List<CasePojo> readExcelSpecifyDatas(int sheet, int startrow,int readRows){
        File file = new File(EXCEL_PATH);
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(sheet-1);
        importParams.setStartRows(startrow-1);
        importParams.setReadRows(readRows);
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
        return listDatas;
    }
}
