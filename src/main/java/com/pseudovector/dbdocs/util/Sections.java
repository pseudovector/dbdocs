package com.pseudovector.dbdocs.util;

import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;

public final class Sections {
    
    private Sections() {}

    public static Section newSection(Section parent, String title) {
        Paragraph titleP = Paragraphs.newParagraph(title, StylePresetResolver.byDepth(parent.getDepth() + 1));
        Section section = parent.addSection(titleP);
        section.add(new Phrase());
        return section;
    }

}
