package com.fhpt.imageqmind.utils;

import com.fhpt.imageqmind.objects.vo.ColumnInfo;

import com.fhpt.imageqmind.objects.vo.ExcelInfo;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 导入导出Excel工具类
 */
public class ExcelUtil {
    /**
     * 默认数字格式
     */
    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * excel文件读取，读取第一个表单
     *
     * @param excelFile 待读取文件
     * @return 读取的数据
     */
    public static List<String[]> readExcel(File excelFile) {
        return readExcel(excelFile.getParentFile(), excelFile.getName());
    }

    /**
     * excel文件读取
     *
     * @param excelFile 待读取文件
     * @return 读取的数据
     */
    public static List<List<String[]>> readAllSheets(File excelFile) {
        return readAllSheets(excelFile.getParentFile(), excelFile.getName());
    }

    /**
     * excel文件读取
     *
     * @param excelFile   待读取文件
     * @param sheetNumber 表单序号，从编号0开始
     * @return 读取的数据
     */
    public static List<String[]> readExcel(File excelFile, int sheetNumber) {
        return readExcel(excelFile.getParentFile(), excelFile.getName(), sheetNumber);
    }

    /**
     * 读excel文件，将每行的内容放进一个String类型数组，并返回String类型数组的List
     *
     * @param dir      需要读取的excel文件路径
     * @param fileName 需要读取的excel文件名称
     * @return 解析后的数组串
     */
    public static List<String[]> readExcel(File dir, final String fileName) {
        return readExcel(dir, fileName, 0);
    }


    /**
     * 读excel文件，将每行的内容放进一个String类型数组，并返回String类型数组的List
     *
     * @param dir         需要读取的excel文件路径
     * @param fileName    需要读取的excel文件名称
     * @param sheetNumber 表单序号，从编号0开始
     * @return 解析后的数组串
     */
    public static List<String[]> readExcel(File dir, final String fileName, int sheetNumber) {
        List<List<String[]>> allSheets = readAllSheets(dir, fileName, sheetNumber);
        if (allSheets == null || allSheets.isEmpty()) {
            return null;
        }
        return allSheets.get(0);
    }

    /**
     * 读excel文件，将每行的内容放进一个String类型数组，并返回String类型数组的List
     *
     * @param dir         需要读取的excel文件路径
     * @param fileName    需要读取的excel文件名称
     * @param sheetNumber 表单序号
     * @return 解析后的数组串
     */
    public static List<List<String[]>> readAllSheets(File dir, final String fileName, int... sheetNumber) {
        File excelFile = new File(dir, fileName);
        if (!excelFile.exists() || excelFile.isDirectory()) {
            return null;
        }
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(excelFile);
            return readAllSheets(is, fileName, sheetNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 针对上传的excel文件
     *
     * @param excelInputStream excel数据输入流
     * @param fileName         文件名
     * @return 表单数据
     */
    public static List<String[]> readExcel(InputStream excelInputStream, String fileName) {
        return readExcel(excelInputStream, fileName, 0);
    }

    /**
     * 针对上传的excel文件
     *
     * @param inputStream 如 MultipartFile file
     * @param fileName    文件名
     * @return 表单数据
     */
    public static List<List<String[]>> readAllSheets(InputStream inputStream, String fileName) {
        return readAllSheets(inputStream, fileName, new int[0]);
    }

    /**
     * 针对上传的excel文件
     *
     * @param inputStream 如 MultipartFile file
     * @param filename    文件名
     * @param sheetNumber 表单序号，从编号0开始
     * @return 表单数据
     */
    public static List<String[]> readExcel(InputStream inputStream, String filename, int sheetNumber) {
        return readAllSheets(inputStream, filename, new int[]{sheetNumber}).get(0);
    }

    /**
     * 针对上传的excel文件
     *
     * @param inputStream  如 MultipartFile file
     * @param filename     文件名
     * @param sheetNumbers 待取表单序号
     * @return 表单数据
     */
    public static List<List<String[]>> readAllSheets(InputStream inputStream, String filename, int... sheetNumbers) {
        List<List<String[]>> list = new ArrayList<>();
        try {
            Workbook workbook;
            filename = filename.toLowerCase();
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                // 文件类型错误，返回Empty List
                return list;
            }
            if (sheetNumbers == null || sheetNumbers.length == 0) {
                sheetNumbers = new int[workbook.getNumberOfSheets()];
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    sheetNumbers[i] = i;
                }
            }
            for (int sheetNumber : sheetNumbers) {
                Sheet sheet = workbook.getSheetAt(sheetNumber);
                int rowNum = sheet.getLastRowNum();
                if (rowNum == 0) {
                    list.add(new ArrayList<>(0));
                    continue;
                }
                // 以标题行，为完整字段数，避免结尾字段为null，不做处理
                short colNum = sheet.getRow(0).getLastCellNum();
                // 去掉第一行（标题行），读余下行
                List<String[]> sheetRows = Lists.newArrayList();
                for (int i = 1; i <= rowNum; i++) {
                    String[] values = new String[colNum];
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    boolean flag = false;
                    for (int j = 0; j < colNum; j++) {
                        Cell cell = row.getCell(j);
                        if (null != cell) {
                            values[j] = getCellValue(cell);
                        } else {
                            values[j] = "";
                        }
                        if (values[j] != null && !"".equals(values[j])) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        sheetRows.add(values);
                    }
                }
                list.add(sheetRows);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ExcelInfo readAllSheetsName(InputStream inputStream, String filename){
        ExcelInfo fileInfo = new ExcelInfo();
        List<String> sheetNames = new ArrayList<>();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            Workbook workbook;
            filename = filename.toLowerCase();
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                // 文件类型错误，返回Empty List
                return fileInfo;
            }
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                sheetNames.add(sheet.getSheetName());
                //解析字段
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setSheetName(sheet.getSheetName());
                List<String> cloumnName = new ArrayList<>();
                Row row = sheet.getRow(0);
                if (row == null) {
                    columnInfo.setColumnNames(cloumnName);
                    columnInfos.add(columnInfo);
                    continue;
                }
                short colNum = row.getLastCellNum();
                for (int j = 0; j < colNum; j++) {
                    Cell cell = row.getCell(j);
                    if (null != cell) {
                        cloumnName.add(getCellValue(cell));
                    } else {
                        cloumnName.add("");
                    }
                }
                columnInfo.setColumnNames(cloumnName);
                columnInfos.add(columnInfo);
            }
            fileInfo.setColumnInfos(columnInfos);
            fileInfo.setSheetNames(sheetNames);
        } catch (Exception e) {
            e.printStackTrace();
            return fileInfo;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileInfo;
    }

    public static List<String> readContentByCondition(InputStream inputStream, String filename, String sheetName, String columnName){
        List<String> contents = new ArrayList<>();
        try {
            Workbook workbook;
            filename = filename.toLowerCase();
            if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                // 文件类型错误，返回Empty List
                return contents;
            }
            Sheet sheet = workbook.getSheet(sheetName);
            //查找列游标
            Row firstRow = sheet.getRow(0);
            int columnNum = 0;
            for (int i = 0; i < firstRow.getLastCellNum(); i++) {
                if (columnName.equals(getCellValue(firstRow.getCell(i)))) {
                    columnNum = i;
                    break;
                }
            }
            int rowCount = sheet.getLastRowNum();
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                contents.add(getCellValue(row.getCell(columnNum)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return contents;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents;
    }


    /**
     * 在workbook中创建表单
     *
     * @param workbook       excel工作簿
     * @param sheetName      表单名
     * @param datas          表单数据
     * @param firstRowAsHead 第一行数据是否是表头
     */
    public static void createSheet(Workbook workbook, String sheetName, List<String[]> datas, boolean firstRowAsHead) {
        CellStyle contextStyle = getContextCellStyle(workbook);// 循环数据的样式
        Sheet sheet = createSheet(workbook, sheetName);
        Row row;
        String[] rowContext;
        for (int i = 0; i < datas.size(); i++) {
            row = createRow(sheet, i);
            rowContext = datas.get(i);
            for (int j = 0; j < rowContext.length; j++) {
                Cell cell;
                if (i == 0 && firstRowAsHead) {
                    cell = createCell(row, getTopCellStyle(workbook), j);
                } else {
                    cell = createCell(row, contextStyle, j);
                }
                if (rowContext[j] == null || "null".equals(rowContext[j]))
                    rowContext[j] = "";
                cell.setCellValue(rowContext[j]);
            }
        }
    }

    /**
     * 将string类型数组按行写入excel文件
     *
     * @param source    : 数据源
     * @param filePath  : 存储文件路径
     * @param sheetName ： 生成sheet页
     * @return File ： 生成excel文件
     */
    public static File writeExcel(final List<String[]> source, final String filePath, final String sheetName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        File excelFile = new File(filePath);
        Workbook book = createXLSXWorkbook();
        // 标题的样式
        CellStyle topStyle = getTopCellStyle(book);
        // 循环数据的样式
        CellStyle contextStyle = getContextCellStyle(book);
        Sheet sheet = createSheet(book, sheetName);
        fillSheet(book, sheet, source);
        try (FileOutputStream outputStream = FileUtils.openOutputStream(excelFile)) {
            book.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelFile;
    }

    /**
     * 将String类型数组的List，写入流，并返回该流
     *
     * @param source    数据来源
     * @param sheetName 表单名称
     * @return InputStream 创建的表单输入流
     * @throws IOException 如发生IO异常
     */
    public static InputStream createExcel(final List<? extends Object[]> source, final String sheetName)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook book = createWorkbook();
        Sheet sheet = createSheet(book, sheetName);
        fillSheet(book, sheet, source);
        // 第一行标题行设置固定高度
        sheet.getRow(0).setHeight((short) (350));
        book.write(out);
        out.flush();
        byte[] sourceByte = out.toByteArray();
        return new ByteArrayInputStream(sourceByte, 0, sourceByte.length);
    }

    /**
     * 创建新的Workbook, 默认情况下生成xls格式的Workbook
     *
     * @return Workbook
     */
    public static Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

    /**
     * * @Description: 创建新的xls类型的工作簿
     *
     * @return HSSFWorkbook
     */
    public static HSSFWorkbook createXLSWorkbook() {
        return new HSSFWorkbook();
    }

    /**
     * 创建新的xlsx类型的工作簿
     */
    public static XSSFWorkbook createXLSXWorkbook() {
        return new XSSFWorkbook();
    }


    /**
     * 创建新的xlsx类型的工作簿
     */
    public static SXSSFWorkbook createSXSSFWorkbook() {
        return new SXSSFWorkbook();
    }

    /**
     * 创建Sheet工作簿
     *
     * @param workbook  工作簿
     * @param sheetName 表单名称
     */
    public static Sheet createSheet(final Workbook workbook, final String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(12);
        sheet.setPrintGridlines(true);
        sheet.setDisplayGridlines(true);// 显示格线
        return sheet;
    }

    /**
     * 创建Sheet工作簿中的行Row
     *
     * @param sheet  表单
     * @param rowNum 行号
     * @return Row
     */
    public static Row createRow(final Sheet sheet, final int rowNum) {
        return sheet.createRow(rowNum);
    }

    /**
     * 创建一行中的一个cell
     *
     * @param row     行
     * @param cellNum 单元格序号
     * @return Cell 单元格
     */
    public static Cell createCell(final Row row, final int cellNum) {
        return row.createCell(cellNum);
    }

    /**
     * 创建一行中的一个cell，可设置样式
     *
     * @param row       行
     * @param cellStyle 单元格样式
     * @param cellNum   单元格序号
     * @return Cell  单元格
     */
    public static Cell createCell(final Row row, final CellStyle cellStyle, final int cellNum) {
        Cell cell = row.createCell(cellNum);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 设置标题头样式
     *
     * @param book 工作簿
     * @return CellStyle
     */
    public static CellStyle getTopCellStyle(Workbook book) {
        CellStyle topCellStyle = book.createCellStyle();
        // 字体样式类
        Font font = book.createFont();
        // 标题字体
        font.setFontName("宋体");
        // 标题字体加粗
        font.setBold(true);
        // 字体大小
        font.setFontHeightInPoints((short) 12);
        // 设置字体样式
        topCellStyle.setFont(font);
        // 标题居中
        topCellStyle.setAlignment(HorizontalAlignment.CENTER);
        topCellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        topCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        topCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置单元格边框 上、下、左、右
        topCellStyle.setBorderRight(BorderStyle.THIN);
        topCellStyle.setBorderLeft(BorderStyle.THIN);
        topCellStyle.setBorderBottom(BorderStyle.THIN);
        topCellStyle.setBorderTop(BorderStyle.THIN);
        return topCellStyle;
    }

    /**
     * 设置普通内容样式
     *
     * @param book 工作簿
     * @return CellStyle 样式
     */
    public static CellStyle getContextCellStyle(Workbook book) {
        CellStyle contextCellStyle = book.createCellStyle();
        contextCellStyle.setWrapText(false);
        contextCellStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        contextCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        // 设置单元格边框 上、下、左、右
        contextCellStyle.setBorderRight(BorderStyle.THIN);
        contextCellStyle.setBorderLeft(BorderStyle.THIN);
        contextCellStyle.setBorderBottom(BorderStyle.THIN);
        contextCellStyle.setBorderTop(BorderStyle.THIN);
        return contextCellStyle;
    }

    public static CellStyle getCommonTopCellStyle(Workbook book) {
        CellStyle topCellStyle = book.createCellStyle();
        Font font = book.createFont(); // 字体样式类
        font.setFontName("宋体"); // 标题字体
        font.setBold(true); // 标题字体加粗
        font.setFontHeightInPoints((short) 12); // 字体大小
        topCellStyle.setFont(font); // 设置字体样式
        topCellStyle.setAlignment(HorizontalAlignment.CENTER); // 标题居中
        topCellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        return topCellStyle;
    }

    /**
     * 获取cell 样式,不含边框
     *
     * @param book 工作簿
     * @return 样式
     */
    public static CellStyle getCommonStyle(Workbook book) {
        CellStyle contextCellStyle = book.createCellStyle();
        contextCellStyle.setWrapText(false);
        contextCellStyle.setAlignment(HorizontalAlignment.LEFT);
        contextCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        return contextCellStyle;
    }

    /**
     * 设置数字内容样式
     *
     * @param book 工作簿
     * @return 样式
     */
    public static CellStyle getNumCellStyle(Workbook book) {
        CellStyle contextCellStyle = book.createCellStyle();
        contextCellStyle.setWrapText(false);
        contextCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        contextCellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        return contextCellStyle;
    }

    /**
     * 获得Excel单个字段值，并转成String类型
     *
     * @param cell 单元格
     * @return 单元格数据
     */
    public static String getCellValue(final Cell cell) {
        String value = "";
        if (cell == null)
            return value;
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = DateUtils.formatDate(cell.getDateCellValue());
                } else {
                    value = DEFAULT_DECIMAL_FORMAT.format(cell.getNumericCellValue());
                }
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = "";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }


    /**
     * 填充表单数据，自动跳转列宽
     *
     * @param book   工作簿
     * @param sheet  表单
     * @param source 来源数据
     */
    private static void fillSheet(Workbook book, Sheet sheet, List<? extends Object[]> source) {
        fillSheet(book, sheet, source, true);
    }

    /**
     * 填充表单数据
     *
     * @param book                 工作簿
     * @param sheet                表单
     * @param source               来源数据
     * @param autoAdjustColumnSize 是否自动调整列宽
     */
    public static void fillSheet(Workbook book, Sheet sheet, List<? extends Object[]> source, boolean autoAdjustColumnSize) {
        CellStyle topStyle = getCommonTopCellStyle(book); // 标题的样式
        CellStyle contextStyle = getCommonStyle(book); // 循环数据的样式
        int maxColumnSize = -1;
        int lastRow = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < source.size(); i++) {
            Object[] rowContext = source.get(i);
            if (rowContext == null) {
                continue;
            }
            Row row = createRow(sheet, i + lastRow);
            int columnSize = rowContext.length;
            if (columnSize > maxColumnSize) {
                maxColumnSize = columnSize;
            }
            for (int j = 0; j < rowContext.length; j++) {
                Cell cell;
                if (lastRow == 0 && i == 0) {
                    cell = createCell(row, topStyle, j);
                } else {
                    cell = createCell(row, contextStyle, j);
                }
                String data = Objects.toString(rowContext[j]);
                if (data == null || "null".equals(data)) {
                    data = "";
                }
                cell.setCellValue(data);
            }
        }
        if (autoAdjustColumnSize && maxColumnSize > 0) {
            for (int j = 0; j < maxColumnSize; j++) {
                sheet.autoSizeColumn((short) j);
            }
        }
    }
}