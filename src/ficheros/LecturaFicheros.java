package ficheros;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import models.Show;

/**
 * Clase que controla todo lo relacionado con la lectura de los ficheros
 * @author Gabriel
 *
 */
public class LecturaFicheros {
	
	public LecturaFicheros()
	{
		
	}
	
	/**
	 * Comprueba si existe un show en el fichero seleccionado
	 * @param show show a buscar
	 * @param file fichero donde buscar
	 * @return true si existe false si no existe
	 */
	public boolean existeEnFichero(Show show, File file)
	{
		Scanner sc = null;
		
		try {
			sc = new Scanner(file, "UTF-8");
			while(sc.hasNextLine())
			{
				String registro = sc.nextLine();
				
				var campos = registro.split(",|;|\t");
				
				if(show.getShow_id().equals(campos[0]))
				{
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
		
		return false;
	}
	
	/**
	 * Permite sacar el separador seleccionado con anterioridad en un fichero ya existente
	 * @param file fichero al que sacar separador
	 * @return separador del fichero
	 */
	public String sacaSeparador(File file)
	{
		Scanner sc = null;
		
		try {
			sc = new Scanner(file, "UTF-8");
			
			String linea = sc.nextLine();
			String[] arrayLinea = linea.split("");
			String separador = arrayLinea[7];
			return separador;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
