package com.ght.classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ght.conexion.ConnHorarios;
import com.mysql.jdbc.TimeUtil;

public class HorasTrabajadas {
	
	/** Actributos */
	private ConnHorarios conn;
	private Usuarios usuario;
	public Calendario calendario;
	
	/** Constructor general **/
	public HorasTrabajadas() {
		conn = new ConnHorarios();
		calendario = new Calendario();
	}
	
	/** Constructor de horas a partir de un usuário **/
	public HorasTrabajadas(Usuarios usuario) {
		conn = new ConnHorarios();
		this.usuario = usuario;
		calendario = new Calendario();
	}
	
	/** 
	 * Método usado personalisar la busqueda y formatación 
	 * del objeto Calendario. Con eso se puede grabar correctamente
	 * la fecha del cierre mensual, por ejemplo
	 * 
	 * @param anoMes
	 */
	public void setAnoMes(String anoMes) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		try {
			calendario.setTime(dateFormat.parse(anoMes));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Método para recuperar las horas trabajadas del usuario
	 * en la pantalla de registro de horas del usuario
	 * 
	 * @return
	 */
	public Object[][] getHorariosDelUsuario() {
		
		try {
			Object[][] horarios = conn.getHorarios(usuario.getCodigo(), calendario.getMes());
			return horarios;
		} catch (Exception e) {
			System.out.println("Error al levantar las horas del usuario registradas: " + e);
		}
		return null;
	}
	
	/**
	 * Método que sobrescrebe el anterior para personalizar
	 * la busqueda por fecha de los horarios del usuario
	 * 
	 * @param mesAno
	 * @return
	 */
	public Object[][] getHorariosDelUsuario(String mesAno) {
		
		try {
			Object[][] horarios = conn.getHorarios(usuario.getCodigo(), calendario.getMesActual());
			return horarios;
		} catch (Exception e) {
			System.out.println("Error al levantar las horas del usuario registradas: " + e);
		}
		return null;
	}
	
	/**
	 * Método que registra los horários del usuário
	 * 
	 * @param fecha
	 * @param entrada
	 * @param salida
	 * @param horasTrabajadas
	 * @param descanso
	 * @return
	 */
	public boolean registrar(String fecha, String entrada, String salida, String horasTrabajadas, String descanso) {
		return conn.registrar(fecha, entrada, salida, horasTrabajadas, descanso, usuario.getCodigo());
	}
	
	/**
	 * Método que calcula el total de horas del usuario para 
	 * exibir en pantalla. No sirve para calculo
	 * 
	 * @param mes
	 * @return String con el total de horas trabajadas para mostrarselo
	 */
	public String getTotalHoras(String mes) {
		Object[] horasTotales = conn.getTotal(usuario.getCodigo(), mes);
		final DateFormat formatarFecha = new SimpleDateFormat("HH:mm:ss");
		final Calendar calendario = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		long milisegundos = 0;
		calendario.clear();
		long empiezoMS = calendario.getTimeInMillis();
		for (final Object tiempo : horasTotales) {
			try {
				long sustrato = formatarFecha.parse(tiempo.toString()).getTime() - empiezoMS;
				milisegundos += sustrato;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		long horas   = TimeUnit.MILLISECONDS.toHours(milisegundos);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(milisegundos)%60;
		StringBuilder total = new StringBuilder(64);
		total.append(horas);
		total.append(":");
		total.append(minutos);
		return total.toString();
	}
	
	/**
	 * Método que levanta el total de horas del usuario.
	 * Distinto al anterior método 
	 * 
	 * @param campo
	 * @return long con el total de horas para fines de calculo
	 */
	public long getCampoTotalHorasTrabajadas(String campo) {
		Object[] horasTotales = conn.getTotal(usuario.getCodigo(), calendario.getMes());
		final DateFormat formatarFecha = new SimpleDateFormat("HH:mm:ss");
		final Calendar calendario = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		long milisegundos = 0;
		calendario.clear();
		long empiezoMS = calendario.getTimeInMillis();
		for (final Object tiempo : horasTotales) {
			try {
				long sustrato = formatarFecha.parse(tiempo.toString()).getTime() - empiezoMS;
				milisegundos += sustrato;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		switch (campo) {
		case "horas":
			return TimeUnit.MILLISECONDS.toHours(milisegundos);
		case "minutos":
			return TimeUnit.MILLISECONDS.toMinutes(milisegundos)%60;
		}
		return 0;
	}
	
	/**
	 * Método que calcula los honorarios profesionales.
	 * utiliza el método anterior para calcular las horas y minutos
	 * 
	 * @param valorHora
	 * @return double con el total de horas
	 */
	public double calcularHonorarios(double valorHora) {
		long horas = getCampoTotalHorasTrabajadas("horas");
		long minutos = getCampoTotalHorasTrabajadas("minutos");
		double total = ((minutos/60) + horas) * valorHora;
		return total;
	}

	/**
	 * Método para levantar las horas que ya fueron aprobadas
	 * en el mês. Se sabe para que usuário con base al codigo
	 * del usuario ya configurado en la clase al momento de instancia
	 * 
	 * @param anoMes
	 * @return DefaultTableModel con los horarios levantados de la base
	 */
	public TableModel getTableCierreMesModel(String anoMes) {
		String[] column = {"", "cod. Usuario", "Nombre", "Mes", "Ano", "Total de horas del mes"};
		DefaultTableModel model = new DefaultTableModel(null, column) {
			/**
			 * marca cada uno de los checkboxes 
			 * configurados para la tabla
			 */
			@SuppressWarnings("uncheck")
			public Class getColumnClass(int c) {
                switch (c) {
                  case 0: return Boolean.class;
                  default: return String.class;
                }   
              } 
			};
		String codigo = usuario.getCodigo();
		String nombre = usuario.getNombre();
		String[] splitAnoMes = anoMes.split("-");
		String ano = splitAnoMes[0];
		String mes = splitAnoMes[1];
		String totalHoras = getTotalHoras(anoMes);
		model.addRow(new Object[] {false, codigo, nombre, mes, ano, totalHoras});
		return model;
	}
}
