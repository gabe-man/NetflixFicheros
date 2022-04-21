package gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import dao.UsuarioDAO;
import email.Email;
import hash.Hash;
import models.Usuario;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

/**
 * Vista para Logearse
 * @author Gabriel
 *
 */
public class LoginView {

	private JFrame frame;
	private JLabel lblLogoNetflix;
	private JTextField textUsuario;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JButton btnLogin;
	private JButton btnRegister;
	private JButton btnForgotPass;
	private UsuarioDAO userdao;
	private ArrayList<Usuario> listaUsers;
	private Email email;
	private Hash hasheo;
	private JPasswordField password;
	
	/**
	 * Crea la vista
	 */
	public LoginView() {
		userdao = new UsuarioDAO();
		listaUsers=userdao.getAll();
		email = new Email();
		hasheo = new Hash();
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
		
		textUsuario = new JTextField();
		textUsuario.setColumns(10);
		textUsuario.setBounds(263, 263, 296, 32);
		frame.getContentPane().add(textUsuario);
		
		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setBackground(Color.BLACK);
		btnLogin.setBounds(84, 385, 170, 33);
		frame.getContentPane().add(btnLogin);
		
		btnRegister = new JButton("Register");
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.BLACK);
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegister.setBounds(348, 385, 170, 33);
		frame.getContentPane().add(btnRegister);
		
		btnForgotPass = new JButton("Forgot Pass?");
		btnForgotPass.setForeground(Color.WHITE);
		btnForgotPass.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnForgotPass.setBackground(Color.BLACK);
		btnForgotPass.setBounds(212, 428, 170, 33);
		frame.getContentPane().add(btnForgotPass);
		
		password = new JPasswordField();
		password.setBounds(263, 305, 296, 32);
		frame.getContentPane().add(password);
		frame.setBounds(100, 100, 616, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Configura las acciones a realizar
	 */
	private void listener()
	{
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterView window = new RegisterView();
				frame.dispose();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean existe = false;
				Usuario user = new Usuario();
				for (int i = 0; i < listaUsers.size(); i++) {
					if(listaUsers.get(i).getNombre().equals(textUsuario.getText()) && listaUsers.get(i).getContraseña().equals(hasheo.hash(String.valueOf(password.getPassword()), "a")))
					{
						existe = true;
						user = listaUsers.get(i);
						break;
					}
				}
				
				if(!existe)
				{
					JOptionPane.showMessageDialog(btnLogin, "El usuario o contraseña no es correcto");
				}
				else
				{
					NetflixView window = new NetflixView(user);
					frame.dispose();
				}
			}
		});
		
		btnForgotPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigo=Integer.toString((int)(Math.random()*100000));
				String correo = JOptionPane.showInputDialog("Introduzca su correo electrónico");
				Usuario user = new Usuario();
				for (int i = 0; i < listaUsers.size(); i++) {
					if(listaUsers.get(i).getCorreo().equals(correo))
					{
						user = new Usuario(listaUsers.get(i).getId(), listaUsers.get(i).getNombre(), listaUsers.get(i).getCorreo(), listaUsers.get(i).getContraseña(), codigo);
						userdao.update(user);
						email.Send(correo, codigo);
						break;
					}
				}
				
				String codigoIntroducido = JOptionPane.showInputDialog("Introduce el codigo enviado");
				
				if(codigo.equals(codigoIntroducido))
				{
					ChangePassView window = new ChangePassView(user);
				}
				else
				{
					JOptionPane.showMessageDialog(btnForgotPass, "Los códigos no coinciden");
				}
				
			}
		});
	}
}
