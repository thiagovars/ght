package com.ght.pantallas;

import com.ght.classes.*;
import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;

public class IniciarSession extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JPasswordField txtPassw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IniciarSession frame = new IniciarSession();
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
	public IniciarSession() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Título da tela
		 */
		setTitle("GHT - Ingresar al Sistema");
		
		JLabel lblLogin = new JLabel("Usuario");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLogin.setBounds(111, 66, 61, 18);
		contentPane.add(lblLogin);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblClave.setBounds(125, 91, 47, 14);
		contentPane.add(lblClave);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(185, 64, 137, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPassw = new JPasswordField();
		txtPassw.setBounds(186, 89, 136, 20);
		contentPane.add(txtPassw);
		
		JButton btnIniciarSession = new JButton("Iniciar Session");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtNombre, txtPassw, btnIniciarSession}));
		btnIniciarSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = txtNombre.getText();
				String passw = txtPassw.getText();
				Usuarios usuario = new Usuarios(nombre, passw);
				try	{
					switch	(usuario.getTipoUsuario()) {
						case "A":
							MainAdmFrame pantAdm = new MainAdmFrame();
							pantAdm.setExtendedState(JFrame.MAXIMIZED_BOTH);
							pantAdm.setVisible(true);
							dispose();
							break;
						case "U":
							MainUsuarioFrame pantFunc = new MainUsuarioFrame(usuario);
							pantFunc.setExtendedState(JFrame.MAXIMIZED_BOTH);
							pantFunc.setVisible(true);
							dispose();
							break;
						case "G":
							MainGestorFrame pantGest = new MainGestorFrame();
							pantGest.setExtendedState(JFrame.MAXIMIZED_BOTH);
							pantGest.setVisible(true);
							dispose();
							break;
						default:
							JOptionPane.showMessageDialog(null, "Usuario no encontrado!");
							break;
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error, no fue posible levantar la session!");
					System.out.println("Error al levantar session: " + e);
				}
			}
		});
		btnIniciarSession.setBackground(new Color(0, 153, 204));
		btnIniciarSession.setBounds(111, 132, 211, 23);
		contentPane.add(btnIniciarSession);
		
		JLabel lblNewLabel = new JLabel("Gestor de Horas Trabajadas");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(12, 13, 441, 16);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 42, 441, 2);
		contentPane.add(separator);
		
	}
}
