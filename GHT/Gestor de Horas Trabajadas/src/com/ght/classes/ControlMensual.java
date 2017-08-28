package com.ght.classes;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ght.conexion.ConnControlMensual;

public class ControlMensual {

	/** Objetos de la clase **/
	private ConnControlMensual conn;
	private Calendario calendario;
	
	/** Constructor general de la clase **/
	public ControlMensual() {
		conn = new ConnControlMensual();
		calendario = new Calendario();
	}
	
	/** 
	 * Método para aprobar las horas listadas del usuario
	 * 
	 * @param monto
	 * @param codUsuario
	 * @param anoMes
	 * @return boolean con éxito o falla
	 */
	public boolean registrarHorasAprobadas(double monto, String codUsuario, String anoMes) {
		String[] arrAnoMes = anoMes.split("-");
		return conn.registrarHorasAprobadas(arrAnoMes[1], arrAnoMes[0], monto, codUsuario);
	}
	
	/**
	 * Método para recuperar el estado de las horas, si está aprobado  debe tener
	 * la letra "C" de "Cerrado"
	 * 
	 * @param codUsuario
	 * @param anoMes
	 * @return String con el estado para mostrarselo en pantalla
	 */
	public String getEstado(String codUsuario, String anoMes) {
		String[] arrAnoMes = anoMes.split("-");
		String estado = conn.getEstado(codUsuario, arrAnoMes[1], arrAnoMes[0]);
		switch(estado) {
		case "C" :
			return "Cerrado";
		case "A" :
			return "Abierto";
		}
		return "";
	}

	/** 
	 * Método para armar la tabla con distintos filtros de cierre mensual
	 * informe con el nombre de usuario, la fecha de aprobación del horario
	 * el mes en que se hizo el cierre, año y el monto aprobado.
	 * 
	 * @param busqueda
	 * @return DefaultTableModel con los datos del control mensual
	 */
	public TableModel getTableModelInforme(Object[] busqueda) {
		String[] columnas = {"Nombre", "fecha de Cerramiento", "Mes", "Ano", "Monto"};
		String[] nombreCampos = {"codUsuario", "mes", "minMonto", "maxMonto"};
		String query = "";
		for (int i = 0; i < busqueda.length; i++) {
			if((String)busqueda[i] != null) {
				if (!query.equals("")) {
					query += " AND ";
				}
				if(nombreCampos[i].equals("minMonto")) {
					query += "monto >= " + busqueda[i];
				} else if(nombreCampos[i].equals("maxMonto")) {
					query += "monto <= " + busqueda[i];
				} else {
					query += nombreCampos[i] + " = " + busqueda[i];
				}
			}
		}
		Object[][] registros = conn.getRegInformes(query);
		DefaultTableModel model = new DefaultTableModel(registros, columnas);
		return model;
	}
}
