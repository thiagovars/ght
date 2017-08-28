package com.ght.conexion;
import java.sql.*;

public class ConnDAO {
	private final String base   = "bra_ght";
	private final String usr    = "root";
	private final String passw  = "";
	private final String host   = "jdbc:mysql://localhost/"+base;
	private Connection connect  = null;
	private Statement statement = null;
	private ResultSet result    = null;
	
	public ConnDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(host, usr, passw);
			statement = connect.createStatement();
		} catch (Exception e) {
			System.out.println("No fue posible encontrar el drive MySQL! "+e.getMessage());
		}
	}
	
	public ResultSet buscar(String query) {
		try {
			PreparedStatement psm = connect.prepareStatement(query);
			ResultSet result = psm.executeQuery();
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public boolean executar(String query) {
		try {
			PreparedStatement psm = connect.prepareStatement(query);
			psm.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean bajar(String query) {
		try	{
			PreparedStatement psm = connect.prepareStatement(query);
			psm.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
