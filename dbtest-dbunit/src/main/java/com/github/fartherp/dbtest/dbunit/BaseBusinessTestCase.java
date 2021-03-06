/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.dbtest.dbunit;

import org.dbunit.DataSourceDatabaseTester;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/2/4
 */
@TestExecutionListeners({ DBUnitExecutionListener.class, TransactionalTestExecutionListener.class })
public abstract class BaseBusinessTestCase extends AbstractTestNGSpringContextTests {
    protected DataSourceDatabaseTester dataSourceDatabaseTester;

    /**
     * Count the rows in the given table.
     *
     * @param tableName
     *            table name to count rows in
     * @return the number of rows in the table
     */
    protected int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(getJdbcTemplate(), tableName);
    }

    /**
     * Convenience method for deleting all rows from the specified tables. Use
     * with caution outside of a transaction!
     *
     * @param names
     *            the names of the tables from which to delete
     * @return the total number of rows deleted from all specified tables
     */
    protected int deleteFromTables(String... names) {
        return JdbcTestUtils.deleteFromTables(getJdbcTemplate(), names);
    }

    public void beforeForDBUnit(String fileType, String[] tableNames) throws Exception {
        try {
            BaseTestCaseDelegate testCaseDelegate = createBaseTestCaseDelegate(fileType, this);
            testCaseDelegate.beforeForDBUnit(tableNames);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
    }

    public void afterForDBUnit(String fileType, String[] tableNames) throws Exception {
        try {
            BaseTestCaseDelegate testCaseDelegate = createBaseTestCaseDelegate(fileType, this);
            testCaseDelegate.afterForDBUnit(tableNames);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
    }

    protected abstract DataSource getDataSource();

    protected abstract JdbcTemplate getJdbcTemplate();

    public abstract String getDbunitDir();

    public abstract String getDbunitFile();

    private BaseTestCaseDelegate createBaseTestCaseDelegate(String fileType, BaseBusinessTestCase testCase) {
        return FileTypeEnum.getFileTypeEnum(fileType).function.apply(testCase);
    }
}
