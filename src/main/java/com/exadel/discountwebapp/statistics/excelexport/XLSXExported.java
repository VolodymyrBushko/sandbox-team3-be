package com.exadel.discountwebapp.statistics.excelexport;

import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedSummaryStatsDTO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedCategoryVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedDiscountVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedUserVO;
import com.exadel.discountwebapp.statistics.extendedvo.ExtendedVendorVO;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class XLSXExported {
    private static final String QUANTITY = "Quantity";
    private static final String TITLE = "Title";
    private static final String NUMBER_SIGN = "№";
    private static final String EMAIL = "Email";
    private static final String DESCRIPTION = "Description";
    private static final String HEADLINE_USERS = "users";
    private static final String HEADLINE_CATEGORIES = "categories";
    private static final String HEADLINE_VENDORS = "vendors";
    private static final String HEADLINE_DIS_VIEWS = "discount views";
    private static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final ExtendedSummaryStatsDTO extendedSummaryStats;

    public XLSXExported(ExtendedSummaryStatsDTO summaryStats) {
        this.extendedSummaryStats = summaryStats;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(String name) {
        sheet = workbook.createSheet(name);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        var cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        writeHeaderLine(HEADLINE_USERS);

        var rowCount = 0;
        Row rowTitleUsers = sheet.createRow(rowCount++);
        rowCount++;
        Row rowUsers = sheet.createRow(rowCount++);

        CellStyle style16 = setStyle(16);
        CellStyle style15 = setStyle(15);

        createCell(rowTitleUsers, 0, "Active Users", style16);
        createCell(rowUsers, 0, NUMBER_SIGN, style15);
        createCell(rowUsers, 1, "First Name", style15);
        createCell(rowUsers, 2, "Last Name", style15);
        createCell(rowUsers, 3, EMAIL, style15);
        createCell(rowUsers, 4, "Country", style15);
        createCell(rowUsers, 5, "City", style15);
        createCell(rowUsers, 6, "Role", style15);
        createCell(rowUsers, 7, QUANTITY, style15);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        var urCounter = 1;
        for (ExtendedUserVO elem : extendedSummaryStats.getExtendedUsersStats()) {
            Row row = sheet.createRow(rowCount++);
            var columnCount = 0;
            createCell(row, columnCount++, urCounter++, style);
            createCell(row, columnCount++, elem.getFirstName(), style);
            createCell(row, columnCount++, elem.getLastName(), style);
            createCell(row, columnCount++, elem.getEmail(), style);
            createCell(row, columnCount++, elem.getCountry(), style);
            createCell(row, columnCount++, elem.getCity(), style);
            createCell(row, columnCount++, elem.getRole(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }

        writeHeaderLine(HEADLINE_CATEGORIES);

        var rowCount2 = 0;
        Row rowTitleCategory = sheet.createRow(rowCount2++);
        rowCount2++;
        Row rowCategory = sheet.createRow(rowCount2++);

        createCell(rowTitleCategory, 0, "Popular Categories", style16);
        createCell(rowCategory, 0, NUMBER_SIGN, style15);
        createCell(rowCategory, 1, TITLE, style15);
        createCell(rowCategory, 2, QUANTITY, style15);

        var catCounter = 1;
        for (ExtendedCategoryVO elem : extendedSummaryStats.getExtendedCategoriesStats()) {
            Row row = sheet.createRow(rowCount2++);
            var columnCount = 0;
            createCell(row, columnCount++, catCounter++, style);
            createCell(row, columnCount++, elem.getTitle(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }

        writeHeaderLine(HEADLINE_VENDORS);
        var rowCount3 = 0;
        Row rowTitleVendor = sheet.createRow(rowCount3++);
        rowCount3++;
        Row rowVendor = sheet.createRow(rowCount3++);

        createCell(rowTitleVendor, 0, "Popular Vendors", style16);
        createCell(rowVendor, 0, NUMBER_SIGN, style15);
        createCell(rowVendor, 1, TITLE, style15);
        createCell(rowVendor, 2, DESCRIPTION, style15);
        createCell(rowVendor, 3, EMAIL, style15);
        createCell(rowVendor, 4, QUANTITY, style15);

        var vnCounter = 1;
        for (ExtendedVendorVO elem : extendedSummaryStats.getExtendedVendorsStats()) {
            Row row = sheet.createRow(rowCount3++);
            var columnCount = 0;
            createCell(row, columnCount++, vnCounter++, style);
            createCell(row, columnCount++, elem.getTitle(), style);
            createCell(row, columnCount++, elem.getDescription(), style);
            createCell(row, columnCount++, elem.getEmail(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }

        writeHeaderLine(HEADLINE_DIS_VIEWS);

        var rowCount4 = 0;
        Row rowTitleDiscountViews = sheet.createRow(rowCount4++);
        rowCount4++;
        Row rowDiscountViews = sheet.createRow(rowCount4++);

        createCell(rowTitleDiscountViews, 0, "Popular discounts by views", style16);
        createCell(rowDiscountViews, 0, NUMBER_SIGN, style15);
        createCell(rowDiscountViews, 1, TITLE, style15);
        createCell(rowDiscountViews, 2, "Short Description", style15);
        createCell(rowDiscountViews, 3, DESCRIPTION, style15);
        createCell(rowDiscountViews, 4, "Promocode", style15);
        createCell(rowDiscountViews, 5, "Percentage", style15);
        createCell(rowDiscountViews, 6, "Flat amount", style15);
        createCell(rowDiscountViews, 7, "Start Date", style15);
        createCell(rowDiscountViews, 8, "Expiration Date", style15);
        createCell(rowDiscountViews, 9, "Vendor", style15);
        createCell(rowDiscountViews, 10, "Category", style15);
        createCell(rowDiscountViews, 11, QUANTITY, style15);

        var disCounter = 1;
        for (ExtendedDiscountVO elem : extendedSummaryStats.getExtendedDiscountsStats()) {
            Row row = sheet.createRow(rowCount4++);
            var columnCount = 0;
            createCell(row, columnCount++, disCounter++, style);
            createCell(row, columnCount++, elem.getTitle(), style);
            createCell(row, columnCount++, elem.getShortDescription(), style);
            createCell(row, columnCount++, elem.getDescription(), style);
            createCell(row, columnCount++, elem.getPromocode(), style);
            createCell(row, columnCount++, String.valueOf(elem.getPercentage() == null ? "" : elem.getPercentage()), style);
            createCell(row, columnCount++, String.valueOf(elem.getFlatAmount()), style);
            createCell(row, columnCount++, new SimpleDateFormat(DATE_PATTERN).format(Timestamp.valueOf(elem.getStartDate())), style);
            createCell(row, columnCount++, new SimpleDateFormat(DATE_PATTERN).format(Timestamp.valueOf(elem.getExpirationDate())), style);
            createCell(row, columnCount++, elem.getVendorTitle(), style);
            createCell(row, columnCount++, elem.getCategoryTitle(), style);
            createCell(row, columnCount, elem.getViewNumber(), style);
        }
    }

    @SneakyThrows
    public void export(OutputStream outputStream) {
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
