package com.pseudovector.dbdocs.util;

import com.lowagie.text.Chapter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.pseudovector.dbdocs.presets.StylePresets;

public final class Chapters {
    
    private Chapters() {}

    public static Chapter newChapter(String title, int chapterIdx) {
        Paragraph titleP = Paragraphs.newParagraph(title, StylePresets.HEADING1);
        Chapter chapter = new Chapter(titleP, chapterIdx);
        chapter.add(new Phrase());
        return chapter;
    }
}

