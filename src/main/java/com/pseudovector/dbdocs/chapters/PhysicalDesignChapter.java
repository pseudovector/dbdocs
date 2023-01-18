package com.pseudovector.dbdocs.chapters;

import java.awt.Color;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.common.base.Strings;
import com.pseudovector.dbdocs.presets.ColorPresets;
import com.pseudovector.dbdocs.presets.StylePresets;
import com.pseudovector.dbdocs.service.DataSourceDetailService;
import com.pseudovector.dbdocs.util.Chapters;
import com.pseudovector.dbdocs.util.Paragraphs;
import com.pseudovector.dbdocs.util.Sections;
import com.pseudovector.dbdocs.util.SpringContextHolder;
import com.pseudovector.dbdocs.util.SqlFormatter;
import com.pseudovector.dbdocs.util.StylePresetResolver;
import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.lowagie.text.Table;

public final class PhysicalDesignChapter {

    private PhysicalDesignChapter() {
    }

    private static final String TABLE_SCHEMA = "table_schema";

    private static final String TABLE_NAME = "table_name";

    private static final String TABLE_COMMENT = "table_comment";

    private static final String VIEW_DEFINITION = "view_definition";

    private static final String VIEW_IS_UPDATABLE = "is_updatable";

    private static final String INDEX_NAME = "index_name";

    private static final String INDEX_TYPE = "index_type";

    private static final String INDEX_COMMENT = "index_comment";

    private static final String INDEX_COLUMN = "column_name";

    private static final String INDEX_IS_UNIQUE = "is_unique";

    private static final String INDEX_IS_NULLABLE = "is_nullable";

    private static final DataSourceDetailService dataService = SpringContextHolder
            .getBean(DataSourceDetailService.class);

    public static void init(Document document, int chapterIdx, List<String> dbNames) throws DocumentException {

        Chapter chapter = Chapters.newChapter("物理设计", chapterIdx);

        Section tableListSection = Sections.newSection(chapter, "表清单");
        handleTableList(tableListSection, dbNames);

        Section tableDesignSection = Sections.newSection(chapter, "表设计");
        handleTableSchemaInfo(tableDesignSection, dbNames);

        Section viewListSection = Sections.newSection(chapter, "视图清单");
        handleViewList(viewListSection, dbNames);

        Section viewDesignSection = Sections.newSection(chapter, "视图设计");
        handleViewDefs(viewDesignSection, dbNames);

        Section indexListSection = Sections.newSection(chapter, "索引清单");
        handleIndexList(indexListSection, dbNames);

        Section indexDesignSection = Sections.newSection(chapter, "索引设计");
        handleIndexDetails(indexDesignSection, dbNames);

        document.add(chapter);
    }

    private static void handleTableList(Section tableListSection, List<String> dbNames) throws DocumentException {
        List<Map<String, String>> tableSchemas = dataService.getAllDataSourceName(dbNames);
        if (tableSchemas.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            tableListSection.add(none);
            return;
        }

        String dbName0 = "";
        Section dbSection = null;
        int tableIdx = 0;
        Table table = null;
        for (Map<String, String> tableSchema : tableSchemas) {
            // 数据库名
            String dbName = tableSchema.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            // 表名
            String tableName = tableSchema.get(TABLE_NAME).toLowerCase(Locale.getDefault());
            // 表说明
            String tableComment = tableSchema.get(TABLE_COMMENT).toLowerCase(Locale.getDefault());

            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(tableListSection, dbName + "库");

                int colNumber = 3;
                tableIdx = 0;
                table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);
                dbSection.add(table);

                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] headerContents = { "序号", "表名", "说明" };
                for (String head : headerContents) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(head, StylePresets.TABLE_HEADER));
                    cell.setHeader(true);
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                table.endHeaders(); // 表头结束
            }

            if (table != null) {
                String[] rowContents = { ++tableIdx + "", tableName, tableComment };
                for (int j = 0; j < rowContents.length; j++) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(rowContents[j], StylePresets.TABLE_CELL));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
        }

    }

    private static void handleViewList(Section viewListSection, List<String> dbNames) throws DocumentException {
        List<Map<String, String>> viewDefs = dataService.getViewDefinitions(dbNames);
        if (viewDefs.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            viewListSection.add(none);
            return;
        }

        String dbName0 = "";
        Section dbSection = null;
        int tableIdx = 0;
        Table table = null;
        for (Map<String, String> viewDef : viewDefs) {
            // 数据库名
            String dbName = viewDef.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            // 表名
            String tableName = viewDef.get(TABLE_NAME).toLowerCase(Locale.getDefault());

            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(viewListSection, dbName + "库");

                int colNumber = 2;
                tableIdx = 0;
                table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);
                dbSection.add(table);

                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] headerContents = { "序号", "视图名" };
                for (String head : headerContents) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(head, StylePresets.TABLE_HEADER));
                    cell.setHeader(true);
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                table.endHeaders(); // 表头结束
            }

            if (table != null) {
                String[] rowContents = { ++tableIdx + "", tableName };
                for (int j = 0; j < rowContents.length; j++) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(rowContents[j], StylePresets.TABLE_CELL));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
        }
    }

    private static void handleIndexList(Section indexListSection, List<String> dbNames) throws DocumentException {
        List<Map<String, String>> indexDetails = dataService.getIndexDetails(dbNames);
        if (indexDetails.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            indexListSection.add(none);
            return;
        }

        String dbName0 = "";
        Section dbSection = null;
        int tableIdx = 0;
        Table table = null;
        for (Map<String, String> indexDetail : indexDetails) {

            String dbName = indexDetail.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            String tableName = indexDetail.get(TABLE_NAME).toLowerCase(Locale.getDefault()); 
            String indexName = indexDetail.get(INDEX_NAME).toLowerCase(Locale.getDefault());

            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(indexListSection, dbName + "库");

                int colNumber = 3;
                tableIdx = 0;
                table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);
                dbSection.add(table);

                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] headerContents = { "序号", "对应表", "索引名称" };
                for (String head : headerContents) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(head, StylePresets.TABLE_HEADER));
                    cell.setHeader(true);
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                table.endHeaders(); // 表头结束
            }

            if (table != null) {
                String[] rowContents = { ++tableIdx + "", tableName, indexName };
                for (int j = 0; j < rowContents.length; j++) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(rowContents[j], StylePresets.TABLE_CELL));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
        }
    }

    @SuppressWarnings("java:S3776")
    private static void handleTableSchemaInfo(Section tableDesignSection, List<String> dbNames)
            throws DocumentException {
        List<Map<String, String>> tableSchemas = dataService.getAllDataSourceName(dbNames);
        if (tableSchemas.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            tableDesignSection.add(none);
            return;
        }

        String dbName0 = "";
        Section dbSection = null;
        for (Map<String, String> tableSchema : tableSchemas) {

            // 数据库名
            String dbName = tableSchema.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(tableDesignSection, dbName + "库");
            }

            if (null != dbSection) {
                // 表名
                String tableName = tableSchema.get(TABLE_NAME).toLowerCase(Locale.getDefault());

                // 表说明
                String tableComment = tableSchema.get(TABLE_COMMENT).toLowerCase(Locale.getDefault());

                // 中文表名
                String tableTitle = Strings.isNullOrEmpty(tableComment) ? tableName + "表" : tableComment;
                String indexNames = "";
                if (tableSchema.containsKey(INDEX_NAME)) {
                    indexNames = tableSchema.get(INDEX_NAME).toLowerCase(Locale.getDefault());
                }

                // 创建有6列的表格
                int colNumber = 6;
                Table table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);

                /*
                 * 添加表头的元素，并设置表头背景的颜色
                 */
                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] virtHeaders = { "表名", tableName, "说明", tableComment, "索引", indexNames };
                for (int j = 0; j < virtHeaders.length; j++) {
                    Cell cell = new Cell();
                    if (j % 2 == 0) {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_HEADER));
                        cell.setHeader(true);
                        cell.setBackgroundColor(headerColor);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    } else {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_CELL));
                        cell.setColspan(5);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    table.addCell(cell);
                }

                String[] headerContents = { "序号", "字段名称", "字段说明", "类型", "是否为空", "主键" };
                for (String head : headerContents) {
                    Cell cell = new Cell();
                    cell.addElement(new Paragraph(head, StylePresets.TABLE_HEADER));
                    cell.setHeader(true);
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
                table.endHeaders(); // 表头结束

                // 获取某张表的所有字段说明
                List<Map<String, String>> list = dataService.getDataSourceDetail(dbName, tableName);

                // 表格的主体
                for (int k = 0; k < list.size(); k++) {
                    // 获取某表每个字段的详细说明
                    Map<String, String> map = list.get(k);
                    String field = map.get("Field").toLowerCase(Locale.getDefault());
                    String type = map.get("Type").toLowerCase(Locale.getDefault());
                    String nullable = map.get("Null").toLowerCase(Locale.getDefault());
                    String key = map.get("Key").toLowerCase(Locale.getDefault());
                    String comment = map.get("Comment").toLowerCase(Locale.getDefault());
                    String[] rowContents = { (k + 1) + "", field, comment, type, nullable, key };
                    for (int j = 0; j < rowContents.length; j++) {
                        Cell cell = new Cell();
                        cell.addElement(new Paragraph(rowContents[j], StylePresets.TABLE_CELL));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }
                }

                Section tableSection = Sections.newSection(dbSection, tableTitle);
                tableSection.add(table);
            }
        }
    }

    @SuppressWarnings("java:S3776")
    private static void handleViewDefs(Section viewDesignSection, List<String> dbNames) throws DocumentException {
        List<Map<String, String>> viewDefs = dataService.getViewDefinitions(dbNames);
        if (viewDefs.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            viewDesignSection.add(none);
            return;
        }
        String dbName0 = "";
        Section dbSection = null;
        for (Map<String, String> viewDef : viewDefs) {
            // 数据库名
            String dbName = viewDef.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(viewDesignSection, dbName + "库");
            }

            if (null != dbSection) {
                // 表名
                String tableName = viewDef.get(TABLE_NAME).toLowerCase(Locale.getDefault());
                String isUpdatable = viewDef.get(VIEW_IS_UPDATABLE).toLowerCase(Locale.getDefault());

                String viewDefinition = viewDef.get(VIEW_DEFINITION);
                if (!Strings.isNullOrEmpty(viewDefinition)) {
                    viewDefinition = SqlFormatter.prettyPrint(viewDefinition);
                }
                Paragraph tableHeading = new Paragraph("视图" + tableName,
                        StylePresetResolver.byDepth(dbSection.getDepth() + 1));
                Section viewSection = dbSection.addSection(tableHeading);

                // 创建有2列的表格
                int colNumber = 2;
                Table table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);
                viewSection.add(table);

                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] virtHeaders = { "视图名", tableName, "是否可更新", isUpdatable };
                for (int j = 0; j < virtHeaders.length; j++) {
                    Cell cell = new Cell();
                    if (j % 2 == 0) {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_HEADER));
                        cell.setHeader(true);
                        cell.setBackgroundColor(headerColor);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    } else {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_CELL));
                        cell.setColspan(1);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }
                    table.addCell(cell);
                }

                Cell hHeaderCell = new Cell();
                hHeaderCell.addElement(new Paragraph("视图定义", StylePresets.TABLE_HEADER));
                hHeaderCell.setHeader(true);
                hHeaderCell.setBackgroundColor(headerColor);
                hHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hHeaderCell.setColspan(2);
                table.addCell(hHeaderCell);
                table.endHeaders(); // 表头结束

                Cell cell = new Cell();
                cell.addElement(new Paragraph(viewDefinition, StylePresets.TABLE_CELL));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setColspan(2);
                table.addCell(cell);
            }
        }
    }

    @SuppressWarnings("java:S3776")
    private static void handleIndexDetails(Section indexDesignSection, List<String> dbNames) throws DocumentException {
        List<Map<String, String>> indexDetails = dataService.getIndexDetails(dbNames);
        if (indexDetails.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无");
            indexDesignSection.add(none);
            return;
        }
        String dbName0 = "";
        Section dbSection = null;
        for (Map<String, String> indexDetail : indexDetails) {
            // 数据库名
            String dbName = indexDetail.get(TABLE_SCHEMA).toLowerCase(Locale.getDefault());
            if (Strings.isNullOrEmpty(dbName0) || !dbName.equalsIgnoreCase(dbName0)) {
                dbName0 = dbName;
                dbSection = Sections.newSection(indexDesignSection, dbName + "库");
            }

            if (null != dbSection) {
                String tableName = indexDetail.get(TABLE_NAME).toLowerCase(Locale.getDefault());
                String indexName = indexDetail.get(INDEX_NAME).toLowerCase(Locale.getDefault());
                String indexComment = indexDetail.getOrDefault(INDEX_COMMENT, "无").toLowerCase(Locale.getDefault());
                String indexColumn = indexDetail.get(INDEX_COLUMN).toLowerCase(Locale.getDefault());
                String indexType = indexDetail.get(INDEX_TYPE).toLowerCase(Locale.getDefault());
                String isUnique = indexDetail.get(INDEX_IS_UNIQUE).toLowerCase(Locale.getDefault());
                String isNullable = indexDetail.get(INDEX_IS_NULLABLE).toLowerCase(Locale.getDefault());

                Section indexSection = Sections.newSection(dbSection, "索引" + tableName + "." + indexName);

                // 创建有2列的表格
                int colNumber = 2;
                Table table = new Table(colNumber);
                table.setBorderWidth(1);
                table.setPadding(0);
                table.setSpacing(0);
                table.setWidth(90.0f);
                indexSection.add(table);

                Color headerColor = ColorPresets.TABLE_HEADER_BACKGROUND;
                String[] virtHeaders = { "所属表名", tableName, "所属字段", indexColumn, "索引名称", indexColumn, 
                                         "索引类型", indexType, "说明", indexComment, "是否唯一", isUnique, "是否可空置", isNullable };
                for (int j = 0; j < virtHeaders.length; j++) {
                    Cell cell = new Cell();
                    if (j % 2 == 0) {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_HEADER));
                        cell.setHeader(true);
                        cell.setColspan(1);
                        cell.setWidth("20%");
                        cell.setBackgroundColor(headerColor);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    } else {
                        cell.addElement(new Paragraph(virtHeaders[j], StylePresets.TABLE_CELL));
                        cell.setColspan(1);
                        cell.setWidth("80%");
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    table.addCell(cell);
                }
                table.endHeaders(); // 表头结束
            }
        }
    }
}
