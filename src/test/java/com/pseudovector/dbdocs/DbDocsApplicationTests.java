package com.pseudovector.dbdocs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Lists;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.pseudovector.dbdocs.entity.Table;
import com.pseudovector.dbdocs.presets.FontPresets;
import com.pseudovector.dbdocs.service.DataSourceDetailService;
import com.pseudovector.dbdocs.util.SqlFormatter;
import com.pseudovector.dbdocs.visualizer.ERDiagramBuilder;
import com.pseudovector.dbdocs.visualizer.VisualizeFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class DbDocsApplicationTests {

    @Autowired
    private DataSourceDetailService dataSourceDetailService;

    private static final String TABLE_SCHEMA = "table_schema";

    private static final String TABLE_NAME = "table_name";

    private static final String TABLE_COMMENT = "table_comment";

    private static final String INDEX_NAME = "index_name";

    private static final String VIEW_DEFINITION = "view_definition";

    private static final String OUTPUT_DIR = "output";

    @Test
    void contextLoads() {
        List<String> dbNames = Lists.newArrayList("schema1");
        List<Map<String, String>> tableSchemas = this.dataSourceDetailService.getAllDataSourceName(dbNames);
        List<Map<String, String>> viewDefs = this.dataSourceDetailService.getViewDefinitions(dbNames);
        List<Table> tables = this.dataSourceDetailService.getTables(dbNames.get(0));
        assertNotNull(tableSchemas);
        assertNotNull(viewDefs);
        assertNotNull(tables);
    }

    @Test
    void testViewSqlFormat() {
        List<String> dbNames = Lists.newArrayList("schema1");
        List<Map<String, String>> viewDefs = this.dataSourceDetailService.getViewDefinitions(dbNames);
        for (Map<String,String> viewDef : viewDefs) {
            String viewDefinition = viewDef.get(VIEW_DEFINITION); 
            String format = SqlFormatter.prettyPrint(viewDefinition);
            if (log.isInfoEnabled()) {
                log.info("formatted sql: " + format); 
            }
        }
    }

    @Test
    void testGenerateERDiagram() {
        String fileName = "diagram_" + Timestamp.from(Instant.now()).getTime() + ".png";
        assertDoesNotThrow(() -> {
            try {
                Path filePath = Paths.get(getClass().getClassLoader().getResource("").toURI())
                                     .getParent().getParent().resolve(OUTPUT_DIR).resolve("er_diagram").resolve(fileName);
                ERDiagramBuilder.withSchema("schema1")
                                .withOutputFilePath(filePath)
                                .withFormat(VisualizeFormat.PNG)
                                .execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    void fontSytleConvert() {
        Font f = FontPresets.PARAGRAPH_FONT_HEADING1;
        RtfParagraphStyle style = new RtfParagraphStyle("heading 1", f);
        assertNotNull(style);
    }
    
    @Test
    void exportDocument() {
        List<String> dbNames = Lists.newArrayList("schema1", "schema2");
        String fileName = "dbDetail_" + Timestamp.from(Instant.now()).getTime() + ".doc";
        String title = "信息系统\n数据库设计文档";
        assertDoesNotThrow(() -> {
            try {
                Path filePath = Paths.get(getClass().getClassLoader().getResource("").toURI())
                                     .getParent().getParent().resolve(OUTPUT_DIR).resolve(fileName);
                this.dataSourceDetailService.generateDoc(filePath, title, dbNames);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        });
    }
}
