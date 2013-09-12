package org.schemaanalyst.sqlparser;

import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TStatementList;

import java.io.File;

import org.schemaanalyst.dbms.DBMS;

/**
 * <p>
 * Parses an SQL file to the representation used by The General SQL Parser.
 * </p>
 */
public class Parser {

    private TGSqlParser sqlParser;

    public Parser(DBMS dbms) {
        sqlParser = new TGSqlParser(VendorResolver.resolve(dbms));
    }

    public TStatementList parse(File file) {
        sqlParser.setSqlfilename(file.getPath());
        return performParse();
    }

    public TStatementList parse(String sql) {
        sqlParser.sqltext = sql;
        return performParse();
    }

    private TStatementList performParse() {
        int result = sqlParser.parse();
        if (result != 0) {
            throw new SQLParseException(sqlParser.getErrormessage());
        }
        return sqlParser.sqlstatements;
    }
}
