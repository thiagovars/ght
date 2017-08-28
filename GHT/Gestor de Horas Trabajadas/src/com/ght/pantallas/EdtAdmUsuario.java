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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ght.classes.Categorias;
import com.ght.classes.Usuarios;
import com.sun.corba.se.impl.legacy.connection.DefaultSocketFactory;

public class EdtAdmUsuario extends JFrame {

	private static String codUsuario;
	
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtLogin;
	private JPasswordField paswClave;
	private JComboBox comboBox;
	private JFormattedTextField txtValorHora;
	
	private NumberFormat valorHoraFormat;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EdtAdmUsuario frame = new EdtAdmUsuario(codUsuario);
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
	public EdtAdmUsuario(final String codUsuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 386, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Label del error cuando no se puede cadastrar el usuário (debug con syso necesário para tirar el error)
		 */
		final JLabel lblError = new JLabel("\u00A1Error. Imposible guardar usuario!");
		lblError.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblError.setBounds(0, 68, 351, 35);
		lblError.setVisible(false);
		contentPane.add(lblError);

		/**
		 * Label del suceso del cadastro
		 */
		final JLabel lblSuceso = new JLabel("\u00A1Suceso! Usuario guardado con \u00E8xito");
		lblSuceso.setForeground(new Color(0, 128, 128));
		lblSuceso.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSuceso.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		lblSuceso.setBounds(0, 68, 373, 35);
		lblSuceso.setVisible(false);
		contentPane.add(lblSuceso);
		
		
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(63, 153, 56, 16);
		contentPane.add(lblNombre);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(63, 182, 56, 16);
		contentPane.add(lblLogin);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea.setBounds(43, 257, 71, 16);
		contentPane.add(lblContrasea);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCategoria.setBounds(43, 297, 71, 16);
		contentPane.add(lblCategoria);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setBounds(63, 118, 56, 16);
		contentPane.add(lblCodigo);
		
		JLabel lblValorHora = new JLabel("Valor Hora");
		lblValorHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorHora.setBounds(43, 223, 71, 16);
		contentPane.add(lblValorHora);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(131, 115, 116, 22);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(131, 150, 116, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.setBounds(131, 179, 116, 22);
		contentPane.add(txtLogin);
		
		paswClave = new JPasswordField();
		paswClave.setBounds(131, 254, 116, 22);
		contentPane.add(paswClave);
		
		txtValorHora = new JFormattedTextField(valorHoraFormat);
		txtValorHora.setValue(new Double(0));
		txtValorHora.setColumns(10);
		txtValorHora.setBounds(131, 219, 116, 22);
		contentPane.add(txtValorHora);
		txtValorHora.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(19, 42, 333, 2);
		contentPane.add(separator);
		setTitle("GHT - Edición de usuarios");
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombre.getText();
				String login  = txtLogin.getText();
				String passw  = paswClave.getText();
				String codigo = txtCodigo.getText();
				double valorHora = Double.parseDouble(txtValorHora.getText());
				String categoria = String.valueOf(comboBox.getSelectedItem());
				Usuarios usr = new Usuarios();
				boolean guardarUsuario = usr.guardar(nombre, login, passw, valorHora, categoria, codigo);
				/**
				 * verifica si se puede grabar el usuário
				 * en caso afirmativo muestra la mensaje de suceso.
				 */
				if(guardarUsuario) {
				    lblSuceso.setVisible(true);
				} else {
					lblError.setVisible(true);
				}
			}
		});
		btnGuardar.setBackground(new Color(0, 153, 204));
		btnGuardar.setBounds(131, 329, 116, 25);
		contentPane.add(btnGuardar);
		
		llenarCampos(codUsuario);

	}
	
	public void llenarCampos(String codUsuario){
		Usuarios usr = new Usuarios(codUsuario);
		txtCodigo.setText(usr.getCodigo());
		txtNombre.setText(usr.getNombre());
		txtLogin.setText(usr.getLogin());
		paswClave.setText(usr.getClave());
		txtValorHora.setValue(new Double(usr.getValorHoraEdt()));
		Categorias categorias = new Categorias();
		comboBox = new JComboBox();
		comboBox.setModel(categorias.getComboboxModel());
		comboBox.setSelectedItem(categorias.getNameByCodigo(usr.getCodCategoria()));
		comboBox.setBounds(131, 294, 116, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Edici\u00F3n Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 344, 22);
		contentPane.add(lblNewLabel);
	}
}
