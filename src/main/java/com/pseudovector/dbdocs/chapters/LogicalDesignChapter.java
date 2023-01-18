package com.pseudovector.dbdocs.chapters;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.pseudovector.dbdocs.util.Chapters;
import com.pseudovector.dbdocs.util.Paragraphs;
import com.pseudovector.dbdocs.util.Sections;
import com.pseudovector.dbdocs.visualizer.ERDiagramBuilder;
import com.pseudovector.dbdocs.visualizer.VisualizeFormat;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;

public final class LogicalDesignChapter {

    private LogicalDesignChapter() {}

    private static final float IMAGE_MAX_PIXELS_HEIGHT = 720f;

    private static final float IMAGE_MAX_PIXELS_WIDTH = 560f;

    public static void init(Document document, Path dirPath, int chapterIdx, List<String> dbNames) throws DocumentException, IOException {

        Chapter chapter = Chapters.newChapter("逻辑设计", chapterIdx);

        Section logicalDesignSection = Sections.newSection(chapter, "逻辑设计");
        handleLogicalDesign(logicalDesignSection, dirPath, dbNames);

        document.add(chapter);
    }

    private static void handleLogicalDesign(Section logicalDesignSection, Path dirPath, List<String> dbNames) throws DocumentException, IOException {
        if (dbNames.isEmpty()) {
            Paragraph none = Paragraphs.newParagraph("无"); 
            logicalDesignSection.add(none);
            return;
        }
        
        Objects.requireNonNull(dirPath);

        for (String dbName : dbNames) {

            Section dbSection = Sections.newSection(logicalDesignSection, dbName + "库");

            Section subSection = Sections.newSection(dbSection, "E-R图");

            Path filePath = dirPath.resolve("er-diagram-" + dbName + Timestamp.from(Instant.now()).getTime() + ".png");
            ERDiagramBuilder.withSchema(dbName)
                            .withOutputFilePath(filePath)
                            .withFormat(VisualizeFormat.PNG)
                            .execute();

            Paragraph imageWrap = new Paragraph();
            Image erImage = Image.getInstance(filePath.toString());
            erImage.scaleToFit(IMAGE_MAX_PIXELS_WIDTH, IMAGE_MAX_PIXELS_HEIGHT);
            imageWrap.add(erImage);
            imageWrap.setAlignment(Element.ALIGN_CENTER);
            subSection.add(imageWrap);
        }
    }
}


