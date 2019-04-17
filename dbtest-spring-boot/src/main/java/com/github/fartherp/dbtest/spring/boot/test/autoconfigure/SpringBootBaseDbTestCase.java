/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.dbtest.spring.boot.test.autoconfigure;

import com.github.fartherp.dbtest.dbunit.BaseBusinessTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2019/4/17
 */
@SpringBootTest
public class SpringBootBaseDbTestCase extends BaseBusinessTestCase {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public String getDbunitDir() {
        return "data";
    }

    public String getDbunitFile() {
        return "sample_data_cust.xml";
    }
}
