package com.pseudovector.dbdocs.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model implements Serializable {

    private String databaseType;

    private String databaseName;

    private String catalogName;

    private String schemaName;

    private List<Table> tableList; 
}
