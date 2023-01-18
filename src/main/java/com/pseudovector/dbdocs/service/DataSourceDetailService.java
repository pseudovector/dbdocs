package com.pseudovector.dbdocs.service;

import com.pseudovector.dbdocs.dao.DataSourceMapper;
import com.pseudovector.dbdocs.chapters.CoverPage;
import com.pseudovector.dbdocs.chapters.ExternalDesignChapter;
import com.pseudovector.dbdocs.chapters.IntroChapter;
import com.pseudovector.dbdocs.chapters.LogicalDesignChapter;
import com.pseudovector.dbdocs.chapters.PhysicalDesignChapter;
import com.pseudovector.dbdocs.chapters.TOC;
import com.pseudovector.dbdocs.entity.Table;
import com.pseudovector.dbdocs.presets.FontPresets;
import com.pseudovector.dbdocs.presets.StylePresets;
import com.pseudovector.dbdocs.util.StylePresetResolver;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.document.RtfDocumentSettings;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.style.RtfParagraphStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：DataSourceDetailService
 * @Author：lv617
 * @Date：2018/9/4 14:22
 * @Version：1.0
 **/
@Service
public class DataSourceDetailService {

    @Autowired
    private DataSourceMapper baseMapper;

    @Value("${configuration.er-diagram-subdir}")
    private String erDiagramSubDir;

    @Value("${configuration.document.company-name}")
    private String companyName;

    public List<Map<String, String>> getDataSourceDetail(String dbName, String tableName) {
        return baseMapper.getDataDetail(dbName, tableName);
    }

    public List<Map<String, String>> getAllDataSourceName(List<String> dbNames) {
        return baseMapper.getAllDataSourceName(dbNames);
    }

    public List<Map<String, String>> getViewDefinitions(List<String> dbNames) {
        return baseMapper.getViewDefinitions(dbNames);
    }

    public List<Map<String, String>> getIndexDetails(List<String> dbNames) {
        return baseMapper.getIndexDetails(dbNames);
    }

    public List<Table> getTables(String dbName) {
        return baseMapper.getTables(dbName);
    }

    public void generateDoc(Path filePath, String title, List<String> dbNames) throws IOException, DocumentException {

        Path dirPath = filePath.getParent();
        if (!Files.isDirectory(dirPath)) {
            Files.createDirectories(dirPath); 
        }

        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        // 创建word文档
        RtfWriter2 writer = RtfWriter2.getInstance(document, new FileOutputStream(filePath.toFile()));
        configWriter(writer);
        document.open();

        int chapterIdx = 0;

        CoverPage.init(document, title, companyName);
        TOC.init(document);

        // configurePageNum(document);

        IntroChapter.init(document, ++chapterIdx);
        ExternalDesignChapter.init(document, ++chapterIdx);
        LogicalDesignChapter.init(document, dirPath.resolve(erDiagramSubDir), ++chapterIdx, dbNames);
        PhysicalDesignChapter.init(document, ++chapterIdx, dbNames);

        document.close();
    }

    private void configWriter(RtfWriter2 writer) {
        RtfDocumentSettings settings = writer.getDocumentSettings();
        settings.setImagePDFConformance(false);
        settings.setAlwaysUseUnicode(true);
        registerStyles(settings);
    }

    private void registerStyles(RtfDocumentSettings settings) {
        for (int i = 0; i <= 5; i++) {
            RtfParagraphStyle style = StylePresetResolver.byDepth(i);
            settings.registerParagraphStyle(style);
        }
        settings.registerParagraphStyle(StylePresets.TABLE_CELL);
        settings.registerParagraphStyle(StylePresets.TABLE_HEADER);
    }

    private void configurePageNum(Document document) throws DocumentException {
        document.setPageCount(0);
        Paragraph footP = new Paragraph("第", FontPresets.FOOT_PAGE_NUMBER);
        footP.add(new RtfPageNumber());
        footP.add(new Chunk("页", FontPresets.FOOT_PAGE_NUMBER));
        RtfHeaderFooter footer = new RtfHeaderFooter(footP);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);
    }
}
