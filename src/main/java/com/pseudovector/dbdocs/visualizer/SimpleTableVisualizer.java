package com.pseudovector.dbdocs.visualizer;

import java.util.Locale;

import com.pseudovector.dbdocs.entity.Table;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleTableVisualizer implements TableVisualizer {

    private Table table;

    @Override
    public String getDotRepresentation() {
        String nodeName = Visualizer.makeDotName(table.getFullName().toLowerCase(Locale.getDefault()));
        StringBuilder builder = new StringBuilder(nodeName);
        builder.append("[label=<");
        builder.append("<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\" PORT=\"p0\">\n");
        builder.append("<TR><TD>");
        builder.append(table.getName());
        builder.append("</TD></TR></TABLE>");
        builder.append(">];\n");
        return builder.toString();
    }

    @Override
    public String getPlantRepresentation() {
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

}
