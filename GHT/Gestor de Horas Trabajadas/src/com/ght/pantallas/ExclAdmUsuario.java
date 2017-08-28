package com.ght.pantallas;

import com.ght.classes.Usuarios;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ExclAdmUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExclAdmUsuario frame = new ExclAdmUsuario();
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
	public ExclAdmUsuario() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDarBajaAl = new JLabel("Dar baja al usuario");
		lblDarBajaAl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDarBajaAl.setHorizontalAlignment(SwingConstants.CENTER);
		lblDarBajaAl.setBounds(0, 13, 432, 16);
		contentPane.add(lblDarBajaAl);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(156, 42, 100, 2);
		contentPane.add(separator);
		
		JLabel lblCodigoDelUsuario = new JLabel("Codigo del usuario");
		lblCodigoDelUsuario.setBounds(59, 85, 113, 16);
		contentPane.add(lblCodigoDelUsuario);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String codigo = txtCodigo.getText();
				Usuarios usuario = new Usuarios();
				usuario.excluir(codigo);
			}
		});
		btnExcluir.setBounds(156, 114, 97, 25);
		contentPane.add(btnExcluir);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(174, 82, 116, 22);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		setTitle("GHT - Dar baja al usuario");
		
	}
}
