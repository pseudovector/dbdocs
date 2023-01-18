package com.pseudovector.dbdocs.presets;

import com.pseudovector.dbdocs.fonts.ChineseFont;
import com.pseudovector.dbdocs.fonts.ChineseFontProvider;
import com.lowagie.text.Font;

public final class FontPresets {

    private FontPresets() {}

    public static final Font DOC_TITLE = ChineseFontProvider.get(ChineseFont.SIMSUM, 26f, Font.BOLD, ColorPresets.BLACK);

    public static final Font COVER_FOOT = ChineseFontProvider.get(ChineseFont.SIMSUM, 16f, Font.NORMAL, ColorPresets.BLACK);

    public static final Font PARAGRAPH_FONT_HEADING1 = ChineseFontProvider.get(ChineseFont.SIMSUM, 15f, Font.BOLD, ColorPresets.BLACK);

    public static final Font PARAGRAPH_FONT_HEADING2 = ChineseFontProvider.get(ChineseFont.SIMSUM, 14f, Font.BOLD, ColorPresets.BLACK);

    public static final Font PARAGRAPH_FONT_HEADING3 = ChineseFontProvider.get(ChineseFont.SIMSUM, 14f, Font.BOLD, ColorPresets.BLACK);

    public static final Font PARAGRAPH_FONT_HEADING4 = ChineseFontProvider.get(ChineseFont.SIMSUM, 12f, Font.BOLD, ColorPresets.BLACK);

    public static final Font PARAGRAPH_FONT_HEADING5 = ChineseFontProvider.get(ChineseFont.SIMSUM, 12f, Font.BOLD, ColorPresets.BLACK);

    public static final Font MAIN_TEXT = ChineseFontProvider.get(ChineseFont.SIMSUM, 11f, Font.NORMAL, ColorPresets.BLACK);

    public static final Font TABLE_HEADER_CELL = ChineseFontProvider.get(ChineseFont.SIMSUM, 11f, Font.BOLD, ColorPresets.BLACK);

    public static final Font TABLE_CONTENT_CELL = ChineseFontProvider.get(ChineseFont.SIMSUM, 11f, Font.NORMAL, ColorPresets.BLACK);

    public static final Font FOOT_PAGE_NUMBER = ChineseFontProvider.get(ChineseFont.SIMSUM, 9f, Font.NORMAL, ColorPresets.BLACK);

  
}
