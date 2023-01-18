# 数据库设计文档生成工具

- Forked from https://github.com/heartsuit/db2word
- ER-Diagram generator based on https://github.com/eska-muc/dbvisualizer


## 生成word文档

- 启动Web应用，访问`http://localhost:8080/myTest/getDbDetail?dbName=your-db-name`；

- 生成地址：D:/data/dbDetail.doc（原作者是硬编码在代码中：DataSourceDetailServiceImpl.toWord方法）

## 参考SQL

* 查询所有表名

```sql
SELECT table_name, table_comment FROM information_schema.TABLES WHERE table_schema='zaservice';
```

* 查询每个表的字段信息

```sql
SELECT
        COLUMN_NAME 字段名称,
        COLUMN_TYPE 字段类型,
        COLUMN_DEFAULT 默认值,
        CHARACTER_MAXIMUM_LENGTH AS 最大长度,
        (CASE WHEN is_nullable = 'NO' THEN
                        '否' ELSE
                        '是' END
        ) AS 是否可空,
        (CASE WHEN column_key = 'PRI' THEN
                        '是' ELSE
                        '否' END
        ) AS 是否主键,
        EXTRA 其他,
        COLUMN_COMMENT 字段说明
FROM
        INFORMATION_SCHEMA.COLUMNS
WHERE
 table_schema='zaservice' AND
        table_name = 'sys_log'
```

## Reference

- [https://github.com/BeliveYourSelf/lv617DbTest](https://github.com/BeliveYourSelf/lv617DbTest)

- [https://github.com/heartsuit/db2word](https://github.com/heartsuit/db2word)

- [https://github.com/eska-muc/dbvisualizer](https://github.com/eska-muc/dbvisualizer)
