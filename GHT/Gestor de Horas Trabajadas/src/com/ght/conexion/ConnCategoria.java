package com.ght.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnCategoria {

	private ConnDAO conn;
	
	public ConnCategoria() {
		this.conn = new ConnDAO();
	}
	
	public ResultSet getNombresCategorias() {
		String query = "SELECT nombre FROM categoria";
		try {
			ResultSet result = conn.buscar(query);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public int getCodigoByName(String nombre) {
		int codigo = 0;
		String query = "SELECT codigo FROM categoria WHERE nombre LIKE '%"+nombre+"%'";
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getInt("codigo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return codigo;
	}
	
	public String getNameByCodigo(String codigo) {
		String query = "SELECT nombre FROM categoria WHERE codigo = "+codigo;
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getString("nombre");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	public String getTipoByCodigo(String codigo) {
		String query = "SELECT tipo FROM categoria";
		if(!codigo.equals("")) {
			 query += " WHERE codigo = "+codigo;
		}
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getString("tipo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	public double getValorHoraByCodigo(String codigo) {
		String query = "SELECT valorHora FROM categoria WHERE codigo = "+codigo;
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return Double.parseDouble(result.getString("valorHora"));
		} catch (Exception e) {
			System.out.println("No fue posible levantar el Valor de la Hora de la categoria del usuario: "+e.getMessage());
		}
		return 0;
	}
	
	public ResultSet getTipoCategorias() {
		String query = "SELECT tipo FROM categoria";
		try {
			ResultSet result = conn.buscar(query);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Object[][] getListadoCategorias(String busqueda) {
		int registros = 0;
		String queryUsrs = "SELECT codigo, nombre, tipo, valorHora FROM categoria";
		if(!busqueda.equals("")) {
			queryUsrs += " WHERE " + busqueda;
		} else {
			queryUsrs += " WHERE codigo > 0";
		}
		String totalRegistros = "SELECT count(*) as cantidad FROM categoria";
		if(!busqueda.equals("")) {
			totalRegistros += " WHERE " + busqueda;
		}
		try {
			ResultSet result = conn.buscar(totalRegistros);
			result.next();
			registros = result.getInt("cantidad");
			result.close();
		} catch (Exception e) {
			System.out.println("No se ha podido recuperar registros: " + e);
		}
		
		Object[][] categorias = new String[registros][4];
		try {
			ResultSet result = conn.buscar(queryUsrs);
			int indice = 0;
			while (result.next()) {
				categorias[indice][0] = result.getString("codigo");
				categorias[indice][1] = result.getString("nombre");
				categorias[indice][2] = result.getString("tipo");
				categorias[indice][3] = result.getString("valorHora");
				indice++;
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Imposible recuperar registros de categoria: "+e);
		}
		return categorias;
	}
	
	public boolean agregar(String nombre, char tipo, double valorHora) {
		String query = "INSERT INTO categoria (creacion, nombre, tipo, valorHora) VALUES " +
				       "(now(),'"+nombre+"', '"+tipo+"', "+valorHora+")";
		try {
			return conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al actualizar categoria: " + e);
		}
		return false;
	}
	
	public boolean guardar(String nombre, char tipo, double valorHora, String codigo) {
		String query = "UPDATE categoria SET " +
						"modificacion  = now(),"+
						"nombre        = '"+nombre+"', "+
						" tipo         = '"+tipo+"', "+
						" valorHora    = "+valorHora+
						" WHERE codigo = " + codigo;
		try {
			return conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al actualizar categoria: " + e);
		}
		return false;
	}

	public boolean bajar(String codigo) {
		String query = "DELETE FROM categoria WHERE codigo = " + codigo;
		try {
			return conn.bajar(query);
		} catch (Exception e) {
			System.out.println("Error al dar de baja a la categoria: " + e);
		}
		return false;
	}
}
