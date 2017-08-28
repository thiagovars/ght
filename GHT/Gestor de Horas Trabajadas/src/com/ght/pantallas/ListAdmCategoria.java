package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.ght.classes.Categorias;

public class ListAdmCategoria extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtNombre;
	private JTable table;
	private JScrollPane sclCategorias;
	private Object[] busqueda = new String[2];
	private static String codigoCategoria = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListAdmCategoria frame = new ListAdmCategoria();
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
	public ListAdmCategoria() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 727, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("GHT - Registro de Categorias");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		levantarCategorias();
		
		JLabel lblRegistroDeCategorias = new JLabel("Registro de Categorias");
		lblRegistroDeCategorias.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRegistroDeCategorias.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeCategorias.setBounds(12, 13, 685, 14);
		contentPane.add(lblRegistroDeCategorias);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 32, 685, 2);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(204, 40, 126, 16);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(214, 62, 116, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		Categorias categorias = new Categorias();
		final JComboBox cbxTipo = new JComboBox();
		cbxTipo.setModel(categorias.getTiposCategoria());
		cbxTipo.setBounds(342, 62, 154, 22);
		contentPane.add(cbxTipo);
		
		JLabel lblTipoCategoria = new JLabel("Tipo Categoria");
		lblTipoCategoria.setBounds(342, 41, 167, 16);
		contentPane.add(lblTipoCategoria);
		
		JButton btnBusqueda = new JButton("Busqueda");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Arrays.fill(busqueda, null);
				if(!txtNombre.getText().equals("")) {
					busqueda[0] = txtNombre.getText().toString();
				}
				if(!cbxTipo.getSelectedItem().equals("Selecione Tipo de Categoria")) {
					busqueda[1] = String.valueOf(cbxTipo.getSelectedItem());
				}
				contentPane.remove(sclCategorias);
				levantarCategorias();
			}
		});
		btnBusqueda.setBackground(new Color(0, 153, 204));
		btnBusqueda.setBounds(508, 62, 91, 23);
		contentPane.add(btnBusqueda);
		
		JButton btnAddCategoria = new JButton("");
		btnAddCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IncAdmCategoria pantIncCat = new IncAdmCategoria();
				pantIncCat.setVisible(true);
			}
		});
		btnAddCategoria.setIcon(new ImageIcon(ListAdmCategoria.class.getResource("/imagenes/plus.png")));
		btnAddCategoria.setBounds(10, 40, 52, 48);
		contentPane.add(btnAddCategoria);
		
		JButton btnEdtCategoria = new JButton("");
		btnEdtCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(codigoCategoria.equals("")) {
					JOptionPane.showMessageDialog(null, "Es necesario selecionar uno registros antes de modificarlo");
				} else {
					EdtAdmCategoria pantEdtCategoria = new EdtAdmCategoria(codigoCategoria);
					pantEdtCategoria.setVisible(true);
				}
			}
		});
		btnEdtCategoria.setIcon(new ImageIcon(ListAdmCategoria.class.getResource("/imagenes/if_pencil_285638.png")));
		btnEdtCategoria.setBounds(66, 40, 52, 48);
		contentPane.add(btnEdtCategoria);
		
		JButton btnBajarCategoria = new JButton("");
		btnBajarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!codigoCategoria.equals("")) {
					Categorias categoria = new Categorias(codigoCategoria);
					if(categoria.bajar()) {
						JOptionPane.showMessageDialog(null, "Categoria bajada con éxito!");
						codigoCategoria = "";
						//talvez tenha que limpar a seleção dos campos checkbox
						levantarCategorias();
					} else {
						JOptionPane.showMessageDialog(null, "Error al dar de baja la categoria!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Es necesario marcar una categoria para dar de baja");
				}
			}
		});
		btnBajarCategoria.setIcon(new ImageIcon(ListAdmCategoria.class.getResource("/imagenes/cancel.png")));
		btnBajarCategoria.setBounds(122, 40, 52, 48);
		contentPane.add(btnBajarCategoria);
	}
	
	public void levantarCategorias() {
		Categorias categorias = new Categorias();
		categorias.getListadoCategorias(busqueda);
		
		table = new JTable();
		table.setBorder(null);
		table.setFillsViewportHeight(true);
		table.setBounds(10, 31, 444, 340);
		table.setModel(categorias.getTableModel());
		
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Columna del marcador
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		
		// Columna del codigo de la categoria.
		table.getColumnModel().getColumn(1).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setMaxWidth(55);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRender);
		
		// Columna del nombre
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		
		
		// Columna del tipo
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRender);
		
		// Columna del valor hora
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRender);
		
		table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            	for (int i = 0; i < table.getModel().getRowCount(); i++) {
            		if((Boolean)table.getModel().getValueAt(i, 0)) {
        				codigoCategoria = (String)table.getValueAt(table.getSelectedRow(), 1);
					}
				}
            }
		});
		sclCategorias = new JScrollPane(table);
		sclCategorias.setViewportBorder(UIManager.getBorder("ScrollPane.border"));
		sclCategorias.setBounds(12, 97, 685, 351);
		contentPane.add(sclCategorias);
	}
}
