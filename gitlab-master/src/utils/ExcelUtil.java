package utils;




import entrys.CommitsBean;
import entrys.FileCommitsBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class ExcelUtil {


    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        //设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor((short) 27);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    /**
     * 设置表体单元格样式
     * @param workbook 工作薄对象
     * @return 单元格样式对象
     */
    private static CellStyle setBodyCellStyle(Workbook workbook, Sheet sheet, int i) {
        CellStyle cellStyle = workbook.createCellStyle();
        sheet.autoSizeColumn(i,true);
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);


        return cellStyle;
    }


    public static void writeGitExcel(List<CommitsBean> commitsBeans,List<FileCommitsBean> fileBeans,String since,String until) {
        try{
            Workbook workbook = null;
            Row row = null;
            Cell cell = null;
            String path="";
            //创建Excel文件
            File excelFile = new File("D:/gitCodeList/gitlab代码变更清单.xlsx");
            File file1=new File("D:/gitCodeList");
            if(!file1.exists()){
                file1.mkdir();
            }
            //创建Excel工作薄
            if (excelFile.getName().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }

            //创建Excel表单
            Sheet sheet1 = workbook.createSheet("提交");
            sheet1.setDefaultRowHeight((short)512);
            sheet1.setColumnWidth(0, 4096);

            sheet1.addMergedRegion(new CellRangeAddress(0,0,0,5));
            row = sheet1.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("代码变更清单(日期:"+since+"到"+until+")");

            //创建表头行
            row = sheet1.createRow(1);
            cell = row.createCell(0);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("提交日期");
            cell = row.createCell(2);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("作者");
            cell = row.createCell(3);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("提交ID");
            cell = row.createCell(4);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("提交信息");
            cell = row.createCell(5);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("文件列表");


            int rowNum=2;
            int num=1;
            for (CommitsBean commitsBean:commitsBeans){
                sheet1.setColumnWidth(rowNum, 4096);
                Row newRow = sheet1.createRow(rowNum++);
                Cell newCell = newRow.createCell(0);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,0));
                newCell.setCellValue(num++);

                newCell = newRow.createCell(1);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,1));
                newCell.setCellValue(commitsBean.getCommitted_date());

                newCell = newRow.createCell(2);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,1));
                newCell.setCellValue(commitsBean.getAuthor_name());

                newCell = newRow.createCell(3);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,1));
                newCell.setCellValue(commitsBean.getShort_id());

                newCell = newRow.createCell(4);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,1));
                newCell.setCellValue(commitsBean.getMessage());

                newCell = newRow.createCell(5);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet1,1));
                StringBuffer fileNames=new StringBuffer();
                commitsBean.getDiffBeans().forEach(diffBean -> {
                    fileNames.append(diffBean.getNew_path()).append("\r\n");
                });
                newCell.setCellValue(String.valueOf(fileNames));
            }

           //创建Excel表单
            Sheet sheet2 = workbook.createSheet("文件");
            sheet2.setDefaultRowHeight((short)512);
            sheet2.setColumnWidth(0, 4096);

            sheet2.addMergedRegion(new CellRangeAddress(0,0,0,2));
            row = sheet2.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("代码变更清单(日期:"+since+"到"+until+")");

            //创建表头行
            row = sheet2.createRow(1);
            cell = row.createCell(0);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("文件");
            cell = row.createCell(2);
            cell.setCellStyle(getHeaderCellStyle(workbook));
            cell.setCellValue("修改方式");

            rowNum=2;
            num=1;
            for (FileCommitsBean fileCommitsBean:fileBeans){
                sheet2.setColumnWidth(rowNum, 4096);
                Row newRow = sheet2.createRow(rowNum++);
                Cell newCell = newRow.createCell(0);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet2,0));
                newCell.setCellValue(num++);

                newCell = newRow.createCell(1);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet2,1));
                newCell.setCellValue(fileCommitsBean.getFile_name());

                newCell = newRow.createCell(2);
                newCell.setCellStyle(setBodyCellStyle(workbook,sheet2,1));
                newCell.setCellValue(fileCommitsBean.getModes());
            }


            //把Excel工作薄写入到Excel文件
            FileOutputStream os = new FileOutputStream(excelFile);
            workbook.write(os);
            os.flush();
            os.close();
            File file=new File(path);
            if(file.exists())
                Runtime.getRuntime().exec("cmd /c start "+path);
            System.out.println("----------生成代码清单成功------------");
            System.out.println("代码清单路径:"+path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
