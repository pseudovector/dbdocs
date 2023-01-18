package com.pseudovector.dbdocs.presets;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.style.RtfParagraphStyle;

public final class StylePresets {
    
    private StylePresets() {}

    public static final int NORMAL_STYLE_FIRST_LINE_INDENT = 30;

    public static final RtfParagraphStyle NORMAL = StylePresets.buildNormal("Normal");

    public static final RtfParagraphStyle HEADING1 = StylePresets.buildHeader("Heading 1", FontPresets.PARAGRAPH_FONT_HEADING1); 

    public static final RtfParagraphStyle HEADING2 = StylePresets.buildHeader("Heading 2", FontPresets.PARAGRAPH_FONT_HEADING2); 

    public static final RtfParagraphStyle HEADING3 = StylePresets.buildHeader("Heading 3", FontPresets.PARAGRAPH_FONT_HEADING3); 

    public static final RtfParagraphStyle HEADING4 = StylePresets.buildHeader("Heading 4", FontPresets.PARAGRAPH_FONT_HEADING4);
    
    public static final RtfParagraphStyle HEADING5 = StylePresets.buildHeader("Heading 5", FontPresets.PARAGRAPH_FONT_HEADING5);

    public static final RtfParagraphStyle TABLE_HEADER = StylePresets.buildTableCell("Table Header", FontPresets.TABLE_HEADER_CELL);

    public static final RtfParagraphStyle TABLE_CELL = StylePresets.buildTableCell("Table Cell", FontPresets.TABLE_CONTENT_CELL);


    private static RtfParagraphStyle buildHeader(String styleName, Font font) {
        RtfParagraphStyle style = new RtfParagraphStyle(styleName, font);
        style.setSpacingBefore(6);
        style.setSpacingAfter(6);
        style.setAlignment(Element.ALIGN_LEFT);
        return style;
    }

    private static RtfParagraphStyle buildNormal(String styleName) {
        RtfParagraphStyle style = new RtfParagraphStyle(styleName, FontPresets.MAIN_TEXT);
        style.setSpacingBefore(6);
        style.setSpacingAfter(6);
        style.setAlignment(Element.ALIGN_LEFT);
        style.setFirstLineIndent(NORMAL_STYLE_FIRST_LINE_INDENT);
        return style;
    }

    private static RtfParagraphStyle buildTableCell(String styleName, Font font) {
        RtfParagraphStyle style = new RtfParagraphStyle(styleName, font);
        style.setSpacingBefore(6);
        style.setSpacingAfter(6);
        style.setAlignment(Element.ALIGN_CENTER);
        return style;
    }

}

