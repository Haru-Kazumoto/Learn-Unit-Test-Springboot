package com.testing.pack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest(classes = TestConnectionDatabase.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestConnectionDatabase {

    @Autowired
    DataSource dataSource;

    Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = dataSource.getConnection();
    }

    @Test
    void shouldConnectedToDatabase() throws SQLException {
        boolean isConnected = connection.isValid(5);

        Assertions.assertTrue(isConnected);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if(connection != null){
            connection.close();
        }
    }
}
