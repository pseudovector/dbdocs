package com.pseudovector.dbdocs.visualizer;

import java.util.List;
import java.util.Objects;

import com.pseudovector.dbdocs.entity.Model;
import com.pseudovector.dbdocs.entity.Table;
import com.pseudovector.dbdocs.service.DataSourceDetailService;
import com.pseudovector.dbdocs.util.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ERModelResolver {

    private ERModelResolver() {}

    private static final DataSourceDetailService dataService = SpringContextHolder.getBean(DataSourceDetailService.class);

    public static Model bySchema(String dbName) {
        Objects.requireNonNull(dbName);
        List<Table> tables = dataService.getTables(dbName);
        return Model.builder()
                    .schemaName(dbName)
                    .tableList(tables)
                    .build();
    }
 
}
