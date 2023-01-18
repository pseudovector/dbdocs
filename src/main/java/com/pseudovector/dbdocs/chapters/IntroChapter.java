package com.pseudovector.dbdocs.chapters;

import com.pseudovector.dbdocs.util.Chapters;
import com.pseudovector.dbdocs.util.Sections;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Section;

public final class IntroChapter {

    private IntroChapter() {}

    public static void init(Document document, int chapterIdx) throws DocumentException {

        Chapter chapter = Chapters.newChapter("引言", chapterIdx);

        Section objSection = Sections.newSection(chapter, "文档目的");

        Section backgroundSection = Sections.newSection(chapter, "背景");

        Section scopeSection = Sections.newSection(chapter, "文档范围");

        Section referenceSection = Sections.newSection(chapter, "参考文档");

        document.add(chapter);
    }
}
