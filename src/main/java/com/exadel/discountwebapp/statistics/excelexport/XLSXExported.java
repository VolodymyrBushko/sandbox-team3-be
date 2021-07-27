package com.exadel.discountwebapp.statistics.excelexport;

import com.exadel.discountwebapp.statistics.dto.extendeddto.ExtendedSummaryStatsDTO;
import com.exadel.discountwebapp.statistics.extendedvo.*;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class XLSXExported {
    private static final String ACTIVATED_QUANTITY = "Activated Discounts Quantity";
    private static final String VIEWS_QUANTITY = "Views Quantity";
    private static final String PERIOD = "Period: ";
    private static final String TITLE = "Title";
    private static final String NUMBER_SIGN = "№";
    private static final String EMAIL = "Email";
    private static final String CATEGORY = "Category";
    private static final String DESCRIPTION = "Description";
    private static final String FIRST_NAME = "First Name";
    private static final String LAST_NAME = "Last Name";
    private static final String HEADLINE_USERS = "users";
    private static final String HEADLINE_CATEGORIES = "categories";
    private static final String HEADLINE_VENDORS = "vendors";
    private static final String HEADLINE_DIS_VIEWS = "discount views";
    private static final String HEADLINE_UR_PREFERENCE = "users preference";
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final ExtendedSummaryStatsDTO extendedSummaryStats;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public XLSXExported(ExtendedSummaryStatsDTO summaryStats, LocalDate dateFrom, LocalDate dateTo) {
        this.extendedSummaryStats = summaryStats;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

        XSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeight(15);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.index);
        style15.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
        style15.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style15.setFont(headerFont);

        createCell(rowTitleUsers, 0, "Active Users", style16);
        getFieldLabels(rowTitleUsers, rowUsers, style16, style15, ACTIVATED_QUANTITY);

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
        createCell(rowTitleCategory, 1, PERIOD + dateFrom + " - " + dateTo, style16);
        createCell(rowCategory, 0, NUMBER_SIGN, style15);
        createCell(rowCategory, 1, TITLE, style15);
        createCell(rowCategory, 2, ACTIVATED_QUANTITY, style15);

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
        createCell(rowTitleVendor, 1, PERIOD + dateFrom + " - " + dateTo, style16);
        createCell(rowVendor, 0, NUMBER_SIGN, style15);
        createCell(rowVendor, 1, TITLE, style15);
        createCell(rowVendor, 2, DESCRIPTION, style15);
        createCell(rowVendor, 3, EMAIL, style15);
        createCell(rowVendor, 4, "Phone Number", style15);
        createCell(rowVendor, 5, ACTIVATED_QUANTITY, style15);

        var vnCounter = 1;
        for (ExtendedVendorVO elem : extendedSummaryStats.getExtendedVendorsStats()) {
            Row row = sheet.createRow(rowCount3++);
            var columnCount = 0;
            createCell(row, columnCount++, vnCounter++, style);
            createCell(row, columnCount++, elem.getTitle(), style);
            createCell(row, columnCount++, elem.getDescription(), style);
            createCell(row, columnCount++, elem.getEmail(), style);
            createCell(row, columnCount++, elem.getPhoneNumber(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }

        writeHeaderLine(HEADLINE_DIS_VIEWS);

        var rowCount4 = 0;
        Row rowTitleDiscountViews = sheet.createRow(rowCount4++);
        rowCount4++;
        Row rowDiscountViews = sheet.createRow(rowCount4++);

        createCell(rowTitleDiscountViews, 0, "Popular discounts by views", style16);
        createCell(rowTitleDiscountViews, 1, "Period: Since Inception ", style16);
        createCell(rowDiscountViews, 0, NUMBER_SIGN, style15);
        createCell(rowDiscountViews, 1, TITLE, style15);
        createCell(rowDiscountViews, 2, "Short Description", style15);
        createCell(rowDiscountViews, 3, DESCRIPTION, style15);
        createCell(rowDiscountViews, 4, "Promocode", style15);
        createCell(rowDiscountViews, 5, "Percentage", style15);
        createCell(rowDiscountViews, 6, "Flat Amount", style15);
        createCell(rowDiscountViews, 7, "Created Date", style15);
        createCell(rowDiscountViews, 8, "Start Date", style15);
        createCell(rowDiscountViews, 9, "Expiration Date", style15);
        createCell(rowDiscountViews, 10, "Vendor", style15);
        createCell(rowDiscountViews, 11, CATEGORY, style15);
        createCell(rowDiscountViews, 12, VIEWS_QUANTITY, style15);
        createCell(rowDiscountViews, 13, "Activated", style15);

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
            createCell(row, columnCount++, String.valueOf(elem.getFlatAmount() == null ? "" : elem.getFlatAmount()), style);
            createCell(row, columnCount++, new SimpleDateFormat(DATE_PATTERN).format(Timestamp.valueOf(elem.getCreated())), style);
            createCell(row, columnCount++, new SimpleDateFormat(DATE_PATTERN).format(Timestamp.valueOf(elem.getStartDate())), style);
            createCell(row, columnCount++, new SimpleDateFormat(DATE_PATTERN).format(Timestamp.valueOf(elem.getExpirationDate())), style);
            createCell(row, columnCount++, elem.getVendorTitle(), style);
            createCell(row, columnCount++, elem.getCategoryTitle(), style);
            createCell(row, columnCount++, elem.getViewNumber(), style);
            createCell(row, columnCount, elem.getActivated() == null ? 0 : elem.getActivated(), style);
        }

        writeHeaderLine(HEADLINE_UR_PREFERENCE);

        var rowCount5 = 0;
        Row rowTitleUsersPref = sheet.createRow(rowCount5++);
        rowCount5++;
        Row rowUsersPref = sheet.createRow(rowCount5++);

        createCell(rowTitleUsersPref, 0, "Users Preference", style16);
        getFieldLabels(rowTitleUsersPref, rowUsersPref, style16, style15, CATEGORY);
        createCell(rowUsersPref, 8, ACTIVATED_QUANTITY, style15);

        var urPrefCounter = 1;
        for (ExtendedUsersPreferenceVO elem : extendedSummaryStats.getExtendedUsersPreference()) {
            Row row = sheet.createRow(rowCount5++);
            var columnCount = 0;
            createCell(row, columnCount++, urPrefCounter++, style);
            createCell(row, columnCount++, elem.getFirstName(), style);
            createCell(row, columnCount++, elem.getLastName(), style);
            createCell(row, columnCount++, elem.getEmail(), style);
            createCell(row, columnCount++, elem.getCountry(), style);
            createCell(row, columnCount++, elem.getCity(), style);
            createCell(row, columnCount++, elem.getRole(), style);
            createCell(row, columnCount++, elem.getCategory(), style);
            createCell(row, columnCount, elem.getQuantity(), style);
        }
    }

    private void getFieldLabels(Row rowTitleUsers, Row rowUsers, CellStyle style16, CellStyle style15, String activatedQuantity) {
        createCell(rowTitleUsers, 1, PERIOD + dateFrom + " - " + dateTo, style16);
        createCell(rowUsers, 0, NUMBER_SIGN, style15);
        createCell(rowUsers, 1, FIRST_NAME, style15);
        createCell(rowUsers, 2, LAST_NAME, style15);
        createCell(rowUsers, 3, EMAIL, style15);
        createCell(rowUsers, 4, "Country", style15);
        createCell(rowUsers, 5, "City", style15);
        createCell(rowUsers, 6, "Role", style15);
        createCell(rowUsers, 7, activatedQuantity, style15);
    }

    @SneakyThrows
    public void export(OutputStream outputStream) {
        writeDataLines();
        workbook.write(outputStream);
        workbook.close();
    }

    private CellStyle setStyle(Integer number) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(number);
        style.setFont(font);
        return style;
    }
}
