package general;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import managers.*;

public class Principal {
	public static void main(String[] args) {
		ejecucion();
	}
	
	public static void ejecucion() {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿DESDE DONDE VAS A COGER LOS ARCHIVOS?\n1.Base de Datos SQL.\n2.Archivos\n3.Hibernate.");
		int opcion = sc.nextInt();
		Funcionalidad miAcceso;
		switch (opcion) {
		case 1:
			System.out.println("ENTENDIDO. BASE DE DATOS.");
			miAcceso = new BDManager();
			break;
		case 2:
			System.out.println("ENTENDIDO. ARCHIVOS.");
			File miArchivo = new File("C:\\Users\\User\\Desktop\\Workspace\\TareaADAT\\Archivo.txt");
			miAcceso = new FileManager(miArchivo);
			break;
		case 3:
			System.out.println("ENTENDIDO. HIBERNATE");
			miAcceso = new HibernateManager();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + opcion);
		}
		System.out
				.println("INDIQUE SU PETICION:\n1.Insertar:\n2.Borrar\n3.Buscar\n4.Modificar\n5.Migrar Tabla\n6.Ver Todo\n7.Finalizar");
		opcion = sc.nextInt();
		while (opcion != 7) {
			switch (opcion) {
			case 1:
				System.out.println("INDIQUE EL NOMBRE DE LA TABLA EN LA QUE QUIERE INSERTAR:");
				String tabla = sc.next();
				System.out.println("MUY BIEN, AHORA INTRODUZCA LOS DATOS DEL CAMPO A INSERTAR: ");
				String[] datos = new String[miAcceso.getNumColumnas(tabla)];
				for (int i = 0; i < datos.length - 1; i++) {
					datos[i] = sc.next();
				}
				System.out.println(miAcceso.insertar(tabla, datos) ? "REGISTRO INSERTADO CORRECTAMENTE"
						: "ERROR AL HACER EL REGISTRO");
				break;
			case 2:
				System.out.println("INDIQUE LA TABLA EN LA QUE QUIERE BORRAR EL DATO:");
				String tablaBorrar = sc.next();
				System.out.println("INDIQUE ID DEL CAMPO A BORRAR.");
				String idBorr = sc.next();
				System.out.println(miAcceso.borrar(tablaBorrar, idBorr) ? "REGISTRO BORRADO CORRECTAMENTE"
						: "NO SE HA PODIDO BORRAR EL REGISTRO");
				break;
			case 3:
				System.out.println("INDIQUE LA TABLA EN LA QUE QUIERE BUSCAR:");
				String tablaBuscar = sc.next();
				System.out.println("INDIQUE EL ID DEL ELEMENTO A BUSCAR:");
				String idBus = sc.next();
				Vuelo miVueloBuscar = miAcceso.buscar(tablaBuscar, idBus);
				System.out.println(miVueloBuscar.toString());
				break;
			case 4:
				System.out.println("INDIQUE LA TABLA EN LA QUE QUIERE MODIFICAR:");
				String tablaModificar = sc.next();
				System.out.println("INDIQUE EL ID DEL CAMPO A CAMBIAR");
				String idMod = sc.next();
				System.out.println("¿QUE CAMPO DESEA CAMBIAR?");
				String campo = sc.next();
				System.out.println("DEFINA EL NUEVO REGISTRO:");
				String nuevRegistro = sc.next();
				System.out.println(miAcceso.modificar(tablaModificar, idMod, campo, nuevRegistro)
						? "MODIFICACION REALIZADA CORRECTAMENTE"
						: "NO SE HA PODIDO MODIFICAR");
				break;
			case 5:
				System.out.println("INDIQUE LA TABLA A EMIGRAR:");
				String tablaEmigrar = sc.next();
				System.out.println("INDIQUE EL NOMBRE DEL ARCHIVO:");
				String nombreArchivo = sc.next();
				System.out.println(miAcceso.migrar(tablaEmigrar, nombreArchivo)? "COPIA DE SEGURIDAD EXITOSA" : "NO SE PUDO HACER LA COPIA DE SEGURIDAD");
				break;
			case 6:
				System.out.println("INDIQUE LA TABLA A VER:");
				String tablaVerTodo = sc.next();
				HashMap<Integer, Vuelo> misVuelos =  miAcceso.verTodo(tablaVerTodo);
				for (Entry<Integer, Vuelo> entry : misVuelos.entrySet()) {
					System.out.println(entry.getValue());
				}
				System.out.println();
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + opcion);
			}
			System.out.println(
					"INDIQUE SU PETICION:\n1.Insertar:\n2.Borrar\n3.Buscar\n4.Modificar\n5.Migrar Tabla\n6.Ver Todo\n7.Finalizar");
			opcion = sc.nextInt();
		}
		System.out.println("HASTA LUEGO!");
	}
}
