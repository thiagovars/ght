package com.ght.conexion;

import java.awt.event.WindowAdapter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.ght.classes.Calendario;

public class ConnControlMensual {

	private ConnDAO conn;
	
	public ConnControlMensual() {
		conn = new ConnDAO();
	}
	
	public ResultSet getHorasAprobadas() {
		String query = "SELECT codigo, created, mes, ano, monto, codUsuario FROM controlmensual WHERE estado = 'C'";
		try {
			ResultSet result = conn.buscar(query);
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public boolean registrarHorasAprobadas(String mes, String ano, double monto, String codUsuario){
		String query = "INSERT INTO controlmensual (creacion, mes, ano, monto, estado, codUsuario) VALUES (" +
					   "now(), "+
					   mes+", "+
					   ano+", "+
					   monto+", "+
					   "'C', "+
					   codUsuario+")";
		try {
			return conn.executar(query);
		} catch (Exception e) {
			System.out.println("Error al grabar horas aprobadas: " + e);
		}
		return false;
	}

	public String getEstado(String codUsuario, String mes, String ano) {
		String query = "SELECT estado FROM controlmensual WHERE codUsuario = " + codUsuario
					   +" AND mes = '" + mes + "' AND ano = '" + ano + "'";
		try {
			ResultSet result = conn.buscar(query);
			result.next();
			return result.getString("estado");
		} catch (Exception e) {
			// si no encontrar un estado es porque las horas están abiertas todavia
			return "A";
		}
	}

	public Object[][] getRegInformes(String where) {
		int total = 0;
		String query = "SELECT usr.nombre, control.creacion, control.mes, control.ano, control.monto"+
					    " FROM controlmensual control "+
				    	" INNER JOIN usuario usr ON usr.codigo = control.codUsuario "+
					    " INNER JOIN categoria cat ON cat.codigo = usr.codCategoria ";
		query += " WHERE " + where;
		String queryTotal = "SELECT count(*) as total FROM controlmensual WHERE " + where;
		try {
			ResultSet result = conn.buscar(queryTotal);
			result.next();
			total = result.getInt("total");
			result.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al levantar informes");
			System.out.println(e);
		}
		Object[][] registros = new String[total][5];
		try {
			ResultSet result = conn.buscar(query);
			int i = 0;
			while(result.next()) {
				registros[i][0] = result.getString("nombre");
				registros[i][1] = result.getString("creacion");
				registros[i][2] = result.getString("mes");
				registros[i][3] = result.getString("ano");
				registros[i][4] = result.getString("monto");
				i++;
			}
			return registros;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al levantar informes");
			System.out.println(e);
		}
		return null;
	}
	
}
