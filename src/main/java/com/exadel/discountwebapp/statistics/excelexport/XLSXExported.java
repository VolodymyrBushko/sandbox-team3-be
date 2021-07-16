package com.exadel.discountwebapp.statistics.excelexport;

import com.exadel.discountwebapp.statistics.dto.SummaryStatisticsDTO;
import com.exadel.discountwebapp.statistics.vo.CategoryVO;
import com.exadel.discountwebapp.statistics.vo.vendorvo.OthersVendorsVO;
import com.exadel.discountwebapp.statistics.vo.vendorvo.VendorVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.DiscountVO;
import com.exadel.discountwebapp.statistics.vo.discountvo.OthersDiscountsVO;
import com.exadel.discountwebapp.statistics.vo.uservo.OthersUsersVO;
import com.exadel.discountwebapp.statistics.vo.uservo.UserVO;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;

public class XLSXExported {
    private static final String QUANTITY = "quantity";
    private static final String TITLE = "title";
    private static final String ID = "id";

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private SummaryStatisticsDTO summaryStats;

    public XLSXExported(SummaryStatisticsDTO summaryStats) {
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

        for (UserVO elem : summaryStats.getMostActiveUsersStats()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
                createCell(row, columnCount++, elem.getFirstName(), style);
                createCell(row, columnCount++, elem.getLastName(), style);
                createCell(row, columnCount++, elem.getEmail(), style);
                createCell(row, columnCount, elem.getQuantity(), style);
            if (elem instanceof OthersUsersVO) {
                createCell(row, 0, ((OthersUsersVO) elem).getOthersTitle(), style);
                createCell(row, 3, ((OthersUsersVO) elem).getOthersQuantity(), style);
            }
        }

        rowCount++;
        Row rowTitleCategory = sheet.createRow(rowCount++);
        Row rowCategory = sheet.createRow(rowCount++);

        createCell(rowTitleCategory, 0, "Top most popular Categories", style16);
        createCell(rowCategory, 0, TITLE, style15);
        createCell(rowCategory, 1, QUANTITY, style15);


        for (CategoryVO elem : summaryStats.getPopularCategoriesStats()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, elem.getTitle(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }

        rowCount++;
        Row rowTitleVendor = sheet.createRow(rowCount++);
        Row rowVendor = sheet.createRow(rowCount++);

        createCell(rowTitleVendor, 0, "Top most popular Vendors", style16);
        createCell(rowVendor, 0, ID, style15);
        createCell(rowVendor, 1, TITLE, style15);
        createCell(rowVendor, 2, QUANTITY, style15);

        for (VendorVO elem : summaryStats.getPopularVendorsStats()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
                createCell(row, columnCount++, elem.getId(), style);
                createCell(row, columnCount++, elem.getTitle(), style);
                createCell(row, columnCount, elem.getQuantity(), style);
            if (elem instanceof OthersVendorsVO) {
                createCell(row, 0, ((OthersVendorsVO) elem).getOthersTitle(), style);
                createCell(row, 2, ((OthersVendorsVO) elem).getOthersQuantity(), style);
            }
        }

        rowCount++;
        Row rowTitleDiscountViews = sheet.createRow(rowCount++);
        Row rowDiscountViews = sheet.createRow(rowCount++);

        createCell(rowTitleDiscountViews, 0, "Top most popular discounts by views", style16);
        createCell(rowDiscountViews, 0, ID, style15);
        createCell(rowDiscountViews, 1, TITLE, style15);
        createCell(rowDiscountViews, 2, QUANTITY, style15);

        for (DiscountVO elem : summaryStats.getPopularDiscountsStats()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
                createCell(row, columnCount++, elem.getId(), style);
                createCell(row, columnCount++, elem.getTitle(), style);
                createCell(row, columnCount, elem.getQuantity(), style);
            if (elem instanceof OthersDiscountsVO) {
                createCell(row, 0, ((OthersDiscountsVO) elem).getOthersTitle(), style);
                createCell(row, 2, ((OthersDiscountsVO) elem).getOthersQuantity(), style);
            }
        }
    }

    @SneakyThrows
    public void export(OutputStream outputStream) {
        writeHeaderLine();
        writeDataLines();
        workbook.write(outputStream);
        workbook.close();
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
