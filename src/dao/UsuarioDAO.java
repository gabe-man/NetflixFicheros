package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Show;
import models.Usuario;

/**
 * DAO que permite acceder a la tabla de users
 * @author Gabriel
 *
 */
public class UsuarioDAO extends AbstractDAO{
	public UsuarioDAO()
	{
		
	}
	
	/**
	 * Permite obtener todos los usuarios de la base de datos
	 * @return Lista de users
	 */
	public ArrayList<Usuario> getAll() {
		final String query = "select id, nombre, correo, contrase�a, codigo from users";
		ArrayList<Usuario> listaUsers= new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String correo = rs.getString("correo");
				String contrase�a = rs.getString("contrase�a");
				String codigo = rs.getString("codigo");
				Usuario s = new Usuario(id, nombre, correo, contrase�a, codigo);
				listaUsers.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsers;
	}
	
	/**
	 * Permite insertar un user en la bd
	 * @param s usuario a insertar
	 */
	public void insert(Usuario s) {
		final String insert = "Insert into users(nombre, correo, contrase�a, codigo)\n"
				              +"Values('"+s.getNombre()+"','"+s.getCorreo()+"','"+s.getContrase�a()+"','"+s.getCodigo()+"');";
		
		try {
			stmt.executeUpdate(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permite borrar un user de la bd
	 * @param s user a borrar
	 */
	public void delete(Usuario s) {
		final String delete = "Delete from users where id = " + s.getId();
		try {
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permite modificar un user de la bd
	 * @param s user a modificar
	 */
	public void update(Usuario s) {
		final String update = "Update users\n"+"set\n"+"nombre='"+s.getNombre()+"',\n"
							  +"correo='"+s.getCorreo()+"',\n"
							  +"contrase�a='"+s.getContrase�a()+"',\n"
							  +"codigo='"+s.getCodigo()+"'\n"
							  +"WHERE id= "+s.getId()+";";
		
		try {
			stmt.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	
	
}
