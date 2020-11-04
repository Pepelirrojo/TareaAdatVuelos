package general;

import java.util.HashMap;

public interface Funcionalidad {
	public HashMap<Integer, Vuelo> verTodo(String tabla);

	public boolean insertar(String tabla, String [] datos);

	public boolean borrar(String tabla, String id);

	public boolean modificar(String tabla, String id, String campo, String nuevoRegistro );

	public Vuelo buscar(String tabla, String id);
	
	public boolean migrar(String tabla, String nombreArchivo);
	
	public int getNumColumnas(String tabla);
}
