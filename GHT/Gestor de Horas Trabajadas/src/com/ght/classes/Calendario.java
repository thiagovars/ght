package com.ght.classes;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Calendario {
	
	/** Actributo de la clase **/
	private String meses[];
	
	/** Objecto de la calse **/
	static Calendar calendar;
	
	/** Constant de la clase **/
	public static final int HOUR_OF_DAY = calendar.HOUR_OF_DAY;
	
	/** Formatador de fecha **/
	private SimpleDateFormat formatter;
	
	/** Construct de la clase **/
	public Calendario() {
		calendar = Calendar.getInstance();
		meses = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	/** 
	 * Método que bajo un intero devuleve el nombre del mes.
	 * Utilizado para que la instancia del objeto calendario tire
	 * el nombre correcto en español del mes buscado
	 * 
	 * @return String con el nombre del mes
	 */
	public String getNombreMes(){
		return meses[calendar.get(calendar.MONTH)];
	}
	
	/** 
	 * Método que sirve para encapsular y concentrar 
	 * acciones de calendario. Este método en concreto
	 * lo que hace es devolver la fecha que esta marcado
	 * en la clase. Eso va a depender del "set" que se hicier
	 * de la clase a princípio. Si lo hago new Calendario(),
	 * ese método me tira la fecha actual del computador. Si
	 * antes de utilizar ese metodo se hace el "set" me va a tirar
	 * la fecha definida. Sirve para que la clase calendario
	 * me formate la fecha en forma de String para una fecha real
	 * a cualquer punto del sistema.
	 * 
	 * @return
	 */
	public int getYear() {
		return calendar.YEAR;
	}
	
	/**
	 * Método que simpre me tira la fecha actual del computador
	 * creada para que no dependa de la fecha setada, por si 
	 * necesita hacer una comparacion entre fechas
	 * 
	 * @return String en formato año, mes y días
	 */
	public String getFechaActual() {
		formatter = new SimpleDateFormat("YYYY-MM-dd");
		calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
	
	/**	
	 * Método que solo se diferencia del anterior por que tira
	 * la fecha en formato de año y mês.
	 * 
	 * @return String en formato año y mes
	 */
	public String getMesActual() {
		formatter = new SimpleDateFormat("YYYY-MM");
		calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
	
	/**
	 * Método que devuelve el mes de la clase
	 * que no depende del computador. 
	 * independiente de la fecha del computador.
	 * 
	 * @return String en formato año y mes
	 */
	public String getMes(){
		formatter = new SimpleDateFormat("YYYY-MM");
		return formatter.format(calendar.getTime());
	}

	/**
	 * Método para trabajar con hora.
	 * 
	 * @param hora
	 */
	public void setTime(Date hora) {
		calendar.setTime(hora);
	}
	
	/**
	 * Método agrega horas al calendario
	 * 
	 * @param hourOfDay
	 * @param i
	 */
	public void add(int hourOfDay, int i) {
		calendar.add(hourOfDay, i);
	}

	/** 
	 * Método que de vuelve el horario de la instancia calendario
	 * 
	 * @return
	 */
	public Object getTime() {
		return calendar.getTime();
	}

	/**
	 * Método que calcula la duración.
	 * utilizada para calcular la cantidad de horas trabajadas en el día, por ejemplo.
	 * 
	 * @param value
	 * @param value2
	 * @return
	 */
	public String getSuprimeHoras(String value, String value2) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			
			long minutos  = getMinutos(value, format);
			long minutos2 = getMinutos(value2, format);
			long diference = (minutos2 - minutos)*60*1000;
			Date fecha = new Date(diference);
			return format.format(fecha);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al calcular la duración de horas de trabajo");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Método que devuelve la parte de los minutos de la hora
	 * utilizada para calcular los minutos trabajados con el 
	 * valor hora enbiado
	 * 
	 * @param hora
	 * @param format
	 * @return long con los minutos convertidos
	 */
	public long getMinutos(String hora, SimpleDateFormat format) {
		Date data;
		try {
			data = format.parse(hora);
		} catch (Exception e) {
			return 0;
		}
		long minutos = data.getTime()/1000/60;
		return minutos;
	}
	
	/**
	 * Método original de la insancia calendário
	 * para que sea acesible de fuera de la clase
	 * Utilizado cuando se necesita limpiar 
	 * la fecha y personalizar sin tener que instanciarla otra vez
	 * 
	 */
	public void clear() {
		calendar.clear();
	}

	/**
	 * Método para devolver los milisegundos de la hora
	 * 
	 * @return long tiempo en milisegundos
	 */
	public long getTiempoEnMilisegundos() {
		// TODO Auto-generated method stub
		return calendar.getTimeInMillis();
	}
	
	/**
	 * Método que arma el comboBox con los meses a elijir.
	 * Utilizado en busquedas que toman el mes como buscador.
	 * 
	 * @return DefaultComboBoxModel con los meses de la clase
	 */
	public DefaultComboBoxModel getMesesCbx() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("Seleccione Mes");
		for (int i = 0; i < meses.length; i++) {
			model.addElement(meses[i]);
		}
		return model;
	}
}