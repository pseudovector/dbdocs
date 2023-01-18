package com.pseudovector.dbdocs.chapters;

import com.pseudovector.dbdocs.util.Chapters;
import com.pseudovector.dbdocs.util.Sections;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Section;

public final class ExternalDesignChapter {

    private ExternalDesignChapter() {}

    public static void init(Document document, int chapterIdx) throws DocumentException {

        Chapter chapter = Chapters.newChapter("外部设计", chapterIdx);

        Section envSection = Sections.newSection(chapter, "硬件环境");

        Section softwareSection = Sections.newSection(chapter, "软件环境");

        document.add(chapter);
    }
  
}
