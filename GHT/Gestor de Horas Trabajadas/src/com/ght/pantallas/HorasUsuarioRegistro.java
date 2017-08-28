package com.ght.pantallas;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.ght.classes.Calendario;
import com.ght.classes.ControlMensual;
import com.ght.classes.HorasTrabajadas;
import com.ght.classes.Usuarios;

public class HorasUsuarioRegistro extends JFrame {

	/**
	 * Objetos estaticos
	 */
	final Calendario calendario = new Calendario();
	static Usuarios usuario;
	
	/**
	 * campos
	 */
	private final JFormattedTextField txtFecha;
	private final JFormattedTextField txtEntrada;
	private final JFormattedTextField txtSalida;
	private final JFormattedTextField txtHorasDescanso;
	private final JFormattedTextField txtHorasTrabajadas;

	/**
	 * Componentes
	 */
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JTable tablaHorarios;
	
	/**
	 * Formataciòn de fechas
	 */
	private DateFormat formatDate = new SimpleDateFormat("yyyy-mm-dd");
	private DateFormat formatHora = new SimpleDateFormat("HH:mm");
	
	/**
	 * Boton
	 */
	private JButton btnRegistrar;
	
	/**
	 * Etiqueta
	 */
	public JLabel lblTotalHoras;
	public JLabel lblSueldo;
	public JLabel lblValorHoras;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HorasUsuarioRegistro frame = new HorasUsuarioRegistro(usuario);
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
	public HorasUsuarioRegistro(final Usuarios usuario) {
		/** mudar antes de cerrar */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 779, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("GHT - Registro de horas");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*************************************************************************
		 * Etiquetas de la pantalla                                              *
		 *************************************************************************/
		JLabel lblRegistroDeHoras = new JLabel("Registro de Horas Trabajadas");
		lblRegistroDeHoras.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroDeHoras.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRegistroDeHoras.setBounds(12, 13, 738, 20);
		contentPane.add(lblRegistroDeHoras);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 35, 738, 9);
		contentPane.add(separator);
		
		final JLabel lblMes = new JLabel(calendario.getNombreMes());
		lblMes.setForeground(new Color(0, 128, 128));
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMes.setBounds(491, 87, 148, 20);
		contentPane.add(lblMes);
		
		JLabel lblHorasTrabajadas = new JLabel("Horas Trabajadas");
		lblHorasTrabajadas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHorasTrabajadas.setBounds(113, 58, 109, 16);
		contentPane.add(lblHorasTrabajadas);
		
		lblTotalHoras = new JLabel("0");
		lblTotalHoras.setBounds(234, 58, 163, 16);
		contentPane.add(lblTotalHoras);
		
		JLabel lblMontoSueldo = new JLabel("Valor Hora");
		lblMontoSueldo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoSueldo.setBounds(113, 87, 109, 16);
		contentPane.add(lblMontoSueldo);
		
		lblValorHoras = new JLabel();
		lblValorHoras.setText(String.valueOf(usuario.getValorHora()));
		lblValorHoras.setForeground(new Color(0, 128, 128));
		lblValorHoras.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblValorHoras.setBounds(234, 87, 163, 16);
		contentPane.add(lblValorHoras);
		
		JLabel lblSueldoTotal = new JLabel("Sueldo Total");
		lblSueldoTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSueldoTotal.setBounds(370, 57, 109, 16);
		contentPane.add(lblSueldoTotal);
		
		lblSueldo = new JLabel("0");
		lblSueldo.setBounds(491, 58, 148, 16);
		contentPane.add(lblSueldo);
		
		JLabel lblMesActual = new JLabel("Mes Actual");
		lblMesActual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMesActual.setBounds(370, 87, 109, 16);
		contentPane.add(lblMesActual);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setBounds(13, 154, 36, 14);
		contentPane.add(lblFecha);
		
		JLabel lblEntrada = new JLabel("Entrada");
		lblEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEntrada.setBounds(195, 153, 46, 14);
		contentPane.add(lblEntrada);
		
		JLabel lblSalida = new JLabel("Salida");
		lblSalida.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSalida.setBounds(195, 180, 46, 14);
		contentPane.add(lblSalida);
		
		JLabel lblHoraDescanso = new JLabel("Hora Descanso");
		lblHoraDescanso.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHoraDescanso.setBounds(401, 152, 88, 14);
		contentPane.add(lblHoraDescanso);
		
		JLabel lblTotalHorasTrabajadas = new JLabel("Total Horas Trabajadas");
		lblTotalHorasTrabajadas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalHorasTrabajadas.setBounds(354, 180, 135, 14);
		contentPane.add(lblTotalHorasTrabajadas);
		
		/*************************************************************************
		 * Campos del formulario de registro de horas                            *
		 *************************************************************************/
		txtFecha = new JFormattedTextField(formatDate);
		txtFecha.setBounds(59, 151, 86, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		txtEntrada = new JFormattedTextField(formatHora);
		txtEntrada.setBounds(254, 150, 86, 20);
		txtEntrada.setColumns(10);
		contentPane.add(txtEntrada);
		
		txtSalida = new JFormattedTextField(formatHora);
		txtSalida.setBounds(254, 177, 86, 20);
		txtSalida.setColumns(10);
		contentPane.add(txtSalida);
		txtSalida.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// al hacer click en el campo hora de salida
				// el sistema estima la hora de salida
				Date hora = null;
				try {
					hora = new SimpleDateFormat("HH:mm").parse(txtEntrada.getText());
					calendario.setTime(hora);
					calendario.add(calendario.HOUR_OF_DAY, 9);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txtSalida.setValue(calendario.getTime());
			}
		});
		
		txtHorasDescanso = new JFormattedTextField(formatHora);
		txtHorasDescanso.setBounds(501, 150, 86, 20);
		contentPane.add(txtHorasDescanso);
		txtHorasDescanso.setColumns(10);
		
		txtHorasTrabajadas = new JFormattedTextField(formatHora);
		txtHorasTrabajadas.setColumns(10);
		txtHorasTrabajadas.setEditable(false);
		txtHorasTrabajadas.setBounds(501, 177, 86, 20);
		contentPane.add(txtHorasTrabajadas);
		txtHorasTrabajadas.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				String totalHoras = "";
				if (!txtHorasDescanso.getText().equals("")) {
					totalHoras = calendario.getSuprimeHoras(txtEntrada.getText(), txtSalida.getText());
					totalHoras = calendario.getSuprimeHoras(txtHorasDescanso.getText(), totalHoras);
					txtHorasTrabajadas.setText(totalHoras);
				}
			}
		});
		
		/** Botones **/
		JButton btnHoy = new JButton("");
		btnHoy.setIcon(new ImageIcon(HorasUsuarioRegistro.class.getResource("/imagenes/calendar.png")));
		btnHoy.setOpaque(false);
		btnHoy.setContentAreaFilled(false);
		btnHoy.setBorderPainted(false);
		btnHoy.setBorder(null);
		btnHoy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtFecha.setText(calendario.getFechaActual());
			}
		});
		btnHoy.setBounds(153, 150, 40, 33);
		contentPane.add(btnHoy);
		
		btnRegistrar = new JButton("");
		btnRegistrar.setToolTipText("Registrar horario");
		btnRegistrar.setIcon(new ImageIcon(HorasUsuarioRegistro.class.getResource("/imagenes/ic_get_app_black_24dp_2x.png")));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fecha     = txtFecha.getText();
				String entrada   = txtEntrada.getText();
				String salida    = txtSalida.getText();
				String horasTrab = txtHorasTrabajadas.getText();
				String descanso  = txtHorasDescanso.getText();
				HorasTrabajadas hTrab = new HorasTrabajadas(usuario);
				ControlMensual control = new ControlMensual();
				String estado = control.getEstado(usuario.getCodigo(), hTrab.calendario.getMes());
				if(estado.equals("Abierto")) {
					if (!fecha.equals("") && !entrada.equals("") && !salida.equals("") && !horasTrab.equals("") && !descanso.equals("")) {
						if (hTrab.registrar(fecha, entrada, salida, horasTrab, descanso)) {
							JOptionPane.showMessageDialog(null, "Exito! Horario registrado!");
							contentPane.remove(scrollPane);
							crearTablaMes(usuario);
							llenarCampos(usuario);
						} else {
							JOptionPane.showMessageDialog(null, "Error! Horario no registrado!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Hay que llenar todo los campos del formulario");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Registro de horas inactivo: Mes cerrado!");
				}
			}
		});
		btnRegistrar.setBounds(610, 144, 54, 50);
		contentPane.add(btnRegistrar);
		
		crearTablaMes(usuario);
		// llenar campos
		llenarCampos(usuario);
	}
	
	public void crearTablaMes(Usuarios usuario) {
		/**
		 * formato de fechas y horas
		 */
		HorasTrabajadas horasTrab = new HorasTrabajadas(usuario);
		String[] columnas = {"Fecha", "Entrada", "Salida", "Hora Descanso", "Total Horas Del Día"};
		Object[][] horarios = horasTrab.getHorariosDelUsuario();

		tablaHorarios = new JTable(){
			private static final long serialVersionUID = 1L;
			// impedir que sea editable
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		tablaHorarios.setFillsViewportHeight(true);
		tablaHorarios.setBounds(10, 31, 444, 340);
		tablaHorarios.setModel(new DefaultTableModel(horarios, columnas));
		
		scrollPane = new JScrollPane(tablaHorarios);
		scrollPane.setBounds(12, 223, 738, 307);
		contentPane.add(scrollPane);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setBounds(370, 116, 109, 16);
		contentPane.add(lblEstado);
		
		ControlMensual control = new ControlMensual();
		String estado = control.getEstado(usuario.getCodigo(), horasTrab.calendario.getMes());
		JLabel lblEstadoMes = new JLabel(estado);
		if(estado.equals("Abierto")){
			lblEstadoMes.setForeground(new Color(0, 128, 128));
		} else {
			lblEstadoMes.setForeground(new Color(220, 20, 60));
		}
		lblEstadoMes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEstadoMes.setBounds(491, 111, 148, 20);
		contentPane.add(lblEstadoMes);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtFecha, txtEntrada, txtSalida, txtHorasDescanso}));
	}
	
	public void llenarCampos(Usuarios usuario) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:MM");
		HorasTrabajadas horasTrab = new HorasTrabajadas(usuario);
		String total = horasTrab.getTotalHoras(calendario.getMes());
		lblTotalHoras.setText(total);
		lblSueldo.setText(String.valueOf(horasTrab.calcularHonorarios(usuario.getValorHora())));
	}
}
