package com.pseudovector.dbdocs.visualizer;

public interface TableVisualizer {

    /**
     * Create a node description for this table
     *
     * @return the dot representation of a table
     */
    String getDotRepresentation();

    /**
     * Create a node description for this table
     *
     * @return the plant representation of a table
     */
    String getPlantRepresentation();

}