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

import dao.UsuarioDAO;
import hash.Hash;
import models.Usuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

/**
 * Vista para cambiar la contraseña
 * @author Gabriel
 *
 */
public class ChangePassView {

	private JFrame frame;
	private JLabel lblLogoNetflix;
	private JLabel lblPass;
	private JLabel lblRepite;
	private JButton btnChange;
	private JPasswordField passwordField;
	private JPasswordField RepitepasswordField;
	private Hash hash;
	private UsuarioDAO userdao;

	/**
	 * Crea la ventana
	 */
	public ChangePassView(Usuario user) {
		hash = new Hash();
		userdao = new UsuarioDAO();
		initialize();
		this.frame.setVisible(true);
		listener(user);
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
		
		lblPass = new JLabel("Nueva Pass:");
		lblPass.setBackground(Color.GREEN);
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblPass.setForeground(Color.RED);
		lblPass.setBounds(48, 262, 170, 33);
		frame.getContentPane().add(lblPass);
		
		lblRepite = new JLabel("Repite:");
		lblRepite.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblRepite.setForeground(Color.RED);
		lblRepite.setBounds(48, 305, 170, 33);
		frame.getContentPane().add(lblRepite);
		
		btnChange = new JButton("Cambiar");
		btnChange.setForeground(Color.WHITE);
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnChange.setBackground(Color.BLACK);
		btnChange.setBounds(212, 428, 170, 33);
		frame.getContentPane().add(btnChange);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(263, 262, 296, 32);
		frame.getContentPane().add(passwordField);
		
		RepitepasswordField = new JPasswordField();
		RepitepasswordField.setBounds(263, 305, 296, 32);
		frame.getContentPane().add(RepitepasswordField);
		frame.setBounds(100, 100, 616, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Configura las acciones a realizar
	 * @param user user al que cambiar contraseña
	 */
	public void listener(Usuario user)
	{
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(String.valueOf(passwordField.getPassword()).equals(String.valueOf(RepitepasswordField.getPassword())))
				{
					user.setContraseña(hash.hash(String.valueOf(passwordField.getPassword()), "a"));
					userdao.update(user);
					JOptionPane.showMessageDialog(btnChange, "Contraseña reestablecida");
					frame.dispose();
					new LoginView();
				}
				else
				{
					JOptionPane.showMessageDialog(btnChange, "Las contraseñas no coinciden");
				}
			}
		});
	}
}
