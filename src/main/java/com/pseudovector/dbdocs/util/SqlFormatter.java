package com.pseudovector.dbdocs.util;

import org.apache.openjpa.lib.jdbc.SQLFormatter;

public final class SqlFormatter {

    private SqlFormatter() {}

    private static final ThreadLocal<SQLFormatter> FORMATTER = ThreadLocal.withInitial(SQLFormatter::new);

    private static SQLFormatter getFormatter() {
        return FORMATTER.get();
    }

    private static void clean() {
        FORMATTER.remove();
    }

    public static String prettyPrint(String sql) {
        String result = String.valueOf(getFormatter().prettyPrint(sql));
        clean();
        return result;
    }
}
