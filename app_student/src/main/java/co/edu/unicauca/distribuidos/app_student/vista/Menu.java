package co.edu.unicauca.distribuidos.app_student.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.distribuidos.app_student.servicios.modelos.OperacionesStudentImpl;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.peticion.PeticionPazSalvoDTO;
import co.edu.unicauca.distribuidos.app_student.servicios.modelos.respuesta.RespuestaPazSalvoConsultadoDTO;
import co.edu.unicauca.distribuidos.app_student.utilidades.UtilidadesConsola;

@Component
public class Menu {
    @Autowired
    private OperacionesStudentImpl objOperacionesStudent;

    public void mostrarMenu() {
        int option = 0;
        int id = pedirId();
        String nombre = pedirNombre();
        PeticionPazSalvoDTO objPeticion = new PeticionPazSalvoDTO(id, nombre);
        RespuestaPazSalvoConsultadoDTO objRespuesta;
        do {
            System.out.println("\n==========================Menu Estudiante===============================");
            System.out.println("1. Generar paz y salvo (forma sincrona)");
            System.out.println("2. Generar paz y salvo (forma asincrona)");
            System.out.println("3. Salir");
            option = UtilidadesConsola.leerEntero("Ingrese una opcion: ", 0, 4);
            switch (option) {
                case 1:
                    objRespuesta = objOperacionesStudent.orquestarServiciosSincronicamente(objPeticion);
                    // Verifica si hubo un error por fallo simulado o real
                    if (objRespuesta.getMensaje() != null && objRespuesta.getMensaje().contains("Error al procesar")) {
                        System.out.println(objRespuesta.getMensaje());
                    } else if (objRespuesta.isPazSalvo()) {
                        System.out.println("Consultado deudas...");
                        System.out.println("Usted no tiene ninguna deuda");
                        System.out.println("Paz y salvo generado con exito!");
                    } else {
                        System.out.println("Listado de deudas");
                        if (!objRespuesta.getObjAreaDeportes().isPazYSalvo()) {
                            System.out.println("Deudas en area de deportes:");
                            objRespuesta.getObjAreaDeportes().getDeudas().forEach(System.out::println);
                        }
                        if (!objRespuesta.getObjAreaFinanciera().isPazYSalvo()) {
                            System.out.println("Deudas en area financiera:");
                            objRespuesta.getObjAreaFinanciera().getDeudas().forEach(System.out::println);
                        }
                        if (!objRespuesta.getObjAreaLaboratorios().isPazYSalvo()) {
                            System.out.println("Deudas en area laboratorios:");
                            objRespuesta.getObjAreaLaboratorios().getDeudas().forEach(System.out::println);
                        }
                    }
                    break;
                case 2:
                    objRespuesta = objOperacionesStudent.orquestarServiciosAsincronicamente(objPeticion);
                    if (objRespuesta.getMensaje() != null && objRespuesta.getMensaje().contains("Error al procesar")) {
                        System.out.println(objRespuesta.getMensaje());
                    } else if (objRespuesta.isPazSalvo()) {
                        System.out.println("Consultado deudas...");
                        System.out.println("Usted no tiene ninguna deuda");
                        System.out.println("Paz y salvo generado con exito!");
                    } else {
                        System.out.println("Listado de deudas");
                        if (!objRespuesta.getObjAreaDeportes().isPazYSalvo()) {
                            System.out.println("Deudas en area de deportes:");
                            objRespuesta.getObjAreaDeportes().getDeudas().forEach(System.out::println);
                        }
                        if (!objRespuesta.getObjAreaFinanciera().isPazYSalvo()) {
                            System.out.println("Deudas en area financiera:");
                            objRespuesta.getObjAreaFinanciera().getDeudas().forEach(System.out::println);
                        }
                        if (!objRespuesta.getObjAreaLaboratorios().isPazYSalvo()) {
                            System.out.println("Deudas en area laboratorios:");
                            objRespuesta.getObjAreaLaboratorios().getDeudas().forEach(System.out::println);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
            }
        } while (option != 3);
    }

    public int pedirId() {
        return UtilidadesConsola.leerEntero("Ingrese tu id: ", 1001, 1007);
    }

    public String pedirNombre() {
        return UtilidadesConsola.leerCadena("Ingresa tu nombre: ");
    }
}
