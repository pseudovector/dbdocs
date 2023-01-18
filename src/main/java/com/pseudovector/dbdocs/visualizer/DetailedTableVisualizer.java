package com.pseudovector.dbdocs.visualizer;

import java.util.Locale;

import com.pseudovector.dbdocs.entity.Column;
import com.pseudovector.dbdocs.entity.Table;

import lombok.AllArgsConstructor;

/**
 * @author Stefan Kuehnel
 * @see https://github.com/eska-muc/dbvisualizer
 */
@AllArgsConstructor
public class DetailedTableVisualizer implements TableVisualizer{

    private Table table;

    @Override
    @SuppressWarnings("java:S1192")
    public String getDotRepresentation() {
        String nodeName = Visualizer.makeDotName(table.getFullName().toLowerCase(Locale.getDefault()));
        StringBuilder builder = new StringBuilder(nodeName);
        builder.append(" [");
        builder.append("label=<");
        builder.append("<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\" PORT=\"p0\">\n");
        builder.append("<TR>");
        builder.append("<TD COLSPAN=\"4\">");
        builder.append(table.getFullName().toUpperCase(Locale.getDefault()));
        builder.append("</TD>");
        builder.append("</TR>\n");

        for (Column column : table.getColumns()) {
            builder.append("<TR>");
            builder.append("<TD>");
            if (column.isPrimaryKey()) {
                builder.append("PK");
            } else if (column.getForeignKeyTable() != null) {
                builder.append("FK");
            } else {
                builder.append("-");
            }
            builder.append("</TD><TD>");
            builder.append(column.getName());
            builder.append("</TD><TD>");
            builder.append(column.getType());
            builder.append("</TD><TD>");
            builder.append(getConstraints(column));
            builder.append("</TD></TR>\n");
        }
        builder.append("</TABLE>>];\n");

        return builder.toString();
    }

    @Override
    public String getPlantRepresentation() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("entity \"%s\" as %s {%n", table.getFullName(), Visualizer.makeDotName(table.getFullName().toLowerCase(Locale.getDefault()))));
        int pkCount = 0;
        for (Column column : table.getColumns()) {
            if (column.isPrimaryKey()) {
                pkCount++;
                builder.append(String.format("  *%s : %s%n", column.getName(), column.getType()));
            }
        }
        if (pkCount > 0) {
            builder.append("  --\n");
        }
        for (Column column : table.getColumns()) {
            if (!column.isPrimaryKey()) {
                builder.append("  ");
                if (!column.isNullable()) {
                    builder.append("*");
                }
                builder.append(String.format("%s : %s", column.getName(), column.getType()));
                if (column.getForeignKeyTable() != null) {
                    builder.append(" <<FK>>");
                }
                builder.append("\n");
            }
        }
        builder.append("}\n");
        return builder.toString();
    }

    private String getConstraints(Column column) {
        StringBuilder constraints = new StringBuilder();

        if (!column.isNullable()) {
            constraints.append("not null");
        }
        if (column.isUnique()) {
            if (!column.isNullable()) {
                constraints.append(", ");
            }
            constraints.append("unique");
        }
        return constraints.toString();
    }

}
