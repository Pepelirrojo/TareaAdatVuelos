package managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import general.Funcionalidad;
import general.Vuelo;

public class FileManager implements Funcionalidad {
	private File miFile;

	public FileManager(File miArchivo) {
		miFile = miArchivo;
	}

	@Override
	public HashMap<Integer, Vuelo> verTodo(String tabla) {
		HashMap<Integer, Vuelo> misVuelos = new HashMap<>();
		try {
			if (miFile.exists()) {
				BufferedReader lectura = new BufferedReader(new FileReader(miFile));
				String campo;
				while ((campo = lectura.readLine()) != null) {

					String[] atr = campo.split("/");
					Vuelo miVuelo = new Vuelo(Integer.parseInt(atr[0]), atr[1], atr[2], atr[3], atr[4], atr[5], atr[6],
							atr[7]);
					misVuelos.put(miVuelo.getId(), miVuelo);
				}
				
			} else {
				System.out.println("FICHERO NO ENCONTRADO");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return misVuelos;
	}

	@Override
	public boolean insertar(String tabla, String[] datos) {
		boolean funciona = false;
		try {
			if (!miFile.exists()) {
				miFile.createNewFile();
			}
			BufferedWriter fileWrite = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(miFile, true), "utf-8"));
			System.out.println("Introduzca el Id del campo a insertar:");
			Scanner sc = new Scanner(System.in);
			String campo = sc.next() + "/";
			for (int i = 1; i < datos.length; i++) {
				campo += datos[i];
				if (i != datos.length - 1) {
					campo += "/";
				}
			}
			fileWrite.write("\n" + campo);
			fileWrite.close();
			funciona = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return funciona;
	}

	@Override
	public boolean borrar(String tabla, String id) {
		boolean exito = false;
		try {
			File temp = File.createTempFile("File", ".txt", miFile.getParentFile());
			Vuelo datAux = buscar(tabla, id);
			String delete = datAux.getId() + "/" + datAux.getCodigo_vuelo() + "/" + datAux.getOrigen() + "/"
					+ datAux.getDestino() + "/" + datAux.getFecha() + "/" + datAux.getHora() + "/"
					+ datAux.getPlazas_totales() + "/" + datAux.getPlazas_disponibles();

			System.out.println(delete);
			BufferedReader leer = new BufferedReader(new InputStreamReader(new FileInputStream(miFile)));
			PrintWriter escribir = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp)));
			for (String linea; (linea = leer.readLine()) != null;) {
				linea = linea.replace(delete, "");
				escribir.println(linea);
			}
			leer.close();
			escribir.close();
			miFile.delete();
			temp.renameTo(miFile);
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public boolean modificar(String tabla, String id, String campo, String nuevoRegistro) {
		boolean exito = false;
		try {
			String oldCampo = "";
			BufferedReader leer = new BufferedReader(new FileReader(miFile));
			String linea = leer.readLine();
			while (linea != null) {
				oldCampo = oldCampo + linea;
				linea = leer.readLine();
			}
			String newCampo = oldCampo.replaceAll(campo, nuevoRegistro);
			FileWriter escribir = new FileWriter(miFile);
			escribir.write(newCampo);
			leer.close();
			escribir.close();
			exito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exito;
	}

	@Override
	public Vuelo buscar(String tabla, String id) {
		Vuelo datoASacar = null;
		try {
			FileReader fr = new FileReader(miFile);
			BufferedReader br = new BufferedReader(fr);
			String campoDiv;
			String[] codigo = null;
			while ((campoDiv = br.readLine()) != null) {
				codigo = campoDiv.split("/");
				if (id.equals(codigo[0])) {
					datoASacar = new Vuelo(Integer.parseInt(codigo[0]), codigo[1], codigo[2], codigo[3], codigo[4],
							codigo[5], codigo[6], codigo[7]);
				}
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datoASacar;
	}

	@Override
	public boolean migrar(String tabla, String nombreArchivo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumColumnas(String tabla) {
		// TODO Auto-generated method stub
		return 8;
	}

}
