package com.ght.classes;

import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

import com.ght.conexion.ConnUsuarios;

public class Usuarios {
	
	private String tipoUsuario;
	private ConnUsuarios conn;
	
	// campos de la base de datos
	private String codigo;
	private String nombre;
	private String login;
	private String clave;
	private double valorHora;
	private String codCategoria;
	
	/** Construct muy usado para levantar el objeto usuário antes de tener su codigo **/
	public Usuarios() {
		conn = new ConnUsuarios();
	}
	
	/** Constructor para quando se levanta la session **/
	public Usuarios(String login, String passw) {
		conn = new ConnUsuarios();
		iniciarSession(login, passw);
	}
	
	/** Constructor usado después de una busqueda donde se conoce el código del usuario **/
	public Usuarios(String codigo) {
		conn = new ConnUsuarios();
		setObjectUsuario(codigo);
	}
	
	/** Iniciar sessión va a levantar los atributos de la clase para el usuario **/
	public boolean iniciarSession(String login, String passw) {
		Object[] retorno = new String[6];
		retorno = conn.iniciarSession(login, passw);
		String codigo       = retorno[0].toString();
		String nombre       = retorno[1].toString();
		String valorHora    = retorno[2].toString();
		String codCategoria = retorno[3].toString();
		setObjectUsuario(codigo);
		Categorias categoria = new Categorias();
		setTipoUsuario(categoria.getTipoByCodigo(codCategoria));
		return (!this.getCodigo().equals("") && !this.getTipoUsuario().equals(""));
	}
	
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getCodigo(String nombre) {
		return conn.getCodigoByName(nombre);
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setValorHora(double valor) {
		this.valorHora = valor;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

	/**
	 * Metodo usado para agregar usuario o actualizar su información
	 * 
	 * @param nombre
	 * @param login
	 * @param passw
	 * @param valorHora
	 * @param categoria
	 * @param codigo
	 * @return boolean con exito o falla de la acción
	 */
	public boolean guardar(String nombre, String login, String passw, double valorHora, String categoria, String codigo) {
		Categorias categorias = new Categorias();
		if(codigo.equals("")) {
			return conn.guardar(nombre, login, passw, valorHora, categorias.getCodigoByName(categoria));
		} else {
			return conn.actualizaUsuario(nombre, login, passw, valorHora, categorias.getCodigoByName(categoria), codigo);
		}
	}
	
	/**
	 * Arma el objeto usuário con lo que tiene en la base de datos
	 * 
	 * @param codigo
	 */
	public void setObjectUsuario(String codigo) {
		Object[] objectUsuarios = conn.getUsuario(codigo);
		this.codigo = objectUsuarios[0].toString();
		nombre = objectUsuarios[1].toString();
		login  = objectUsuarios[2].toString();
		clave  = objectUsuarios[3].toString();
		valorHora = Double.parseDouble(objectUsuarios[4].toString());
		codCategoria = objectUsuarios[5].toString();
	}
	
	/**
	 * Método para levantar el listado de usuarios a partir de una búsqueda
	 * 
	 * @param búsqueda
	 * @return
	 */
	public Object[][] getListadoUsuarios(Object[] busqueda) {
		String campos = "";
		String[] modelo = {"nombre", "login", "codCategoria"};
		Categorias categorias = new Categorias();
		for (int i = 0; i < busqueda.length; i++) {
			if((String)busqueda[i] != null) {
				if (!campos.equals("")) {
					campos += " AND ";
				}
				if (modelo[i] == "codCategoria") {
					campos += modelo[i] + " = " + categorias.getCodigoByName((String)busqueda[i]);
				} else {
					campos += modelo[i] + " like '%" + busqueda[i] + "%'";
				}
			}
		}
		Object[][] objectUsuarios = conn.getListadoUsuarios(campos);
		
		for (int i = 0; i < objectUsuarios.length; i++) {
			objectUsuarios[i][3] = categorias.getNameByCodigo(objectUsuarios[i][3].toString());
		}
		return objectUsuarios;
	}
	
	/**
	 * Método corto para dar de baja al usuario
	 * 
	 * @param codigo
	 * @return boolean con éxito o falla
	 */
	public boolean excluir(String codigo) {
		return conn.bajar(codigo);
	}
	
	/**
	 * Cuando la pantalla es la de edición del usuario
	 * debe mostrar el horario configurado para él
	 * y no lo de la categoria que sirve para calcular
	 * los honorarios profesionales
	 * 
	 * @return
	 */
	public double getValorHoraEdt() {
		return valorHora;
	}
	
	/**
	 * Recupera el valor hora para efectivamente hacer el calculo de los honorarios profesionales
	 * 
	 * @return
	 */
	public double getValorHora() {
		if(valorHora == 0) {
			Categorias categoria = new Categorias();
			return categoria.getValorHora(getCodCategoria());
		} else {
			return valorHora;
		}
	}
	
	/**
	 * Método que arma el modelo de checkbox ya con los nombres de los usuarios
	 * para eligir
	 * 
	 * @return
	 */
	public DefaultComboBoxModel getNombresCbx() {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		try {
			ResultSet result = conn.getNombresUsuarios();
			model.addElement("Seleccione Usuario");
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
	 * Método que arma el modelo de tabla para las distintas pantallas 
	 * en donde se necesita levantar un listado de usuários
	 * 
	 * @param busqueda
	 * @return
	 */
	public DefaultTableModel getTableModel(Object[] busqueda) {
		String[] column = {"", "codigo", "nombre", "usuario", "Categoria"};
		Object[][] usrs = getListadoUsuarios(busqueda);
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
		for (int i = 0; i < usrs.length; i++) {
			model.addRow(new Object[] {false, usrs[i][0],usrs[i][1], usrs[i][2], usrs[i][3]});
		}
		return model;
	}
}
