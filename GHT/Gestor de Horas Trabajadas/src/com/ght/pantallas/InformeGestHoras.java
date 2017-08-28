package com.ght.pantallas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.ght.classes.ControlMensual;
import com.ght.classes.HorasTrabajadas;
import com.ght.classes.Usuarios;

public class InformeGestHoras extends JFrame {

	/** objects Swing **/
	private JPanel contentPane;
	
	/** Atributos de la pantalla **/
	private static Usuarios usuario;
	private static String anoMes;
	
	/** Class objects **/
	private HorasTrabajadas horasTrabajadas;
	private static ControlMensual control = new ControlMensual();
	
	/** Formatación **/
	private NumberFormat formatter = new DecimalFormat("#0.00");    
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformeGestHoras frame = new InformeGestHoras(usuario, anoMes);
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
	public InformeGestHoras(final Usuarios usuario, final String anoMes) {
		horasTrabajadas = new HorasTrabajadas(usuario);
		horasTrabajadas.setAnoMes(anoMes);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInformeDeHoras = new JLabel("Informe de Horas");
		lblInformeDeHoras.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInformeDeHoras.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformeDeHoras.setBounds(12, 13, 738, 21);
		contentPane.add(lblInformeDeHoras);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 42, 738, 2);
		contentPane.add(separator);
		
		setTitle("GHT - Informe de horas del usuario");
		
		/**
		 * Label del error cuando no se puede cadastrar el usuário
		 */
		final JLabel lblError = new JLabel("\u00A1Error al guardar usuario!");
		lblError.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Error.gif")));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblError.setBounds(12, 51, 738, 35);
		lblError.setVisible(false);
		contentPane.add(lblError);

		/**
		 * Label del exito del registro
		 */
		final JLabel lblSuceso = new JLabel("\u00A1Usuario guardado con éxito");
		lblSuceso.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuceso.setForeground(new Color(0, 128, 128));
		lblSuceso.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSuceso.setIcon(new ImageIcon(IncAdmUsuario.class.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		lblSuceso.setBounds(12, 51, 738, 35);
		lblSuceso.setVisible(false);
		contentPane.add(lblSuceso);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(22, 99, 56, 16);
		contentPane.add(lblUsuario);
		
		final JLabel lblNombreUsuario = new JLabel(usuario.getNombre());
		lblNombreUsuario.setForeground(new Color(0, 128, 128));
		lblNombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreUsuario.setBounds(32, 128, 166, 16);
		contentPane.add(lblNombreUsuario);
		
		JLabel lblMontoAPagar = new JLabel("Monto a pagar");
		lblMontoAPagar.setHorizontalAlignment(SwingConstants.LEFT);
		lblMontoAPagar.setBounds(245, 99, 99, 16);
		contentPane.add(lblMontoAPagar);
		
		String monto = formatter.format(horasTrabajadas.calcularHonorarios(usuario.getValorHora()));
		JLabel lblMonto = new JLabel(monto);
		lblMonto.setHorizontalAlignment(SwingConstants.LEFT);
		lblMonto.setForeground(new Color(255, 0, 0));
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMonto.setBounds(245, 128, 89, 16);
		contentPane.add(lblMonto);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(384, 99, 99, 16);
		contentPane.add(lblEstado);
		
		final JLabel txtEstado = new JLabel(control.getEstado(usuario.getCodigo(), anoMes));
		if(control.getEstado(usuario.getCodigo(), anoMes).equals("Cerrado")) {
			txtEstado.setForeground(new Color(220, 20, 60));
		} else {
			txtEstado.setForeground(new Color(0, 128, 128));
		}
		txtEstado.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtEstado.setBounds(384, 128, 79, 16);
		contentPane.add(txtEstado);
		
		final JButton btnAprobar = new JButton("Aprobar Horas");
		btnAprobar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double monto = horasTrabajadas.calcularHonorarios(usuario.getValorHora());
				if (control.registrarHorasAprobadas(monto, usuario.getCodigo(), anoMes)) {
					JOptionPane.showMessageDialog(null, "Horas aprobadas!");
					txtEstado.setForeground(new Color(220, 20, 60));
					txtEstado.setText("Cerrado");
					btnAprobar.setEnabled(false);
				}
			}
		});
		if(control.getEstado(usuario.getCodigo(), anoMes).equals("Cerrado")) {
			btnAprobar.setEnabled(false);
		}
		btnAprobar.setBackground(new Color(0, 153, 204));
		btnAprobar.setBounds(538, 99, 125, 49);
		contentPane.add(btnAprobar);
		
		crearTablaMes(usuario, anoMes);
	}
	
	public void crearTablaMes(Usuarios usuario, String anoMes) {
		HorasTrabajadas horasTrab = new HorasTrabajadas(usuario);
		horasTrab.setAnoMes(anoMes);
		String[] columnas = {"Fecha", "Entrada", "Salida", "Hora Descanso", "Total Horas Del Día"};
		Object[][] horarios = horasTrab.getHorariosDelUsuario();

		JTable tablaHorarios = new JTable();
		tablaHorarios.setFillsViewportHeight(true);
		tablaHorarios.setBounds(10, 31, 444, 340);
		tablaHorarios.setModel(new DefaultTableModel(horarios, columnas));
		
		JScrollPane scrollPane = new JScrollPane(tablaHorarios);
		scrollPane.setBounds(12, 173, 738, 307);
		contentPane.add(scrollPane);
	}
}
