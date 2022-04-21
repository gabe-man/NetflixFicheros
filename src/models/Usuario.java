package models;

public class Usuario {
	private int id;
	private String nombre;
	private String correo;
	private String contraseña;
	private String codigo;
	
	public Usuario()
	{
		id=0;
		nombre="";
		correo="";
		contraseña="";
		codigo="";
	}
	
	public Usuario(String nombre, String correo, String contraseña, String codigo) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contraseña = contraseña;
		this.codigo = codigo;
	}
	
	public Usuario(int id, String nombre, String correo, String contraseña, String codigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.contraseña = contraseña;
		this.codigo = codigo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	
	
	
	

}
