package com.ght.conexion;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class ConnUsuarios extends ConnDAO {

	private ConnDAO conn;
	
	public ConnUsuarios() {
		this.conn = new ConnDAO();
	}
	
	public Object[] iniciarSession(String login, String passw){
		Object[] retorno = new String[4];
		String query = "SELECT codigo, nombre, valorHora, codCategoria FROM usuario WHERE login = '"+login+"' AND passw = '"+passw+"'";
		try {
			ResultSet result = this.conn.buscar(query);
			result.next();
			retorno[0] = result.getString("codigo");
			retorno[1] = result.getString("nombre");
			retorno[2] = result.getString("valorHora");
			retorno[3] = result.getString("codCategoria");
			result.close();
			return retorno;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: usuario o clave erradas");
			System.out.println("Error al validar usuario: " + e);
		}
		return retorno;
	}
	
	/**
	 * Metodo que escribe sql para que la classe
	 * de conexión efectivamente lo grabe en la base.
	 * @return true or false
	 */
	public boolean guardar(String nombre, String login, String passw, double valorHora, int categoria) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat formatDate = new SimpleDateFormat("YYYY-MM-dd");
		String creacion = formatDate.format(calendar.getTime());
		String query = "INSERT INTO USUARIO (creacion, nombre, login, passw, valorHora, codCategoria) VALUES " +
				"('"+creacion+"', '"+
				    nombre+"', '"+
				    login+"', '"+
				    passw+"', "+
				    valorHora+", "+
				    categoria+")";
		try {
			return this.conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al guardar usuario: " + e);
		}
		return false;
	}
	
	public boolean actualizaUsuario(String nombre, String login, String passw, double valorHora, int categoria, String codigo) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat formatDate = new SimpleDateFormat("YYYY-MM-dd");
		String modificacion = formatDate.format(calendar.getTime());
		String query = "UPDATE usuario set " +
				"modificacion = '"+modificacion+"', "+
				"nombre = '"+nombre+"', "+
				"login = '"+login+"', "+
				"passw = '"+passw+"', "+
				"valorHora = " +valorHora+", "+ 
				"codCategoria = '"+categoria+"' "+
				"WHERE codigo = " + codigo;
		try {
			return this.conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al actualizar usuario: " + e);
		}
		return false;
	}
	
	public Object[][] getListadoUsuarios(String busqueda){
		int registros = 0;
		String queryUsrs = "SELECT codigo, nombre, login, codCategoria FROM usuario";
		if(!busqueda.equals("")) {
			queryUsrs += " WHERE " + busqueda;
		} else {
			queryUsrs += " WHERE codigo > 0";
		}
		String totalRegistros = "SELECT count(*) as cantidad FROM usuario";
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
		
		Object[][] usuarios = new String[registros][4];
		try {
			ResultSet result = conn.buscar(queryUsrs);
			int indice = 0;
			while (result.next()) {
				usuarios[indice][0] = result.getString("codigo");
				usuarios[indice][1] = result.getString("nombre");
				usuarios[indice][2] = result.getString("login");
				usuarios[indice][3] = result.getString("codCategoria");
				indice++;
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Imposible recuperar registros: "+e);
		}
		return usuarios;
	}
	
	public Object[] getUsuario(String codigo) {
		Object[] usuario = new String[6];
		String query = "SELECT codigo, nombre, login, passw, valorHora, codCategoria FROM usuario WHERE codigo = " + codigo;
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			usuario[0] = result.getString("codigo");
			usuario[1] = result.getString("nombre");
			usuario[2] = result.getString("login");
			usuario[3] = result.getString("passw");
			usuario[4] = result.getString("valorHora");
			usuario[5] = result.getString("codCategoria");
		} catch (Exception e) {
			System.out.println("No fue posible encontrar objeto usuario: " + e);
		}
		return usuario;
	}
	
	public boolean bajar(String codigo){
		String query = "DELETE FROM usuario WHERE codigo = " + codigo;
		try {
			return conn.bajar(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public boolean find(String codigo) {
		String query = "SELECT COUNT(1) AS CANTIDAD FROM usuario WHERE codigo = " + codigo;
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getBoolean("CANTIDAD");
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public ResultSet getNombresUsuarios() {
		String query = "SELECT usuario.codigo, usuario.nombre FROM usuario, categoria"+
					  " WHERE usuario.codCategoria = categoria.codigo AND categoria.tipo = 'U'";
		try {
			return conn.buscar(query);
		} catch (Exception e) {
			System.out.println("Error al levantar listado de nombres de usuarios: "+e);
		}
		return null;
	}

	public String getCodigoByName(String nombre) {
		String query = "SELECT codigo FROM usuario WHERE nombre like '%"+nombre+"%'";
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getString("codigo");
		} catch (Exception e) {
			System.out.println("Error al levantar listado de nombres de usuarios: "+e);
		}
		return null;
	}
}
