package com.ght.classes;

import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.ght.conexion.ConnCategoria;

public class Categorias {
	
	/** Objetos de la clase **/
	private ConnCategoria conn = null;
	private Object[][] listCategorias;
	
	/** Actributos de la clase **/
	private String codigo;
	private String nombre;
	private double valorHora;
	private String tipo;

	/** Constructor general de la clase **/
	public Categorias() {
		this.conn = new ConnCategoria();
	}
	
	/** Constructor a partir de un código de categoria **/
	public Categorias(String codigo) {
		this.conn = new ConnCategoria();
		setObject(codigo);
	}
	
	/**
	 * Método para armar el objeto de la clase a partir de un código de categoria
	 * 
	 * @param codigo
	 */
	public void setObject(String codigo) {
		String busqueda = "codigo = " + codigo;
		Object[][] categorias = conn.getListadoCategorias(busqueda);
		setCodigo((String)categorias[0][0]);
		setNombre((String)categorias[0][1]);
		setTipo((String)categorias[0][2]);
		setValorHora(Double.parseDouble((String) categorias[0][3]));
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getValorHora() {
		return valorHora;
	}

	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/** 
	 * Método que arma el modelo del comboBox con los nombres de las categorias
	 * 
	 * @return DefaultComboBoxModel con los nombres de las categorias
	 */
	public DefaultComboBoxModel getComboboxModel() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			ResultSet result = this.conn.getNombresCategorias();
			model.addElement("Seleccione Categoria");
			while(result.next()) {
				model.addElement(result.getString("nombre"));
			}
			return model;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Método que devuleve un codigo de categoria bajo el nombre que se le dá al método
	 * utilizado, por ejemplo, para levantar el código de la categoria selecionada en
	 * el combobox armado por el método anterior
	 * 
	 * @param nombre
	 * @return integer con el codigo a partir del nombre
	 */
	public int getCodigoByName(String nombre) {
		return conn.getCodigoByName(nombre);
	}
	
	/**
	 * Método que hace lo contrario del método anterior.
	 * utilizado en pantallas que hacen ligación con tablas que tienen el código
	 * de la categoria, por ejemplo la tabla usuario.
	 * 
	 * @param codigo
	 * @return String con el nombre de la categoria
	 */
	public String getNameByCodigo(String codigo) {
		return conn.getNameByCodigo(codigo);
	}
	
	/**
	 * Método que trae el típo de categoria
	 * U = Usuario
	 * A = Administrador
	 * G = Gestor
	 * 
	 * @param codigo
	 * @return
	 */
	public String getTipoByCodigo(String codigo) {
		return conn.getTipoByCodigo(codigo);
	}
	
	/**
	 * Es posible configurar un valor hora general para
	 * cada categoria de usuarios. Aunque solo los usuários
	 * puedan registrar sus horários, la programación permite configurar
	 * para cada una de las categorias su valor hora.
	 * 
	 * @param codigo
	 * @return double con el valor hora configurado para la categoria
	 */
	public double getValorHora(String codigo) {
		return conn.getValorHoraByCodigo(codigo);
	}

	/**
	 * Método que arma el comboBox con el listado de tipos de categoria
	 * Solo 3 son posíbles de eligir.
	 * A, G U. Ese método fue creado para que no sea libre crear alguna
	 * categoria no prevista por el programa. Él tipo es que define que tipo
	 * de pantalla el que utiliza el programa puede levantar.
	 * 
	 * @return DefaultComboBoxModel con el listado de tipos categoria
	 */
	public DefaultComboBoxModel<String> getTiposCategoria() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione Tipo de Categoria");
		model.addElement("Administrador");
		model.addElement("Gestor");
		model.addElement("Usuario");
		if(codigo != null) {
			String tipoSelecionado = conn.getTipoByCodigo(codigo);
			model.setSelectedItem(getTipoChar(tipoSelecionado));
		}
		return model;
	}
	
	/**
	 * Método que traduce el nombre del tipo de categoria
	 * para el char que es efectivamente grabado en la tabla
	 * categoria de la base de datos
	 * 
	 * @param tipo
	 * @return char con el tipo de categoria
	 */
	public char getCharTipo(String tipo) {
		switch(tipo) {
		case "Administrador":
			return 'A';
		case "Gestor":
			return 'G';
		case "Usuario":
			return 'U';
		}
		return 0;
	}
	
	/**
	 * Método que hace la vuelta del método anterior, se traduce 
	 * el nombre bajo el char que viene del banco
	 * 
	 * @param tipoSelecionado
	 * @return String con el nombre de la categoria
	 */
	public String getTipoChar(String tipoSelecionado) {
		switch(tipoSelecionado) {
		case "A":
			return "Administrador";
		case "G":
			return "Gestor";
		case "U":
			return "Usuario";
		}
		return "";
	}

	/**
	 * Método que lista las categorias registradas en la base
	 * a partir de una busqueda en la pantalla que lista estas categorias
	 * Solo el administrador del sistema puede configurar estas categorias
	 * 
	 * @param busqueda
	 * @return Object con las categorias creadas
	 */
	public Object[][] getListadoCategorias(Object[] busqueda) {
		String campos = "";
		String[] modelo = {"nombre", "tipo", "valorHora"};
		for (int i = 0; i < busqueda.length; i++) {
			if((String)busqueda[i] != null) {
				if (!campos.equals("")) {
					campos += " AND ";
				} else if(modelo[i].equals("tipo")) {
					campos += modelo[i] + " = '" + getCharTipo((String)busqueda[i]) + "'";
				} else {
					campos += modelo[i] + " like '%" + busqueda[i] + "%'";
				}
			}
		}
		listCategorias = conn.getListadoCategorias(campos);
		return listCategorias;
	}

	/**
	 * Método para levantar categorias y configurar 
	 * checkboxes que van a servir para identificar la
	 * linea que se pretende editar o dar de baja
	 * 
	 * @return Objeto de modelo de tabla con checkboxes
	 */
	public DefaultTableModel getTableModel() {
		String[] column = {"", "Codigo", "Nombre", "Tipo", "Valor hora"};
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
		for (int i = 0; i < listCategorias.length; i++) {
			model.addRow(new Object[] {false, listCategorias[i][0],listCategorias[i][1], listCategorias[i][2], listCategorias[i][3]});
		}
		return model;
	}
	
	/**
	 * Método que ingresa categorias a la base de datos
	 * 
	 * @param nombre
	 * @param tipo
	 * @param valorHora
	 * @return boolean con éxito o falla
	 */
	public boolean agregar(String nombre, String tipo, double valorHora) {
		return conn.agregar(nombre, getCharTipo(tipo), valorHora);
	}
	
	/**
	 * Método que actualiza las modificaciones de la categoria
	 * 
	 * @param nombre
	 * @param tipo
	 * @param valorHora
	 * @return
	 */
	public boolean guardar(String nombre, String tipo, double valorHora) {
		return conn.guardar(nombre, getCharTipo(tipo), valorHora, codigo);
	}

	/** 
	 * Método que da de baja a una categoria
	 * 
	 * @return boolean con éxito o falla
	 */
	public boolean bajar() {
		return conn.bajar(codigo);
	}
}
