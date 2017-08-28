package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ght.classes.Categorias;
import com.ght.classes.Usuarios;

public class IncAdmUsuario extends JFrame {

	/**
	 * Campos del formulario
	 */
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtLogin;
	private JPasswordField txtClave;
	private JFormattedTextField txtValorHora;
	
	/**
	 * format de los números
	 */
	private NumberFormat valorHoraFormat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IncAdmUsuario frame = new IncAdmUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IncAdmUsuario() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 564, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Subtitulo de la pantalla
		 */
		setTitle("GHT - Registro de usuario");
		
		/**
		 * Subtitulo de la pantalla para destacar la pantalla
		 */
		JLabel lblNewLabel = new JLabel("Registro Usuario GHT");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 522, 16);
		contentPane.add(lblNewLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 42, 522, 2);
		contentPane.add(separator_1);
		
		/**
		 * Label del error cuando no se puede cadastrar el usuário
		 */
		final JLabel lblError = new JLabel("\u00A1Error al guardar usuario!");
		lblError.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblError.setBounds(12, 68, 522, 35);
		lblError.setVisible(false);
		contentPane.add(lblError);

		/**
		 * Label del exito del registro
		 */
		final JLabel lblSuceso = new JLabel("\u00A1Usuario guardado con éxito");
		lblSuceso.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuceso.setForeground(new Color(0, 128, 128));
		lblSuceso.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSuceso.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		lblSuceso.setBounds(12, 68, 522, 35);
		lblSuceso.setVisible(false);
		contentPane.add(lblSuceso);
		
		/**
		 * Separador del contenido de la pantalla con los campos
		 */
		JSeparator separator = new JSeparator();
		separator.setBounds(500, 55, 1, 2);
		contentPane.add(separator);
		
		/**
		 * Label nombre
		 */
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(154, 131, 46, 14);
		contentPane.add(lblNombre);
		
		/**
		 * Label login
		 */
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(154, 161, 46, 14);
		contentPane.add(lblLogin);
		
		/**
		 * Label para la categoria
		 */
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(146, 267, 55, 22);
		contentPane.add(lblCategoria);

		/**
		 * Label para la clave
		 */
		JLabel lblClave = new JLabel("Clave");
		lblClave.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClave.setBounds(154, 201, 46, 14);
		contentPane.add(lblClave);
		
		/**
		 * Tipo de cuenta, depende que tipo de usuário me levanta distintas pantallas, ejemplo:
		 * Tipo usuário A: administrador de la aplicacion, que permite cadastar usuários y además
		 * Tipo usuário F: Funcionário utilizador de la aplicacion, que lo unico permitido es ingresar horas trabajadas y mirar el cadastro
		 * campo armado a partir de la tabla CATEGORIA
		 */
		Categorias categoria = new Categorias();
		final JComboBox cbxCategoria = new JComboBox();
		cbxCategoria.setToolTipText("Categoria del usuario");
		cbxCategoria.setModel(categoria.getComboboxModel());
		cbxCategoria.setBounds(218, 267, 150, 22);
		contentPane.add(cbxCategoria);
		
		/**
		 * Campo Nombre
		 */
		txtNombre = new JTextField();
		txtNombre.setToolTipText("Nombre del usu\u00E1rio");
		lblNombre.setLabelFor(txtNombre);
		txtNombre.setBounds(218, 127, 116, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		/**
		 * Campo Login
		 */
		txtLogin = new JTextField();
		txtLogin.setToolTipText("Login por lo cual usa el sistema");
		lblLogin.setLabelFor(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setBounds(218, 162, 116, 22);
		contentPane.add(txtLogin);
		
		/**
		 * Campo Clave secreta
		 */
		txtClave = new JPasswordField();
		txtClave.setToolTipText("Clave secreta del usuario");
		lblClave.setLabelFor(txtClave);
		txtClave.setBounds(218, 197, 116, 22);
		contentPane.add(txtClave);
		
		/**
		 * Campo Valor Hora
		 */
		txtValorHora = new JFormattedTextField(valorHoraFormat);
		txtValorHora.setValue(new Double(0));
		txtValorHora.setColumns(10);
		txtValorHora.setBounds(218, 232, 116, 22);
		contentPane.add(txtValorHora);
		txtValorHora.setColumns(10);
		
		/**
		 * Buton que dispara la grabación en la tabla USUÁRIO
		 */
		JButton btnGuardarUsuario = new JButton("Agregar Usuario");
		btnGuardarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombre.getText();
				String login  = txtLogin.getText();
				String passw  = txtClave.getText();
				String categoria = String.valueOf(cbxCategoria.getSelectedItem());
				double valorHora = Double.parseDouble(txtValorHora.getText());
				if(nombre.equals("") || login.equals("") || passw.equals("")) {
					JOptionPane.showMessageDialog(null, "Campos vazios! Lenar por lo menos el nombre, login y clave");
				} else {
					Usuarios usr = new Usuarios();
					boolean guardarUsuario = usr.guardar(nombre, login, passw, valorHora, categoria, "");
					if(guardarUsuario) {
					    lblSuceso.setVisible(true);
					} else {
						lblError.setVisible(true);
					}
				}
			}
		});
		btnGuardarUsuario.setBackground(new Color(0, 153, 204));
		btnGuardarUsuario.setBounds(218, 302, 150, 25);
		contentPane.add(btnGuardarUsuario);
		
		JLabel lblValorHora = new JLabel("Valor Hora");
		lblValorHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorHora.setBounds(120, 235, 80, 14);
		contentPane.add(lblValorHora);
	}
}
