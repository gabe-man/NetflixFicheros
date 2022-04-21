package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UsuarioDAO;
import email.Email;
import hash.Hash;
import models.Usuario;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JPasswordField;

/**
 * Vista de registros
 * @author Gabriel
 *
 */
public class RegisterView {

	private JFrame frame;
	private JLabel lblLogoNetflix;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JButton btnRegister;
	private JLabel lblRepite;
	private JLabel lblCorreo;
	private TextField txtUsuario;
	private TextField txtCorreo;
	private JPasswordField password;
	private JPasswordField Repite;

	/**
	 * Crea la vista
	 */
	public RegisterView() {
		initialize();
		this.frame.setVisible(true);
		listener();
	}

	/**
	 * Inicializa los contenidos de la ventana
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Gabriel\\Downloads\\Ficheros\\imagenes\\icono_netflix_nuevo.jpg"));
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		
		lblLogoNetflix = new JLabel("");
		lblLogoNetflix.setBounds(10, 10, 582, 175);
		lblLogoNetflix.setIcon(new ImageIcon("C:\\Users\\Gabriel\\Downloads\\Ficheros\\imagenes\\Netflix-Logo-Print_CMYK.jpg"));
		frame.getContentPane().add(lblLogoNetflix);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBackground(Color.GREEN);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblUsuario.setForeground(Color.RED);
		lblUsuario.setBounds(48, 262, 170, 33);
		frame.getContentPane().add(lblUsuario);
		
		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblContrasea.setForeground(Color.RED);
		lblContrasea.setBounds(48, 305, 170, 33);
		frame.getContentPane().add(lblContrasea);
		
		btnRegister = new JButton("Register");
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.BLACK);
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegister.setBounds(199, 428, 170, 33);
		frame.getContentPane().add(btnRegister);
		
		lblRepite = new JLabel("Repite:");
		lblRepite.setHorizontalAlignment(SwingConstants.LEFT);
		lblRepite.setForeground(Color.RED);
		lblRepite.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblRepite.setBounds(48, 348, 170, 33);
		frame.getContentPane().add(lblRepite);
		
		lblCorreo = new JLabel("Correo:");
		lblCorreo.setForeground(Color.RED);
		lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblCorreo.setBackground(Color.GREEN);
		lblCorreo.setBounds(48, 219, 170, 33);
		frame.getContentPane().add(lblCorreo);
		
		txtCorreo = new TextField();
		txtCorreo.setBounds(263, 219, 296, 33);
		frame.getContentPane().add(txtCorreo);
		
		txtUsuario = new TextField();
		txtUsuario.setBounds(263, 262, 296, 33);
		frame.getContentPane().add(txtUsuario);
		
		password = new JPasswordField();
		password.setBounds(263, 305, 296, 32);
		frame.getContentPane().add(password);
		
		Repite = new JPasswordField();
		Repite.setBounds(263, 349, 296, 32);
		frame.getContentPane().add(Repite);
		frame.setBounds(100, 100, 616, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Configura las acciones a realizar
	 */
	public void listener() {
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean existe = false;
				UsuarioDAO userdao=new UsuarioDAO();
				Hash hashea = new Hash();
				ArrayList<Usuario> listausuarios = userdao.getAll();
				for (int i = 0; i < listausuarios.size(); i++) {
					if(listausuarios.get(i).getNombre().equals(txtUsuario.getText())||listausuarios.get(i).getCorreo().equals(txtCorreo.getText()))
					{
						existe=true;
						break;
					}
				}
				if(txtCorreo.getText().isEmpty()||password.getText().isEmpty()||Repite.getText().isEmpty()||txtUsuario.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(btnRegister, "Rellene todos los campos");
				}
				else if (!String.valueOf(Repite.getPassword()).equals(String.valueOf(password.getPassword())))
				{
					JOptionPane.showMessageDialog(btnRegister, "Las contraseñas no coinciden");
				}
				else if (existe)
				{
					
					JOptionPane.showMessageDialog(btnRegister, "El usuario o contraseña ya existe");
				}
				else
				{
					String codigo=Integer.toString((int)(Math.random()*100000));
					Email correo = new Email();
					correo.Send(txtCorreo.getText(), codigo);
					String codigoIntroducido=JOptionPane.showInputDialog("Introduzca el codigo enviado");
					
					if(codigoIntroducido.equals(codigo))
					{
						String contaseña=hashea.hash(String.valueOf(password.getPassword()), "a");
						Usuario user = new Usuario(txtUsuario.getText(), txtCorreo.getText(), contaseña, codigo);
						userdao.insert(user);
						JOptionPane.showMessageDialog(btnRegister, "Registro completado");
						frame.dispose();
						LoginView login=new LoginView();
						
					}
					else
					{
						JOptionPane.showMessageDialog(btnRegister, "Los códigos no coinciden");
					}
				}
			}
		});
	}
}
