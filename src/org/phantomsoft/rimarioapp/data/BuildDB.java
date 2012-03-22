package org.phantomsoft.rimarioapp.data;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class BuildDB implements DictionaryTableColumns {

    static final String DATABASE_NAME = "dict_it.db";
    static final int DATABASE_VERSION = 1;

    public static void main(String[] args) throws Exception {

	// register the driver

	String sMakeTable = "create table " + TABLE_NAME + " (" +
		ID + " integer primary key autoincrement, " +
		WORD + " text not null, " +
		REVERSED_WORD + " text not null, " +
		LAST_3_CHARS + " text not null, " +
		LAST_2_CHARS + " text not null, " +
		LAST_CHAR + " text not null " + ");";

	String sDriverName = "org.sqlite.JDBC";
	Class.forName(sDriverName);

	// now we set up a set of fairly basic string variables to use in the
	// body of the code proper
	String sJdbc = "jdbc:sqlite";
	String sDbUrl = sJdbc + ":" + DATABASE_NAME;

	// which will produce a legitimate Url for SqlLite JDBC :
	int iTimeout = 30;

	// create a database connection
	Connection conn = DriverManager.getConnection(sDbUrl);
	conn.setAutoCommit(false);

	try {
	    Statement stmt = conn.createStatement();
	    try {
		stmt.setQueryTimeout(iTimeout);
		stmt.executeUpdate(sMakeTable);

		PreparedStatement prep = conn.prepareStatement(
			"insert into " + TABLE_NAME + " (" + WORD + ","
				+ REVERSED_WORD + ","
				+ LAST_3_CHARS + ","
				+ LAST_2_CHARS + ","
				+ LAST_CHAR + ") " +
				" values (?,?,?,?,?);");

		FileInputStream is =
			new FileInputStream(".\\res\\raw\\wordlist.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = br.readLine();
		while (line != null) {

		    prep.setString(1, line);
		    prep.setString(2, new StringBuffer(line).reverse()
			    .toString());
		    prep.setString(3, getLastChars(line, 3));
		    prep.setString(4, getLastChars(line, 2));
		    prep.setString(5, getLastChars(line, 1));

		    prep.executeUpdate();

		    line = br.readLine();

		}
		conn.commit();

		br.close();
		isr.close();
		is.close();

	    } finally {
		try {
		    stmt.close();
		} catch (Exception ignore) {
		}
	    }
	} finally {
	    try {
		conn.close();
	    } catch (Exception ignore) {
	    }
	}

    }

    private static String getLastChars(String input, int n) {

	if (input.length() <= n) {
	    return input;
	} else {
	    return input.substring(input.length() - n);
	}

    }

}
