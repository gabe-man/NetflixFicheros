package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase abstracta de la que heredará el resto de DAOs y que especifica la base de datos a la que conectarse
 * @author Gabriel
 *
 */
public abstract class AbstractDAO {
	private final String DB_URL = "jdbc:mysql://localhost/netflix";
	private final String USER = "programacion";
	private final String PASS = "paraprogramacion";
	protected Connection conn;
	protected Statement stmt;
	
	public AbstractDAO() {
		try {
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
			this.stmt = conn.createStatement();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
