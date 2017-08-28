package com.ght.pantallas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainAdmFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainAdmFrame frame = new MainAdmFrame();
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
	public MainAdmFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("GERENCIADOR DE HORAS TRABAJADAS - Adminsitrador");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menu = new JMenuBar();
		
		JMenu archivo         = new JMenu("Archivo");
		JMenu cadastro        = new JMenu("Registro de usuarios");
		JMenu configuraciones = new JMenu("Configuraciones");
		
		JMenuItem logout = new JMenuItem("Cambiar Usuario");
		JMenuItem salir  = new JMenuItem("Cerrar Aplicacion");
		
		JMenuItem nuevoUsr     = new JMenuItem("Nuevo Usuario");
		JMenuItem listUsuarios = new JMenuItem("Listado de Usuarios");
		
		JMenuItem categorias = new JMenuItem("Categorias de usuario");
		
		archivo.add(logout);
		archivo.add(salir);
		
		cadastro.add(nuevoUsr);
		cadastro.add(listUsuarios);
		
		configuraciones.add(categorias);
		
		menu.add(archivo);
		menu.add(cadastro);
		menu.add(configuraciones);
		
		setJMenuBar(menu);
		
		/**
		 * Ación de los menus del ARCHIVO
		 */
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar la sesiòn?", "GHT - Cambiar usuario", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					IniciarSession login = new IniciarSession();
					login.setVisible(true);
					dispose();
				}
			}
		});
		
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(null, "¿Cerrar la aplicaciòn?", "GHT - Cerrar aplicación", JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		/**
		 * Aciòn de los menus de registro
		 */
		nuevoUsr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IncAdmUsuario pantIncUsuario = new IncAdmUsuario();
				pantIncUsuario.setVisible(true);
			}
		});
		listUsuarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ListAdmUsuario pantListUsuario = new ListAdmUsuario();
				pantListUsuario.setVisible(true);
			}
		});
		/**
		 * Ación de los menus de las CATEGORIAS
		 */
		categorias.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ListAdmCategoria pantIncCategoria = new ListAdmCategoria();
				pantIncCategoria.setVisible(true);
			}
		});
	}
}
