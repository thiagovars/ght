package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.ght.classes.Calendario;
import com.ght.classes.HorasTrabajadas;
import com.ght.classes.Usuarios;

public class CierreGestMes extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane sclUsuarios;
	
	private DateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd");
	
	private Object[][] horariosDelUsuario = null;
	private Object[] busqueda = new String[3];
	private Usuarios usuario = new Usuarios();
	
	private String mesSelecionado = null;
	private String anoSelecionado = null;
	private static String usuarioSelecionado = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CierreGestMes frame = new CierreGestMes();
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
	public CierreGestMes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 691, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setTitle("GHT - Cierre del mes");
		
		JLabel lblNewLabel = new JLabel("Cierre del Mes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(12, 13, 649, 20);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 35, 649, 2);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("Usuario");
		lblNombre.setBounds(27, 70, 56, 16);
		contentPane.add(lblNombre);
		
		JLabel lblMesInicio = new JLabel("Mes");
		lblMesInicio.setBounds(276, 70, 116, 16);
		contentPane.add(lblMesInicio);
		
		Usuarios usuarios = new Usuarios();
		final JComboBox comboUsuarios = new JComboBox();
		comboUsuarios.setModel(usuarios.getNombresCbx());
		comboUsuarios.setBounds(28, 96, 188, 22);
		contentPane.add(comboUsuarios);
		
		Calendario calendario = new Calendario();
		final JComboBox comboMeses = new JComboBox();
		comboMeses.setModel(calendario.getMesesCbx());
		comboMeses.setBounds(275, 96, 188, 22);
		contentPane.add(comboMeses);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombreUsr = String.valueOf(comboUsuarios.getSelectedItem());
				if(nombreUsr.equals("Seleccione Usuario")){
					JOptionPane.showMessageDialog(null, "Seleccione un usuario!");
				} else if (String.valueOf(comboMeses.getSelectedItem()).equals("Seleccione Mes")){
					JOptionPane.showMessageDialog(null, "Seleccione un mes!");
				} else {
					usuario.setObjectUsuario(usuario.getCodigo(nombreUsr));
					String mesAno = "2017-"+String.format("%02d", comboMeses.getSelectedIndex());
					usuarioSelecionado = null;
					mesSelecionado     = null;
					anoSelecionado     = null;
					contentPane.remove(sclUsuarios);
					crearTablaUsuarios(mesAno);
				}
			}
		});
		btnBuscar.setBackground(new Color(0, 153, 204));
		btnBuscar.setBounds(518, 95, 97, 25);
		contentPane.add(btnBuscar);
		
		JButton btnDetallar = new JButton("Detallar");
		btnDetallar.setHorizontalAlignment(SwingConstants.LEFT);
		btnDetallar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(usuarioSelecionado.equals("")){
					JOptionPane.showMessageDialog(null, "Para detallar es necesario marcar un usuario");
				} else {
					InformeGestHoras pantRelHrs = new InformeGestHoras(usuario, anoSelecionado+"-"+mesSelecionado);
					pantRelHrs.setVisible(true);
				}
			}
		});
		btnDetallar.setIcon(new ImageIcon(CierreGestMes.class.getResource("/imagenes/zoom_48x48.png")));
		btnDetallar.setBounds(27, 131, 189, 49);
		contentPane.add(btnDetallar);
		
		crearTablaUsuarios("0000-00");

	}
	
	public void crearTablaUsuarios(String mesAno) {
		HorasTrabajadas cierreMes = new HorasTrabajadas(usuario);
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setBounds(10, 31, 444, 340);
		if (usuario.getNombre() != null) {
			table.setModel(cierreMes.getTableCierreMesModel(mesAno));

			DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
			centerRender.setHorizontalAlignment(SwingConstants.CENTER);
			
			DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
			rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
			
			// Checkbox
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			table.getColumnModel().getColumn(0).setMaxWidth(20);
			
			// Codigo del usuario
			table.getColumnModel().getColumn(1).setPreferredWidth(82);
			table.getColumnModel().getColumn(1).setMaxWidth(82);
			table.getColumnModel().getColumn(1).setCellRenderer(centerRender);
			
			// Nombre del usuario
			table.getColumnModel().getColumn(2).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setMaxWidth(300);
			
			//Mes
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
			table.getColumnModel().getColumn(3).setMaxWidth(40);
			table.getColumnModel().getColumn(3).setCellRenderer(centerRender);
			
			//Ano
			table.getColumnModel().getColumn(4).setPreferredWidth(40);
			table.getColumnModel().getColumn(4).setMaxWidth(40);
			table.getColumnModel().getColumn(4).setCellRenderer(centerRender);
			
			//Total de horas trabajadas
			table.getColumnModel().getColumn(5).setPreferredWidth(150);
			table.getColumnModel().getColumn(5).setMaxWidth(150);
			table.getColumnModel().getColumn(5).setCellRenderer(rightRender);
			
			//Tomando los valores de los checkboxes
			table.getModel().addTableModelListener(new TableModelListener() {
	            @Override
	            public void tableChanged(TableModelEvent e) {
	            	for (int i = 0; i < table.getModel().getRowCount(); i++) {
	            		if((Boolean)table.getModel().getValueAt(i, 0)) {
	        				usuarioSelecionado = (String)table.getValueAt(table.getSelectedRow(), 1);
	        				mesSelecionado     = (String)table.getValueAt(table.getSelectedRow(), 3);
	        				anoSelecionado     = (String)table.getValueAt(table.getSelectedRow(), 4);
						}
					}
	            }
			});
		}
		sclUsuarios = new JScrollPane(table);
		sclUsuarios.setViewportBorder(UIManager.getBorder("ScrollPane.border"));
		sclUsuarios.setBounds(12, 206, 649, 62);
		contentPane.add(sclUsuarios);
	}
}
