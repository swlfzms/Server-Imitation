package com.example.test;

import java.sql.Connection;

import com.example.database.ConnectionPool;

public class Test {

	public static void main(String[] args) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = connectionPool.getConnection();
		connectionPool.release(connection);
	}
}
