package com.pseudovector.dbdocs.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Column implements Serializable {

    private String name;

	private String type;

	private boolean primaryKey;

	private boolean unique;

	private boolean nullable;

	private Table foreignKeyTable;

	private Column foreignKeyColumn;

	private String comment;
}
