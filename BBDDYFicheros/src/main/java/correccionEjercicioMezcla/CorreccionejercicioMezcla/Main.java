package correccionEjercicioMezcla.CorreccionejercicioMezcla;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;

public class Main {
	
	static ArrayList<Empleado> listaEmp = new ArrayList<Empleado>();
	static ArrayList<Departamento> listaDep = new ArrayList<Departamento>();

	public static void main(String[] args) throws IOException, ParserConfigurationException, ClassNotFoundException, SQLException{
	
			BufferedReader br = Files.newBufferedReader(Paths.get("C:\\PRUEBAS\\empresa.csv"));
			
			//leo las lineas y obtengo un Stream de Strings al leer las lineas
			Stream<String> lines = br.lines();
			lines.forEach(copia -> {
				String[] valoresEmpresa;//declaro este array para guardar los campos del fichero: codigo, nombre, departamento o empleado
				valoresEmpresa = copia.split(" ");
				//en valoresEmpresa de 0 me mete el codigo, en el campo de 1, el nombre, en el campos de 2 me dice si es departamento o empleado:
				if(valoresEmpresa[2].equals("1")) {
					//si valoresEmpresa[2].equals("1"), el "1" se refiere a empleados, si es "2", se refiere a departamentos:
					listaEmp.add(new Empleado(Integer.parseInt(valoresEmpresa[0]), valoresEmpresa[1]));
					System.out.println("Es un empleado.");
				}if(valoresEmpresa[2].equals("2")) {
					listaDep.add(new Departamento(Integer.parseInt(valoresEmpresa[0]), valoresEmpresa[1]));
					System.out.println("Es un departamento.");
				}
			});

		//genero el XML:
		Utilidades.generarXML(listaEmp, listaDep);
		
		//inserto valores en la BBDD:
		Utilidades.insertarBBDD(listaEmp, listaDep);
	}
	

}
