package com.pseudovector.dbdocs.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pseudovector.dbdocs.entity.Table;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataSourceMapper {
    /**
     *  描述：根据表名称获取表的详细信息
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select("SHOW FULL FIELDS FROM ${dbName}.${tableName}")
    List<Map<String, String>> getDataDetail(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     *  描述：根据数据库名称获取数据库中表的名称和注释
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:55
     */
    @Select({"<script> ",
             "SELECT",
             "  q.table_schema, ",
             "  q.table_name, ",
             "  q.table_comment, ",
             "  GROUP_CONCAT(q.index_name SEPARATOR ',') AS index_name",
             "FROM ( ",
             "SELECT DISTINCT",
             "  t.table_schema AS table_schema, ",
             "  t.table_name AS table_name, ",
             "  t.table_comment AS table_comment, ",
             "  p.index_name AS index_name ",
             "FROM information_schema.tables t ",
             "LEFT JOIN information_schema.statistics p ON (t.table_schema = p.table_schema AND t.table_name = p.table_name) ",
             "WHERE 1=1 ",
             "AND UPPER(t.table_type) LIKE '%TABLE%'",
             "AND t.table_schema IN ", 
             "  <foreach item='dbName' collection='dbNames' open='(' separator=', ' close=')' > ",
             "     #{dbName} ",
             "  </foreach> ",
             "ORDER BY t.table_schema, t.table_name ",
             ") q",
             "GROUP BY q.table_schema, q.table_name, q.table_comment ",
             "ORDER BY q.table_schema, q.table_name ",
             "</script>"})
    List<Map<String, String>> getAllDataSourceName(@Param("dbNames") List<String> dbNames);

    @Select({"<script> ",
             "SELECT ",
             "  v.table_schema, ",
             "  v.table_name, ",
             "  v.view_definition, ",
             "  v.is_updatable ",
             "FROM information_schema.views v ",
             "WHERE v.table_schema IN ",
             "  <foreach item='dbName' collection='dbNames' open='(' separator=', ' close=')' > ",
             "     #{dbName} ",
             "  </foreach> ",
             "</script>"})
    List<Map<String, String>> getViewDefinitions(@Param("dbNames") List<String> dbNames);

    @Select({"<script> ",
             "SELECT ",
             "  t.table_schema AS table_schema, ",
             "  t.table_name AS table_name, ",
             "  t.index_name AS index_name, ",
             "  t.index_schema AS index_schema, ",
             "  t.index_type AS index_type, ",
             "  t.index_comment AS index_comment, ",
             "  t.column_name AS column_name, ",
             "  CASE WHEN t.non_unique = 1 THEN 'NO' ELSE 'YES' END AS is_unique, ",
             "  CASE WHEN UPPER(t.nullable) = 'YES' THEN 'YES' ELSE 'NO' END AS is_nullable ",
             "FROM information_schema.statistics t ",
             "WHERE 1=1 ",
             "  AND t.table_schema IN ",
             "  <foreach item='dbName' collection='dbNames' open='(' separator=', ' close=')' > ",
             "     #{dbName} ",
             "  </foreach> ",
             "ORDER BY t.table_schema, t.table_name, t.column_name ",
             "</script>"})
    List<Map<String, String>> getIndexDetails(@Param("dbNames") List<String> dbNames);

    List<Table> getTables(@Param("dbName") String dbName);
}
