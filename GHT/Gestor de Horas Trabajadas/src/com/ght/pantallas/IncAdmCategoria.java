package com.ght.pantallas;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ght.classes.Categorias;

public class IncAdmCategoria extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JComboBox cbxTipo;
	private JFormattedTextField txtValorHora;

	private NumberFormat valorHoraFormat;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IncAdmCategoria frame = new IncAdmCategoria();
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
	public IncAdmCategoria() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		setTitle("GHT - Adición de Categoria");
		contentPane.setLayout(null);
		
		JLabel lblIncCategoria = new JLabel("Adici\u00F3n de Categoria");
		lblIncCategoria.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIncCategoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncCategoria.setBounds(12, 13, 408, 25);
		contentPane.add(lblIncCategoria);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 36, 408, 2);
		contentPane.add(separator);
		
		/**
		 * Label del error cuando no se puede cadastrar el usuário (debug con syso necesário para tirar el error)
		 */
		final JLabel lblError = new JLabel("\u00A1Error guardar categoria!");
		lblError.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblError.setBounds(22, 44, 398, 35);
		lblError.setVisible(false);
		contentPane.add(lblError);

		/**
		 * Label del suceso del cadastro
		 */
		final JLabel lblExito = new JLabel("\u00A1Categoria guardada con \u00E8xito!");
		lblExito.setForeground(new Color(0, 128, 128));
		lblExito.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblExito.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		lblExito.setBounds(22, 44, 398, 35);
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
		
		Categorias categoria = new Categorias();
		cbxTipo = new JComboBox();
		cbxTipo.setBounds(98, 124, 211, 26);
		cbxTipo.setModel(categoria.getTiposCategoria());
		contentPane.add(cbxTipo);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Categorias categoria = new Categorias();
				String nombre = txtNombre.getText();
				double valorHora = Double.parseDouble(txtValorHora.getText());
				String tipo = String.valueOf(cbxTipo.getSelectedItem());
				if(tipo.equals("Selecione Tipo de Categoria") || nombre.equals("")) {
					JOptionPane.showMessageDialog(null, "Todos los campos deben ser llenados");
				} else {
					if(categoria.agregar(nombre, tipo, valorHora)) {
						lblExito.setVisible(true);
					} else 
						lblError.setVisible(true);
				}
			}
		});
		btnAgregar.setBackground(new Color(0, 153, 204));
		btnAgregar.setBounds(98, 194, 211, 35);
		contentPane.add(btnAgregar);
		
	}
}
