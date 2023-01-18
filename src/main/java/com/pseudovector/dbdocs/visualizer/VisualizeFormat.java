package com.pseudovector.dbdocs.visualizer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Stefan Kuehnel
 * @see https://github.com/eska-muc/dbvisualizer
 */
@Getter
@AllArgsConstructor
public enum VisualizeFormat {

    DOT("DOT", ".dot", "The Language of GraphViz; see http://www.graphviz.org", true),
    PLANT("PLANT", ".txt", "PlantUML ER Diagrams; see https://plantuml.com/ie-diagram", true),
    PNG("PNG", ".png", "Portable Network Graphics; see http://libpng.org/pub/png", true),
    SVG("SVG", ".svg", "Scalable Vector Graphics; see https://www.w3.org/TR/2003/REC-SVG11-20030114/", true),
    PDF("PDF", ".pdf", "Portable Document Format; see https://en.wikipedia.org/wiki/Portable_Document_Format", true),
    VISJS("VISJS", ".html", "JavaScript Visualization Framework; see: https://github.com/almende/vis", false);

    private String name;
    private String extension;
    private String description;
    private boolean implemented;

}
