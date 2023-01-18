package com.pseudovector.dbdocs.util;

import com.pseudovector.dbdocs.presets.FontPresets;
import com.lowagie.text.Font;

public final class FontPresetResolver {

    private FontPresetResolver() {}

    public static Font byDepth(int depth) {
        Font font = new Font();
        switch (depth) {
            case 1:
                font = FontPresets.PARAGRAPH_FONT_HEADING1;
                break;
            case 2:
                font = FontPresets.PARAGRAPH_FONT_HEADING2;
                break;
            case 3:
                font = FontPresets.PARAGRAPH_FONT_HEADING3;
                break;
            case 4:
                font = FontPresets.PARAGRAPH_FONT_HEADING4;
                break;
            case 5:
                font = FontPresets.PARAGRAPH_FONT_HEADING5;
                break;
            default:
                break;
        }
        return font;
    }
     
}

