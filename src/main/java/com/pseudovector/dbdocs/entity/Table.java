package com.pseudovector.dbdocs.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table implements Serializable {

	private String catalog;

	private String schema;

	private String name;

	private String comment;

	private List<Column> columns; 

	@Builder.Default
    private List<Table> foreignKeyRelations = Lists.newArrayList();

	public Table(String catalog, String schema, String tableName) {
		this.name = tableName;
		this.catalog = catalog;
		this.schema = schema;
	}

    public void setColumns(List<Column> columns) {
		this.columns = columns;
		updateForeignKeyRelations();
	}

    public void updateForeignKeyRelations() {
		foreignKeyRelations.clear();
		for (Column column : columns) {
			Table t = column.getForeignKeyTable();
			if (t != null) {
				foreignKeyRelations.add(t);
			}
		}
	}

	public String getFullName() {
		return this.formatFullName(this.catalog, this.schema, this.name);
	}

	private String formatFullName(String catalog, String schema, String tableName) {
		StringBuilder builder = new StringBuilder();
		if (!Strings.isNullOrEmpty(catalog)) {
			builder.append(catalog).append(".");
		}
		if (!Strings.isNullOrEmpty(schema)) {
			builder.append(schema).append(".");
		}
		builder.append(Objects.requireNonNull(tableName));
		return builder.toString();
	}
    
}
