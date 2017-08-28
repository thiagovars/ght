package com.ght.pantallas;

import java.awt.BorderLayout;
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

public class MainGestorFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGestorFrame frame = new MainGestorFrame();
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
	public MainGestorFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setTitle("GERENCIADOR DE HORAS TRABAJADAS - Gestor");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menu = new JMenuBar();
		
		JMenu archivo = new JMenu("Archivo");
		JMenu acciones = new JMenu("Usuarios");
		
		JMenuItem logout = new JMenuItem("Cambiar Usuario");
		JMenuItem salir  = new JMenuItem("Cerrar Aplicación");
		
		JMenuItem cierreDelMes = new JMenuItem("Cierre del mes");
		JMenuItem informes     = new JMenuItem("Informes");
		
		archivo.add(logout);
		archivo.add(salir);
		
		acciones.add(cierreDelMes);
		acciones.add(informes);
		menu.add(archivo);
		menu.add(acciones);
		
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
		 * Apertura de la pantalla para controlar las horas del mes
		 * de cada funcionario
		 */
		cierreDelMes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CierreGestMes pantCierreMes = new CierreGestMes();
				pantCierreMes.setVisible(true);
			}
		});
		
		/**
		 * Informes de cierres de los usuarios
		 */
		informes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				InformesGestUsuarios pantInfGestUsu = new InformesGestUsuarios();
				pantInfGestUsu.setVisible(true);
			}
		});
	}

}
