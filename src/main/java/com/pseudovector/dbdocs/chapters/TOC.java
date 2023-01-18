package com.pseudovector.dbdocs.chapters;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.field.RtfTableOfContents;
import com.pseudovector.dbdocs.presets.FontPresets;

public final class TOC {
 
    private TOC() {}

    public static void init(Document document) throws DocumentException {

        Chapter chapter = new Chapter(0);

        Paragraph title = new Paragraph("目录", FontPresets.PARAGRAPH_FONT_HEADING1);
        title.setSpacingBefore(30f);
        title.setSpacingAfter(20f);
        title.setAlignment(Element.ALIGN_CENTER);
        chapter.add(title);

        Paragraph p = new Paragraph();
        p.add(new RtfTableOfContents("右键点击，选择‘更新域’以更新目录 RIGHT CLICK AND HERE AND SELECT \"UPDATE FIELD\" TO UPDATE."));
        chapter.add(p);

        document.add(chapter);
    }
}
