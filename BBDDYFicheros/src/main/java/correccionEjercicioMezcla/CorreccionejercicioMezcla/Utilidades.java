package correccionEjercicioMezcla.CorreccionejercicioMezcla;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Utilidades {
	
	//hacer la BBDD en esta clase:
	
	public static void generarXML(ArrayList<Empleado> listaEmp, ArrayList<Departamento> listaDep) {
		
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factoria.newDocumentBuilder();
			Document documento = db.newDocument();
			
			Element empresa = documento.createElement("empresa");
			documento.appendChild(empresa);
			
			Element empleados = documento.createElement("empleados");
			empresa.appendChild(empleados);
			
			for(int i = 0; i < listaEmp.size(); i++) {
				Element empleado = documento.createElement("empleado");
				empleados.appendChild(empleado);
				
				Element codigo = documento.createElement("codigo");
				codigo.setTextContent(String.valueOf(listaEmp.get(i).getCodigo()));
				empleado.appendChild(codigo);
				
				Element nombre = documento.createElement("nombre");
				nombre.setTextContent(listaEmp.get(i).getNombre());
				empleado.appendChild(nombre);
			}
			
			Element departamentos = documento.createElement("departamentos");
			empresa.appendChild(departamentos);
			
			for(int i = 0; i < listaDep.size(); i++) {
				Element departamento = documento.createElement("departamento");
				departamentos.appendChild(departamento);
				
				Element codigo = documento.createElement("codigo");
				codigo.setTextContent(String.valueOf(listaDep.get(i).getCodigo()));
				departamento.appendChild(codigo);
				
				Element nombre = documento.createElement("nombre");
				nombre.setTextContent(listaDep.get(i).getNombre());
				departamento.appendChild(nombre);
			}
			
			TransformerFactory tf = TransformerFactory.newInstance();
			try {
				Transformer transformer = tf.newTransformer();
				
				DOMSource dom = new DOMSource(documento);
				StreamResult sr = new StreamResult(new File("C:\\EXAMEN\\FicheroEmpresa.xml"));
			
				try {
					transformer.transform(dom, sr);
					
					
				} catch (TransformerException e) {
					System.out.println("Error al crear el XML.");
				}
			} catch (TransformerConfigurationException e) {
				System.out.println("Error.");
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void insertarBBDD(ArrayList<Empleado> listaEmp, ArrayList<Departamento> listaDep) throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/empleados2", "root", "");
		PreparedStatement psEmp = conexion.prepareStatement("INSERT INTO empleados VALUES(?, ?)");
		
		for(int i = 0; i < listaEmp.size(); i++) {
			psEmp.setInt(1, listaEmp.get(i).getCodigo());
			psEmp.setString(2, listaEmp.get(i).getNombre());
			psEmp.executeUpdate();
		}
		
		PreparedStatement psDep = conexion.prepareStatement("INSERT INTO departamentos VALUES(?, ?)");
	
		for(int i = 0; i < listaDep.size(); i++) {
			psDep.setInt(1, listaDep.get(i).getCodigo());
			psDep.setString(2, listaDep.get(i).getNombre());
			psDep.executeUpdate();
		}
		
		psEmp.close();
		psDep.close();
		conexion.close();
	}
}
