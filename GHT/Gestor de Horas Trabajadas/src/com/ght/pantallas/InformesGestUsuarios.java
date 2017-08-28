package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
import javax.swing.table.DefaultTableCellRenderer;

import com.ght.classes.Calendario;
import com.ght.classes.ControlMensual;
import com.ght.classes.Usuarios;

public class InformesGestUsuarios extends JFrame {

	/**	Swing objects **/
	private JPanel contentPane;
	private JFormattedTextField txtMaxMonto;
	private JFormattedTextField txtMinMonto;
	private static JScrollPane sclUsuarios;
	private static JTable table;
	
	/** Object **/
	private static Usuarios usuario = new Usuarios();
	
	/** Atributos **/
	private Object[] busqueda = new String[4];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformesGestUsuarios frame = new InformesGestUsuarios();
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
	public InformesGestUsuarios() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 702, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setTitle("GHT - Informes");
		
		JLabel lblNewLabel = new JLabel("Informes de usuarios");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 660, 21);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 34, 660, 2);
		contentPane.add(separator);
		
		JLabel lblUsuarios = new JLabel("Usuarios");
		lblUsuarios.setBounds(12, 47, 56, 16);
		contentPane.add(lblUsuarios);
		
		JLabel lblMontoDesde = new JLabel("Monto desde");
		lblMontoDesde.setBounds(497, 47, 73, 16);
		contentPane.add(lblMontoDesde);
		
		JLabel lblMontoHasta = new JLabel("Monto hasta");
		lblMontoHasta.setBounds(412, 47, 73, 16);
		contentPane.add(lblMontoHasta);
		
		JLabel lblMes = new JLabel("Mes");
		lblMes.setBounds(212, 47, 56, 16);
		contentPane.add(lblMes);
		
		Usuarios usuarios = new Usuarios();
		final JComboBox comboUsuarios = new JComboBox();
		comboUsuarios.setModel(usuarios.getNombresCbx());
		comboUsuarios.setBounds(12, 66, 188, 22);
		contentPane.add(comboUsuarios);
		
		Calendario calendario = new Calendario();
		final JComboBox comboMeses = new JComboBox();
		comboMeses.setModel(calendario.getMesesCbx());
		comboMeses.setBounds(212, 66, 188, 22);
		contentPane.add(comboMeses);
		
		txtMinMonto = new JFormattedTextField(new Double(0));
		txtMinMonto.setBounds(412, 66, 73, 22);
		contentPane.add(txtMinMonto);
		txtMinMonto.setColumns(10);
		
		txtMaxMonto = new JFormattedTextField(new Double(0));
		txtMaxMonto.setBounds(497, 66, 73, 22);
		contentPane.add(txtMaxMonto);
		txtMaxMonto.setColumns(10);
				
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nombre = String.valueOf(comboUsuarios.getSelectedItem());
				String codUsuario = "";
				if(!nombre.equals("Seleccione Usuario")) {
					usuario.setObjectUsuario(usuario.getCodigo(nombre));
					codUsuario = usuario.getCodigo();
				}
				int mes = comboMeses.getSelectedIndex();
				double minMonto = (double) txtMinMonto.getValue();
				double maxMonto = (double) txtMaxMonto.getValue();
				if( codUsuario.equals("") && mes == 0 && minMonto == 0 && maxMonto == 0) {
					JOptionPane.showMessageDialog(null, "Hay que llenar por lo menos uno de los campos de búsqueda");
				} else {
					Arrays.fill(busqueda, null);
					if(!nombre.equals("")) {
						busqueda[0] = codUsuario;
					}
					if(mes != 0) {
						busqueda[1] = String.valueOf(mes);
					}
					if(minMonto != 0) {
						busqueda[2] = String.valueOf(minMonto);
					}
					if(maxMonto != 0) {
						busqueda[3] = String.valueOf(maxMonto);
					}
					crearTablaInformes();
				}
			}
		});
		btnBuscar.setBackground(new Color(0, 153, 204));
		btnBuscar.setBounds(582, 65, 90, 25);
		contentPane.add(btnBuscar);
	}
	
	public void crearTablaInformes() {
		ControlMensual control = new ControlMensual();
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);
		table.setBounds(10, 31, 444, 340);
		table.setModel(control.getTableModelInforme(busqueda));

		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Codigo del usuario
		table.getColumnModel().getColumn(0).setPreferredWidth(350);
		table.getColumnModel().getColumn(0).setMaxWidth(350);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRender);
		
		// Codigo del usuario
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRender);
		
		// Nombre del usuario
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRender);
		
		//Mes
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setMaxWidth(150);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRender);
		
		//Ano
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setMaxWidth(150);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRender);
			
		sclUsuarios = new JScrollPane(table);
		sclUsuarios.setViewportBorder(UIManager.getBorder("ScrollPane.border"));
		sclUsuarios.setBounds(12, 109, 660, 62);
		contentPane.add(sclUsuarios);
	}
}
