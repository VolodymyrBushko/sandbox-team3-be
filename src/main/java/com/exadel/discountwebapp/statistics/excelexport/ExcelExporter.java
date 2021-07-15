package com.exadel.discountwebapp.statistics.excelexport;

import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.vo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.VendorVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.OthersDiscountsVO;
import com.exadel.discountwebapp.statistics.vo.uservo.OthersUsersVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ExcelExporter {
    private static final String QUANTITY = "quantity";
    private static final String TITLE = "title";

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private SummaryStatisticsDTO summaryStats;

    public ExcelExporter(SummaryStatisticsDTO summaryStats) {
        this.summaryStats = summaryStats;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("statistics");
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        var cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    private void writeDataLines() {
        var rowCount = 0;
        Row rowTitleUsers = sheet.createRow(rowCount++);
        Row rowUsers = sheet.createRow(rowCount++);

        CellStyle style16 = setStyle(16);
        CellStyle style15 = setStyle(15);

        createCell(rowTitleUsers, 0, "Top most active Users", style16);
        createCell(rowUsers, 0, "firstName", style15);
        createCell(rowUsers, 1, "lastName", style15);
        createCell(rowUsers, 2, "email", style15);
        createCell(rowUsers, 3, QUANTITY, style15);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Map.Entry<UserVO, Long> pair : summaryStats.getMostActiveUsersStats().entrySet()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, pair.getKey().getFirstName(), style);
            createCell(row, columnCount++, pair.getKey().getLastName(), style);
            createCell(row, columnCount++, pair.getKey().getEmail(), style);
            createCell(row, columnCount, pair.getValue(), style);
            if (pair.getKey() instanceof OthersUsersVO) {
                createCell(row, 0, ((OthersUsersVO) pair.getKey()).getTitle(), style);
            }
        }

        rowCount++;
        Row rowTitleCategory = sheet.createRow(rowCount++);
        Row rowCategory = sheet.createRow(rowCount++);

        createCell(rowTitleCategory, 0, "Top most popular Categories", style16);
        createCell(rowCategory, 0, TITLE, style15);
        createCell(rowCategory, 1, QUANTITY, style15);


        for (Map.Entry<CategoryVO, Long> pair : summaryStats.getPopularCategoriesStats().entrySet()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, pair.getKey().getTitle(), style);
            createCell(row, columnCount, pair.getValue(), style);
        }

        rowCount++;
        Row rowTitleVendor = sheet.createRow(rowCount++);
        Row rowVendor = sheet.createRow(rowCount++);

        createCell(rowTitleVendor, 0, "Top most popular Vendors", style16);
        createCell(rowVendor, 0, TITLE, style15);
        createCell(rowVendor, 1, QUANTITY, style15);

        for (Map.Entry<VendorVO, Long> pair : summaryStats.getPopularVendorsStats().entrySet()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, pair.getKey().getTitle(), style);
            createCell(row, columnCount, pair.getValue(), style);
        }

        rowCount++;
        Row rowTitleDiscountViews = sheet.createRow(rowCount++);
        Row rowDiscountViews = sheet.createRow(rowCount++);

        createCell(rowTitleDiscountViews, 0, "Top most popular discounts by views", style16);
        createCell(rowDiscountViews, 0, "id", style15);
        createCell(rowDiscountViews, 1, TITLE, style15);
        createCell(rowDiscountViews, 2, QUANTITY, style15);

        for (Map.Entry<DiscountVO, Long> pair : summaryStats.getPopularDiscountsStats().entrySet()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, pair.getKey().getId(), style);
            createCell(row, columnCount++, pair.getKey().getTitle(), style);
            createCell(row, columnCount, pair.getValue(), style);
            if (pair.getKey() instanceof OthersDiscountsVO) {
                createCell(row, 0, pair.getKey().getTitle(), style);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private CellStyle setStyle(Integer num) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(num);
        style.setFont(font);
        return style;
    }

}
