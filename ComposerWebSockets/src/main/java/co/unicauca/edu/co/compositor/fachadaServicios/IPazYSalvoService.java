package co.unicauca.edu.co.compositor.fachadaServicios;

import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;

public interface IPazYSalvoService {
    StudentStatusDTO verificarEstado(String studentCode);
    StudentStatusDTO consultarDeudas(String idEstudiante);
}