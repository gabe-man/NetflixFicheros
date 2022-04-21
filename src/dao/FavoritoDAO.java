package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Show;
import models.Usuario;

/**
 * DAO para acceder y modificar la tabla de favoritos de la base de datos
 * @author Gabriel
 *
 */
public class FavoritoDAO extends AbstractDAO{

	public FavoritoDAO() {
		
	}
	
	/**
	 * Permite sacar una lista de todos los shows favoritos de un usuario
	 * @param user Usuario del que deseamos sacar los shows favoritos
	 * @return Lista de shows favoritos del usuario
	 */
	public ArrayList<Show> getAll(Usuario user) {
		final String query = "select shows.show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description from shows"
							+ " inner join favoritos on shows.show_id = favoritos.show_id where favoritos.user_id = " + user.getId() + ";";
		
		ArrayList<Show> favoritos = new ArrayList<Show>();
		
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
				
				favoritos.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return favoritos;
	}
	
	/**
	 * Inserta en la tabla favoritos el id del usuario logeado en el momento junto al id del show favorito
	 * @param user usuario logeado
	 * @param show show seleccionado como favorito
	 */
	public void insert(Usuario user, Show show) {
		final String insert= "Insert into favoritos(user_id, show_id)\n"
							+ "values("+ user.getId() + ", '" + show.getShow_id() + "');";
		
		try {
			stmt.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra de la tabla de favoritos el registro correspondiente al show especificado del user logeado
	 * @param user user logeado
	 * @param show show especificado para eliminar
	 */
	public void delete(Usuario user, Show show) {
		final String delete = "Delete from favoritos where user_id = " + user.getId() + " and show_id= '" + show.getShow_id() + "';";
		try {
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Comprueba si un show se encuentra entre los favoritos del usuario logeado
	 * @param user user logeado
	 * @param show show a encontrar
	 * @return
	 */
	public boolean existeFavorito(Usuario user, Show show) {
		final String query = "select show_id from favoritos where user_id= " + user.getId() + " and show_id= '" + show.getShow_id() + "';";
		
		try {
			ResultSet rs = stmt.executeQuery(query);
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
