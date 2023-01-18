package com.pseudovector.dbdocs.fonts;

import java.util.Map;
import java.util.Objects;

import java.awt.Color;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.collect.Maps;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

public final class ChineseFontProvider {
    private ChineseFontProvider() {}

    private static final Map<ChineseFont, BaseFont> fonts = Maps.newHashMap();

    private static final String FONT_FILE_DIR = "fonts";

    static {
        for (ChineseFont fontInfo : ChineseFont.values()) {
            fonts.put(fontInfo, ChineseFontProvider.createBaseFont(fontInfo.getFontFile()));
        }
    }

    public static BaseFont createBaseFont(String fileName) {
        try {
            Objects.requireNonNull(fileName, "Font file name cannot be null");
            URI dir = ChineseFontProvider.class.getClassLoader().getResource(FONT_FILE_DIR).toURI();
            Path resPath = Paths.get(dir).resolve(fileName);
            return BaseFont.createFont(resPath.toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BaseFont getBaseFont(final ChineseFont font) {
        Objects.requireNonNull(font, "font can't be null");
        return fonts.get(font);
    }

    public static Font get(final ChineseFont font, final Float fontSize, final Integer style, final Color color) {
        Objects.requireNonNull(font, "font can't be null");
        Objects.requireNonNull(fontSize, "fontSize can't be null");
        Objects.requireNonNull(style, "style can't be null");
        BaseFont baseFont = ChineseFontProvider.getBaseFont(font);
        if (null != baseFont) {
            return new Font(baseFont, fontSize, style, color);
        }
        return null;
    }

    public static Font get(final String fontName, final Float fontSize, final Integer style, final Color color) {
        return ChineseFontProvider.get(ChineseFont.valueOf(fontName), fontSize, style, color);
    }
}
