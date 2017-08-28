package com.ght.pantallas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.ght.classes.Categorias;
import com.ght.classes.Usuarios;
import javax.swing.JSeparator;

public class ListAdmUsuario extends JFrame {

	/** objects swing **/
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtLogin;
	private JTable table;
	private JScrollPane sclUsuarios;
	
	/** Objects **/
	private Object[] busqueda = new String[3];
	
	/** Atributo **/
	private String usrSeleccionado = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListAdmUsuario frame = new ListAdmUsuario();
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
	public ListAdmUsuario() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 724, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Titulo de la pantalla
		 */
		setTitle("GHT - Listado de usuarios");
		levantarListadoUsuarios();
		
		JLabel lblListadoDeUsuarios = new JLabel("Listado de usuarios");
		lblListadoDeUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblListadoDeUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeUsuarios.setBounds(12, 13, 685, 22);
		contentPane.add(lblListadoDeUsuarios);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(12, 55, 116, 16);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(12, 77, 116, 22);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel login = new JLabel("Usuario");
		login.setBounds(156, 55, 116, 16);
		contentPane.add(login);
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.setBounds(156, 77, 116, 22);
		contentPane.add(txtLogin);
		
		Categorias categorias = new Categorias();
		final JComboBox cbxTipo = new JComboBox();
		cbxTipo.setModel(categorias.getComboboxModel());
		cbxTipo.setBounds(300, 77, 154, 22);
		contentPane.add(cbxTipo);
		
		JLabel lblNewLabel = new JLabel("Tipo Usuario");
		lblNewLabel.setBounds(300, 55, 167, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Arrays.fill(busqueda, null);
				if(!txtNombre.getText().equals("")) {
					busqueda[0] = txtNombre.getText().toString();
				}
				if(!txtLogin.getText().equals("")) {
					busqueda[1] = txtLogin.getText().toString();
				}
				if(!cbxTipo.getSelectedItem().equals("Seleccione Categoria")) {
					busqueda[2] = String.valueOf(cbxTipo.getSelectedItem());
				}
				contentPane.remove(sclUsuarios);
				levantarListadoUsuarios();
			}
		});
		btnBuscar.setBackground(new Color(0, 153, 204));
		btnBuscar.setBounds(480, 76, 84, 25);
		contentPane.add(btnBuscar);
		
		JButton btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Suprimir Usuario");
		btnExcluir.setIcon(new ImageIcon(ListAdmUsuario.class.getResource("/imagenes/ic_delete_black_24dp_2x.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Usuarios usuario = new Usuarios();
				if(usuario.excluir(usrSeleccionado)) {
					JOptionPane.showMessageDialog(null, "Usuario excluir con exito");
					contentPane.remove(sclUsuarios);
					levantarListadoUsuarios();
				} else {
					JOptionPane.showMessageDialog(null, "No fue posible excluir usuario");
				}
			}
		});
		btnExcluir.setBounds(73, 112, 55, 49);
		contentPane.add(btnExcluir);
		
		JButton btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(usrSeleccionado.equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione un usuario a editar!");
				} else {
					EdtAdmUsuario pantEdtUsuario = new EdtAdmUsuario(usrSeleccionado);
					pantEdtUsuario.setVisible(true);
				}
			}
		});
		btnEditar.setIcon(new ImageIcon(ListAdmUsuario.class.getResource("/imagenes/if_pencil_285638.png")));
		btnEditar.setToolTipText("Editar Usuario");
		btnEditar.setBounds(12, 112, 49, 49);
		contentPane.add(btnEditar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 40, 685, 2);
		contentPane.add(separator);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtNombre, txtLogin, cbxTipo, btnBuscar}));
	}
	
	public void levantarListadoUsuarios() {
		Usuarios usuarios = new Usuarios();
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setBounds(10, 31, 444, 340);
		table.setModel(usuarios.getTableModel(busqueda));
		
		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
		centerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		/** checkbox **/
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		
		/** Codigo del usuario **/
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRender);
		
		/** Nombre del usuario **/
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		
		/** Login **/
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setMaxWidth(80);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRender);
		
		/** Categoria **/
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setMaxWidth(200);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRender);
		
		/** Tomando los valores de los checkboxes **/
		table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
            	for (int i = 0; i < table.getModel().getRowCount(); i++) {
            		if((Boolean)table.getModel().getValueAt(i, 0)) {
            			usrSeleccionado = (String)table.getValueAt(table.getSelectedRow(), 1);
					}
				}
            }
		});
		sclUsuarios = new JScrollPane(table);
		sclUsuarios.setViewportBorder(UIManager.getBorder("ScrollPane.border"));
		sclUsuarios.setBounds(12, 174, 685, 351);
		contentPane.add(sclUsuarios);
	}
}
