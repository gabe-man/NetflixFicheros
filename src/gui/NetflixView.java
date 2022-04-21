package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import models.Show;
import models.Usuario;
import java.awt.Color;
import javax.swing.JTextField;

import dao.FavoritoDAO;
import dao.ShowDAO;
import ficheros.LecturaFicheros;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * Ventana principal de la aplicación
 * @author Gabriel
 *
 */
public class NetflixView {

	private JFrame frame;
	private JTextField textBuscador;
	private JButton btnBuscar;
	private JComboBox comboBoxEleccion;
	private JScrollPane scrollPane;
	private JTextArea txtSeries;
	private JButton btnPrevio;
	private JButton btnSiguiente;
	private JButton btnAñadirFav;
	private JButton btnVolcar;
	private ArrayList<Show> listaSeries;
	private ShowDAO showdao;
	private int contador;
	private FavoritoDAO favdao;
	private ArrayList<Show> listaFavoritos;

	/**
	 * Crea la vista
	 */
	public NetflixView(Usuario user) {
		contador=0;
		listaSeries = new ArrayList<>();
		showdao = new ShowDAO();
		favdao = new FavoritoDAO();
		initialize();
		this.frame.setVisible(true);
		listener(user);
	}

	/**
	 * Inicializa los contenidos de la ventana
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 621, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(10, 10, 306, 40);
		frame.getContentPane().add(textBuscador);
		textBuscador.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnBuscar.setBounds(315, 10, 113, 40);
		frame.getContentPane().add(btnBuscar);
		
		comboBoxEleccion = new JComboBox();
		comboBoxEleccion.setFont(new Font("Tahoma", Font.BOLD, 20));
		comboBoxEleccion.setModel(new DefaultComboBoxModel(new String[] {"title", "country", "director", "release_year"}));
		comboBoxEleccion.setBounds(454, 10, 143, 40);
		frame.getContentPane().add(comboBoxEleccion);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(88, 100, 431, 265);
		frame.getContentPane().add(scrollPane);
		
		txtSeries = new JTextArea();
		txtSeries.setBackground(Color.WHITE);
		txtSeries.setEditable(false);
		scrollPane.setViewportView(txtSeries);
		
		btnPrevio = new JButton("<");
		btnPrevio.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPrevio.setBounds(10, 186, 68, 78);
		frame.getContentPane().add(btnPrevio);
		
		btnSiguiente = new JButton(">");
		btnSiguiente.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSiguiente.setBounds(529, 186, 68, 78);
		frame.getContentPane().add(btnSiguiente);
		
		btnAñadirFav = new JButton("A\u00F1adir a Favoritos");
		btnAñadirFav.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAñadirFav.setBounds(37, 387, 232, 46);
		frame.getContentPane().add(btnAñadirFav);
		
		btnVolcar = new JButton("Volcar a fichero");
		btnVolcar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnVolcar.setBounds(334, 387, 232, 46);
		frame.getContentPane().add(btnVolcar);
	}
	
	/**
	 * Configura las acciones a realizar
	 * @param user user logeado
	 */
	public void listener(Usuario user)
	{
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eleccion = (String) comboBoxEleccion.getSelectedItem();
				String busqueda = textBuscador.getText();
				
				if(!busqueda.isEmpty())
				{
					listaSeries = showdao.getShows(eleccion, busqueda);
					contador=0;
					
					if(listaSeries.isEmpty())
					{
						JOptionPane.showMessageDialog(btnBuscar, "No hay shows que coincidan con su busqueda");
					}
					else
					{
						mostrarBusqueda(user);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(btnBuscar, "Introduzca algo en el buscador");
				}
			}
		});
		
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contador==(listaSeries.size()-1))
				{
					contador=0;
				}
				else
				{
					contador++;
				}
				
				mostrarBusqueda(user);
			}
		});
		
		btnPrevio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contador==0)
				{
					contador=listaSeries.size()-1;
				}
				else
				{
					contador--;
				}
				
				mostrarBusqueda(user);
			}
		});
		
		btnAñadirFav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!favdao.existeFavorito(user, listaSeries.get(contador)))
				{
					favdao.insert(user, listaSeries.get(contador));
					txtSeries.setBackground(Color.GREEN);
				}
				else
				{
					favdao.delete(user, listaSeries.get(contador));
					txtSeries.setBackground(Color.WHITE);
				}
			}
		});
		
		btnVolcar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreFichero = JOptionPane.showInputDialog("¿Cual es el nombre del fichero?");
				File ficheroFav = new File(nombreFichero + String.valueOf(user.getId()) + ".csv");
				LecturaFicheros lector = new LecturaFicheros();
				if(ficheroFav.exists())
				{
					String separador = lector.sacaSeparador(ficheroFav);
					
					FileWriter fw;
					try {
						fw = new FileWriter(ficheroFav,true);
						listaFavoritos = favdao.getAll(user);
						
						for (int i = 0; i < listaFavoritos.size(); i++) {
							if(!lector.existeEnFichero(listaFavoritos.get(i), ficheroFav))
							{
								String show = crearLinea(listaFavoritos.get(i), separador);
								
								fw.write("\n"+show);
							}
						}
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					int separador = JOptionPane.showOptionDialog(btnVolcar, "¿Que separador desea?", "Separadores", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"coma", "punto y coma", "tabulador"}, "coma");
					
					String separadorSelec = "";
					
					switch(separador)
					{
					case 0:
						separadorSelec = ",";
						break;
					
					case 1:
						separadorSelec = ";";
						break;
						
					case 2:
						separadorSelec = "\t";
						break;
					}
					
					try {
						ficheroFav.createNewFile();
						FileWriter fw = new FileWriter(ficheroFav,true);
						fw.write("show_id"+ separadorSelec +"type"+separadorSelec+"title"+separadorSelec+"director"+separadorSelec+"cast"+separadorSelec+"country"+separadorSelec+"date_added"+separadorSelec+"release_year"+separadorSelec+"rating"+separadorSelec+"duration"+separadorSelec+"listed_in"+separadorSelec+"description\n");
						
						listaFavoritos = favdao.getAll(user);
						
						for (int i = 0; i < listaFavoritos.size(); i++) {
							if(!lector.existeEnFichero(listaFavoritos.get(i), ficheroFav))
							{
								String show = crearLinea(listaFavoritos.get(i), separadorSelec);
								
								fw.write("\n"+show);
							}
						}
						fw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * Muestra en el area de texto la informacion de cada serie encontrada
	 * @param user user logeado
	 */
	public void mostrarBusqueda(Usuario user)
	{
		
		String datos="";
		
		datos += "Titulo: " + listaSeries.get(contador).getTitle() + "\n";
		datos += "Categoria: " + listaSeries.get(contador).getType() + "\n";
		datos += "Director: " + listaSeries.get(contador).getDirector() + "\n";
		datos += "Casting: " + listaSeries.get(contador).getCast() + "\n";
		datos += "Pais: " + listaSeries.get(contador).getCountry() + "\n";
		datos += "Fecha Añadido: " + listaSeries.get(contador).getDate_added() + "\n";
		datos += "Calificacion: " + listaSeries.get(contador).getRating() + "\n";
		datos += "Duracion: " + listaSeries.get(contador).getDuration() + "\n";
		datos += "Listado en: " + listaSeries.get(contador).getListed_in() + "\n";
		datos += "Dascripcion: " + listaSeries.get(contador).getDescription() + "\n";
		
		txtSeries.setText(datos);
		
		if(favdao.existeFavorito(user, listaSeries.get(contador)))
		{
			txtSeries.setBackground(Color.GREEN);
		}
		else
		{
			txtSeries.setBackground(Color.WHITE);
		}
	}
	
	/**
	 * Crea la linea que se va a insertar en el fichero de favoritos
	 * @param show show a insertar en fichero
	 * @param separador separador de campos del fichero
	 * @return string a insertar en fichero
	 */
	public String crearLinea(Show show, String separador)
	{
		return show.getShow_id()+separador+show.getType()+separador+show.getTitle()+separador+show.getDirector()+separador+show.getCast()+separador+show.getCountry()+separador+show.getDate_added()+separador+show.getRelease_year()+separador+show.getRating()+separador+show.getDuration()+separador+show.getListed_in()+separador+show.getDescription();
	}
}
