package com.pseudovector.dbdocs.fonts;

import lombok.Getter;

@Getter
public enum ChineseFont {

	// 仿宋体
	SIMFANG("SimFang","simfang.ttf"),
	// 黑体
	SIMHEI("SimHei","simhei.ttf"),
	// 楷体
	SIMKAI("SimKai","simkai.ttf"),
	// 宋体/新宋体
	SIMSUM("SimSun","simsun.ttf"),
	// 华文仿宋
	STFANGSO("StFangSo","STFANGSO.TTF"),
    ;
    
    private final String fontName;
    private final String fontFile;

    ChineseFont(String fontName, String fontFile) {
        this.fontName = fontName;
        this.fontFile = fontFile;
    }

}
