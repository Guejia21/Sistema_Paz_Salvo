package co.edu.unicauca.distribuidos.app_student.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.OperacionesStudentImpl;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.StudentStatusDTO;
import co.edu.unicauca.distribuidos.app_student.utilidades.UtilidadesConsola;

@Component
public class Menu {
    @Autowired
    private OperacionesStudentImpl objOperacionesStudent;

    public void mostrarMenu() {
        int option = 0;
        int id = pedirId();
        StudentStatusDTO objRespuesta;
        do {
            System.out.println("\n==========================Menu Estudiante===============================");
            System.out.println("1. Generar paz y salvo");
            System.out.println("2. Consultar deudas pendientes");
            System.out.println("3. Salir");
            option = UtilidadesConsola.leerEntero("Ingrese una opcion: ", 0, 4);
            switch (option) {
                case 1:
                    objRespuesta = objOperacionesStudent.generarPazYSalvo(id);
                    // System.out.println("Respuesta del servidor: " + objRespuesta.getMensaje());
                    // logica de la respuesta que llega
                    if (objRespuesta.isPazYSalvo()) {
                        System.out.println("Generando paz y salvo...");
                        System.out.println("Se ha generado el paz y salvo correctamente para el estudiante con id "
                                + objRespuesta.getIdEstudiante());

                    } else {
                        /*
                         * System.out.println(
                         * "Usted tiene deudas pendientes, para mas informacion consulta tus deudas en el menu."
                         * );
                         */
                        System.out.println(objRespuesta.getMensaje());
                    }
                    break;
                case 2:
                    objRespuesta = objOperacionesStudent.consultarDeudas(id);
                    // System.out.println("Respuesta del servidor: " + objRespuesta.getMensaje());
                    // logic
                    System.out.println("Listado de deudas");
                    if (objRespuesta.isEstadoDeportes()) {
                        System.out.println("Usted posee deudas en el area de deportes");
                    }
                    if (objRespuesta.isEstadoFinanciero()) {
                        System.out.println("Usted posee deudass en el area financiera");
                    }
                    if (objRespuesta.isEstadoLaboratorios()) {
                        System.out.println("Usted posee deudas en el area de laboratorios");
                    }
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
