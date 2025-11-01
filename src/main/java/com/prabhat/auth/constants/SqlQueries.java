package com.prabhat.auth.constants;

public final class SqlQueries {

	
	private SqlQueries() {
        // prevent instantiation
    }

	public static final String TABLE_NAME = "`user`";


    public static final String INSERT_USER =
        "INSERT INTO " + TABLE_NAME + " (username, email, password, role) VALUES (?, ?, ?, ?)";

    public static final String FIND_BY_EMAIL =
        "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

    public static final String FIND_BY_USERNAME =
        "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
}
