package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ght.classes.Categorias;
import com.ght.classes.Usuarios;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EdtAdmCategoria extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JFormattedTextField txtValorHora;
	private JComboBox cbxTipo;
	
	private NumberFormat valorHoraFormat;
	
	private static String codigo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EdtAdmCategoria frame = new EdtAdmCategoria(codigo);
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
	public EdtAdmCategoria(final String codigo) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 391, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setTitle("GHT - Edición de Categoria");
		
		JLabel lblEdicinDeCategoria = new JLabel("Edici\u00F3n de Categoria");
		lblEdicinDeCategoria.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEdicinDeCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblEdicinDeCategoria.setBounds(12, 13, 349, 25);
		contentPane.add(lblEdicinDeCategoria);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 36, 349, 2);
		contentPane.add(separator);
		
		/**
		 * Label del error cuando no se puede cadastrar el usuário (debug con syso necesário para tirar el error)
		 */
		final JLabel lblError = new JLabel("\u00A1Error guardar categoria!");
		lblError.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblError.setBounds(0, 44, 351, 35);
		lblError.setVisible(false);
		contentPane.add(lblError);

		/**
		 * Label del suceso del cadastro
		 */
		final JLabel lblExito = new JLabel("\u00A1Categoria guardada con \u00E8xito!");
		lblExito.setForeground(new Color(0, 128, 128));
		lblExito.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblExito.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		lblExito.setBounds(0, 44, 373, 35);
		lblExito.setVisible(false);
		contentPane.add(lblExito);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(30, 95, 56, 16);
		contentPane.add(lblNombre);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(30, 124, 56, 16);
		contentPane.add(lblTipo);
		
		JLabel lblValorHora = new JLabel("Valor Hora");
		lblValorHora.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorHora.setBounds(10, 163, 71, 16);
		contentPane.add(lblValorHora);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(98, 92, 211, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtValorHora = new JFormattedTextField(valorHoraFormat);
		txtValorHora.setValue(new Double(0));
		txtValorHora.setColumns(10);
		txtValorHora.setBounds(98, 159, 116, 22);
		contentPane.add(txtValorHora);
		txtValorHora.setColumns(10);
		
		cbxTipo = new JComboBox();
		cbxTipo.setBounds(98, 124, 155, 22);
		contentPane.add(cbxTipo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre    = txtNombre.getText();
				String tipo      = String.valueOf(cbxTipo.getSelectedItem());
				double valorHora = Double.parseDouble(txtValorHora.getText());
				Categorias categoria = new Categorias(codigo);
				if(categoria.guardar(nombre, tipo, valorHora)) {
					lblExito.setVisible(true);
				} else {
					lblError.setVisible(true);
				}
			}
		});
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnGuardar.setBackground(new Color(0, 153, 204));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBounds(98, 194, 211, 35);
		contentPane.add(btnGuardar);
		
		llenarCampos(codigo);
	}
	
	public void llenarCampos(String codigo){
		Categorias categoria = new Categorias(codigo);
		txtNombre.setText(categoria.getNombre());
		txtValorHora.setValue(new Double(categoria.getValorHora()));
		cbxTipo.setModel(categoria.getTiposCategoria());
	}
}
