package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Show;

/**
 * DAO para acceder y modificar la tabla de shows
 * @author Gabriel
 *
 */
public class ShowDAO extends AbstractDAO{
	
	public ShowDAO()
	{
		
	}
	
	/**
	 * Permite saber el primer show de la tabla
	 * @return El primer show de la tabla shows
	 */
	public Show first() {
		final String query = "select show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description from shows";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String show_id = rs.getString("show_id");
				String type = rs.getString("type");
				String title = rs.getString("title");
				String director = rs.getString("director");
				String cast = rs.getString("cast");
				String country = rs.getString("country");
				String date_added = rs.getString("date_added");
				String release_year = rs.getString("release_year");
				String rating = rs.getString("rating");
				String duration = rs.getString("duration");
				String listed_in = rs.getString("listed_in");
				String description = rs.getString("description");
				Show s = new Show(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description);
				return s;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Permite conseguir todos los shows de la base de datos
	 * @return Una lista de todos los shows
	 */
	public ArrayList<Show> getAll() {
		final String query = "select show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description from shows";
		ArrayList<Show> listaShows= new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				String show_id = rs.getString("show_id");
				String type = rs.getString("type");
				String title = rs.getString("title");
				String director = rs.getString("director");
				String cast = rs.getString("cast");
				String country = rs.getString("country");
				String date_added = rs.getString("date_added");
				String release_year = rs.getString("release_year");
				String rating = rs.getString("rating");
				String duration = rs.getString("duration");
				String listed_in = rs.getString("listed_in");
				String description = rs.getString("description");
				Show s = new Show(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description);
				listaShows.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaShows;
	}
	
	/**
	 * Inserta un nuevo show en la base de datos
	 * @param s show a insertar
	 */
	public void insert(Show s) {
		final String insert = "Insert into shows(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description)\n"
				              +"Values('"+s.getShow_id()+"','"+s.getType()+"','"+s.getTitle()+"','"+s.getDirector()+"','"+s.getCast()+"','"+s.getCountry()+"','"
				              +s.getDate_added()+"','"+s.getRelease_year()+"','"+s.getRating()+"','"+s.getDuration()+"','"+s.getListed_in()+"','"+s.getDescription()+"');";
		
		try {
			stmt.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra un show de la base de datos
	 * @param s show a borrar
	 */
	public void delete(Show s) {
		final String delete = "Delete from shows where show_id = '" + s.getShow_id();
		try {
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifica un show de la base de datos
	 * @param s show a modificar
	 */
	public void update(Show s) {
		final String update = "Update shows\n"+"set\n"+"show_id='"+s.getShow_id()+"',\n"
							  +"type='"+s.getType()+"',\n"+"title='"+s.getTitle()+"',\n"
							  +"director='"+s.getDirector()+"',\n"+"cast='"+s.getCast()+"',\n"
							  +"country='"+s.getCountry()+"',\n"+"date_added='"+s.getDate_added()+"',\n"
							  +"release_year='"+s.getRelease_year()+"',\n"+"rating='"+s.getRating()+"',\n"
							  +"duration='"+s.getDuration()+"',\n"+"listed_in='"+s.getListed_in()+"',\n"
							  +"description='"+s.getDescription()+"'\n"
							  +"where show_id ='"+s.getShow_id()+";";
		
		try {
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	
	/**
	 * Permite obtener los shows que coincidan con los parametros de busqueda
	 * @param campo categoria por la que se buscan los shows
	 * @param busqueda string a buscar en la base de datos
	 * @return Lista de shows que coinciden con la busqueda
	 */
	public ArrayList<Show> getShows(String campo, String busqueda)
	{
		final String query = "select show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description from shows where " + campo + " like '%"+busqueda+"%';";
		ArrayList<Show> listaShows= new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				String show_id = rs.getString("show_id");
				String type = rs.getString("type");
				String title = rs.getString("title");
				String director = rs.getString("director");
				String cast = rs.getString("cast");
				String country = rs.getString("country");
				String date_added = rs.getString("date_added");
				String release_year = rs.getString("release_year");
				String rating = rs.getString("rating");
				String duration = rs.getString("duration");
				String listed_in = rs.getString("listed_in");
				String description = rs.getString("description");
				Show s = new Show(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description);
				listaShows.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaShows;
	}
}
