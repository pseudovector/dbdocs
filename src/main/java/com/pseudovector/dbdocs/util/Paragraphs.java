package com.pseudovector.dbdocs.util;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.pseudovector.dbdocs.presets.StylePresets;

public final class Paragraphs {

    public static final int DEFAULT_SPACE_BEFORE = 6;

    public static final int DEFAULT_SPACE_AFTER = 6;

    private Paragraphs() {}

    public static Paragraph newParagraph(String content) {
        return Paragraphs.newParagraph(content, StylePresets.NORMAL);
    }

    public static Paragraph newParagraph(String content, Font font) {
        return Paragraphs.newParagraph(content, font, DEFAULT_SPACE_BEFORE, DEFAULT_SPACE_AFTER);
    }

    public static Paragraph newParagraph(String content, Font font, int spaceBefore, int spaceAfter) {
        Paragraph p = new Paragraph(content, font);
        p.setSpacingBefore(spaceBefore);
        p.setSpacingAfter(spaceAfter);
        return p;
    }
    
}

