package com.pseudovector.dbdocs.chapters;

import java.util.Date;

import com.pseudovector.dbdocs.presets.FontPresets;
import com.pseudovector.dbdocs.util.DateTimeUtil;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ElementTags;
import com.lowagie.text.Paragraph;

public final class CoverPage {
  
    private CoverPage() {}

    public static void init(Document document, String title, String company) throws DocumentException {

        Chapter chapter = new Chapter(0);
        Paragraph emptyLinesBefore = new Paragraph("");
        emptyLinesBefore.setSpacingBefore(150f);
        chapter.add(emptyLinesBefore);

        Paragraph titleParagraph = new Paragraph(title, FontPresets.DOC_TITLE);
        titleParagraph.setAlignment(ElementTags.ALIGN_CENTER);
        chapter.add(titleParagraph);

        Paragraph footParagraph1 = new Paragraph(company, FontPresets.COVER_FOOT);
        footParagraph1.setSpacingBefore(400f);
        footParagraph1.setAlignment(ElementTags.ALIGN_CENTER);
        chapter.add(footParagraph1);

        Paragraph footParagraph2 = new Paragraph(DateTimeUtil.formatChnDate(new Date()), FontPresets.COVER_FOOT);
        footParagraph2.setSpacingBefore(10f);
        footParagraph2.setAlignment(ElementTags.ALIGN_CENTER);
        chapter.add(footParagraph2);

        document.add(chapter);
    }
}
