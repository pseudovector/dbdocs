package com.pseudovector.dbdocs.visualizer;

import java.util.List;
import java.util.Locale;

import com.pseudovector.dbdocs.entity.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Create a file in dot language 
 * 
 * @author Stefan Kuehnel
 * @see https://github.com/eska-muc/dbvisualizer
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visualizer {

    private List<Table> tables;

    @Builder.Default
    private boolean lrEnabled = false;

    @Builder.Default
	private boolean entitiesOnly = false;

    public void addTable(Table table) {
        this.tables.add(table);
    }

    /**
	 * Generate the .dot file
	 * @return the ER-model as GraphViz dot file
	 */
	public String getDotRepresentation() {
		StringBuilder builder = new StringBuilder();
		builder.append("digraph tables {\n");
		if (lrEnabled) {
			builder.append("  rankdir=LR;\n");
		}
		builder.append("  node [shape=plaintext];\n");
		for (Table table : tables) {
			TableVisualizer tv = getTableVisualizer(table);
			builder.append(tv.getDotRepresentation());
		}
		for (Table table : tables) {
			List<Table> foreignKeyRelations = table.getForeignKeyRelations();
			if (!foreignKeyRelations.isEmpty()) {
				for (Table other : foreignKeyRelations) {
				builder.append(makeDotName(table.getFullName().toLowerCase(Locale.getDefault())));
				builder.append(":p0");
				builder.append(" -> ");
				builder.append(makeDotName(other.getFullName().toLowerCase(Locale.getDefault())));
				builder.append(":p0");
				builder.append(" [arrowtail=crow;dir=back];\n");
				}
			}
		}
		builder.append("}\n");		
		return builder.toString();
	}

	/**
	 * Get Output for Plant IE (ER) Diagrams
	 *
	 * @return a string describing the database in PlantUML notation
	 */
	public String getPlantRepresentation() {
        StringBuilder builder = new StringBuilder();
		builder.append("@startuml\n");
		builder.append("'hide the spot\n");
		builder.append("hide circle\n");
		builder.append("' comment\n");
		builder.append("skinparam linetype ortho\n");
		for (Table table : tables) {
			TableVisualizer tv = getTableVisualizer(table);
			builder.append(tv.getPlantRepresentation());
			builder.append("\n");
		}
		builder.append("\n");
        for (Table table : tables) {
			List<Table> foreignKeyRelations = table.getForeignKeyRelations();
			if (!foreignKeyRelations.isEmpty()) {
				for (Table other : foreignKeyRelations) {
					builder.append(Visualizer.makeDotName(table.getName()).toLowerCase(Locale.getDefault()));
					builder.append(" }|--|| ");
					builder.append(Visualizer.makeDotName(other.getName()).toLowerCase(Locale.getDefault()));
					builder.append("\n");
				}
			}
		}
        builder.append("@enduml\n");
		return builder.toString();
    }

    protected static String makeDotName(String in) {
		return in.replaceAll("([\\.\\$])", "_");
	}

    private TableVisualizer getTableVisualizer(Table table) {
		if (this.entitiesOnly) {
			return new SimpleTableVisualizer(table);
		}
		return new DetailedTableVisualizer(table);
    }

}
