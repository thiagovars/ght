package com.ght.conexion;

import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class ConnHorarios {

	private ConnDAO conn;
	
	public ConnHorarios() {
		conn = new ConnDAO();
	}
	
	public Object[][] getHorarios(String codigoUsuario, String mesActual) {
		String busqueda = "SELECT codigo, fecha, entrada, salida, descanso, total FROM horarios where codUsuario = " + 
	                       codigoUsuario + " AND date_format(fecha, '%Y-%m') = '" + mesActual + "'";
		int registros = 0;
		String totalRegistros = "SELECT COUNT(*) as cantidad FROM horarios WHERE codUsuario = " + 
								 codigoUsuario + " AND date_format(fecha, '%Y-%m') = '" + mesActual + "'";
		try {
			ResultSet result = conn.buscar(totalRegistros);
			result.next();
			registros = result.getInt("cantidad");
			result.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error! Al levantar horarios del usuario");
			System.out.println("Error al contar registros de horas del usuario: " + e);
		}
		Object[][] horarios = new String[registros][5];
		
		try {
			ResultSet result = conn.buscar(busqueda);
			int indice = 0;
			while(result.next()) {
				horarios[indice][0] = result.getString("fecha");
				horarios[indice][1] = result.getString("entrada");
				horarios[indice][2] = result.getString("salida");
				horarios[indice][3] = result.getString("descanso");
				horarios[indice][4] = result.getString("total");
				indice++;
			}
			return horarios;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al levantar Horarios del usuario");
			System.out.println("Erro al levantar Horarios del usuário: " + e);
		}
		return null;
	}
	
	public Object[] getTotal(String codUser, String mesActual) {
		String cantidad = "SELECT COUNT(total) as total FROM horarios WHERE codUsuario = " + 
	                       codUser + " AND date_format(fecha, '%Y-%m') = '" + mesActual + "'";
		int totalRegistros = 0;
		try {
			ResultSet registros = conn.buscar(cantidad);
			registros.next();
			totalRegistros = registros.getInt("total");
			registros.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al contar la cantidad de registros de horas");
			System.out.println("Error al contar la cantidad de registros de horas: " + e);
		}
		String query = "SELECT total FROM horarios WHERE codUsuario = " + 
		                codUser  + " AND date_format(fecha, '%Y-%m') = '" + mesActual + "'";
		Object[] total = new String[totalRegistros];
		try {
			ResultSet result = conn.buscar(query);
			int i = 0;
			while(result.next()) {
				total[i] = result.getString("total");
				i++;
			}
			return total;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al levantar los totales de horas");
			System.out.println("Error al levantar los totales de horas: " + e + " - " + query);
		}
		return null;
	}
	
	public boolean registrar(String fecha, String entrada, String salida, String horasTrab, String descanso, String codUsuario){
		String query = "INSERT INTO horarios (fecha, entrada, salida, total, descanso, codUsuario) VALUES "+
					  "('"+fecha+"', '"+
						entrada+"', '"+
						salida+"', '"+
						horasTrab+"', '"+
						descanso+"', "+
						codUsuario+")";
		try {
			return conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al registrar horarios: " + e);
		}
		return false;
	}
}
