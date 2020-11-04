package managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import general.*;

public class BDManager implements Funcionalidad {
	private String db = "adat_vuelos";
	private String login = "root";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + db
			+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Connection conexion;

	public BDManager() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			System.out.println("-> Conexión con MySQL establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver JDBC No encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error al conectarse a la BD");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general de Conexión");
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Integer, Vuelo> verTodo(String tabla) {
		HashMap<Integer, Vuelo> misVuelos = new HashMap<>();
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rset = stmt.executeQuery("Select * from " + tabla);
			while (rset.next()) {
				Vuelo miVuelo = new Vuelo(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4),
						rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8));
				misVuelos.put(miVuelo.getId(), miVuelo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misVuelos;
	}

	@Override
	public boolean insertar(String tabla, String[] datos) {
		boolean exito = false;
		int resultado = 0;
		try {
			String queryAux = "SELECT * FROM " + tabla;
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(queryAux);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumnas = rsmd.getColumnCount();

			String query = "INSERT INTO " + tabla + "(";
			for (int i = 2; i <= numColumnas; i++) {
				String x = rsmd.getColumnName(i);// darNombreColumna(i, tabla);
				query += x;
				if (numColumnas - i != 0) {
					query += ", ";
				}
			}
			query += ") VALUES (";
			for (int i = 2; i <= numColumnas; i++) {
				query += "?";
				if (numColumnas - i != 0) {
					query += ", ";
				}
			}
			query += ");";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			for (int i = 1; i < numColumnas; i++) {
				pstmt.setString(i, datos[i]);
			}
			resultado = pstmt.executeUpdate();
			pstmt.close();
			exito = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return exito;
	}

	@Override
	public boolean borrar(String tabla, String id) {
		boolean exito = false;
		int resultado = 0;
		try {
			String query = "Delete from " + tabla + " where id = ?";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, id);
			resultado = pstmt.executeUpdate();
			pstmt.close();
			exito = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return exito;
	}

	@Override
	public boolean modificar(String tabla, String id, String campo, String nuevoRegistro) {
		int resultado = 0;
		boolean exito = false;
		try {
			String query = "UPDATE " + tabla + " SET " + campo + " = ? where  id = " + id;
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, nuevoRegistro);
			resultado = pstmt.executeUpdate();
			pstmt.close();
			exito = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return exito;
	}

	@Override
	public Vuelo buscar(String tabla, String id) {
		Vuelo miVuelo = null;
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rset = stmt.executeQuery("Select * from " + tabla + " where id = " + id);
			ResultSetMetaData rsmd = rset.getMetaData();
			int numColumnas = rsmd.getColumnCount();
			boolean aux = rset.next();
			if (!aux) {
				System.out.println("Registro no encontrado con el id: " + id);
			} else {
				miVuelo = new Vuelo(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4),
						rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8));
			}
			rset.close();
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
		}
		return miVuelo;
	}

	@Override
	public boolean migrar(String tabla, String nombreArchivo) {
		boolean exito = false;
		HashMap<Integer, Vuelo> tablaSeguridad = verTodo(tabla);
		String ruta = "..\\TareaADAT\\" + nombreArchivo + ".txt";
		File file = new File(ruta);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
			for (int i = 0; i < tablaSeguridad.size(); i++) {
				bw.write("\n" + tablaSeguridad.get(i));
			}
			bw.close();
			exito = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return exito;
	}

	@Override
	public int getNumColumnas(String tabla) {
		String queryAux = "SELECT * FROM " + tabla;
		Statement stmt;
		int numColumnas = 0;
		try {
			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(queryAux);
			ResultSetMetaData rsmd = rs.getMetaData();
			numColumnas = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numColumnas;
	}

}
