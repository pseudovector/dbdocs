package com.pseudovector.dbdocs.util;

import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.pseudovector.dbdocs.presets.StylePresets;

public final class StylePresetResolver {
    private StylePresetResolver() {}

    public static RtfParagraphStyle byDepth(int depth) {
        RtfParagraphStyle font = null;
        switch (depth) {
            case 1:
                font = StylePresets.HEADING1;
                break;
            case 2:
                font = StylePresets.HEADING2;
                break;
            case 3:
                font = StylePresets.HEADING3;
                break;
            case 4:
                font = StylePresets.HEADING4;
                break;
            case 5:
                font = StylePresets.HEADING5;
                break;
            default:
                font = StylePresets.NORMAL;
                break;
        }
        return font;
    } 
}
