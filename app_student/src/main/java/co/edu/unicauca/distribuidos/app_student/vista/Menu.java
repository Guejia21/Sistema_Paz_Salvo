package co.edu.unicauca.distribuidos.app_student.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.OperacionesStudentImpl;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaSolicitudDTO;
import co.edu.unicauca.distribuidos.app_student.utilidades.UtilidadesConsola;

@Component
public class Menu {
    @Autowired
    private OperacionesStudentImpl objOperacionesStudent;

    public void mostrarMenu() {
        int option = 0;
        int id = pedirId();
        RespuestaSolicitudDTO objRespuesta;
        do {
            System.out.println("\n==========================Menu Estudiante===============================");
            System.out.println("1. Generar paz y salvo");
            System.out.println("2. Consultar deudas pendientes");
            System.out.println("3. Salir");
            option = UtilidadesConsola.leerEntero("Ingrese una opcion: ", 0, 4);
            switch (option) {
                case 1:
                    objRespuesta = objOperacionesStudent.generarPazYSalvo(id);
                    System.out.println("Respuesta del servidor: " + objRespuesta.getMensaje());
                    break;
                case 2:
                    objRespuesta = objOperacionesStudent.consultarDeudas(id);
                    System.out.println("Respuesta del servidor: " + objRespuesta.getMensaje());
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (option != 3);
    }

    public Integer pedirId() {
        return UtilidadesConsola.leerEntero("Ingrese tu id para poder identificarte: ", 0, 10);
    }
}
