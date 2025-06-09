package co.unicauca.edu.co.compositor.fachadaServicios.servicios;

import org.springframework.stereotype.Service;

@Service
public class ServiciosDeportesImpl implements IServicioDeportes {

    @Override
    public boolean estaAPazYSalvo(String idEstudiante) {
        // Aquí se implementaría la lógica para verificar el estado de paz y salvo en deportes
        System.out.println("Verificando estado de paz y salvo en deportes para el estudiante: " + idEstudiante);
        // Simulación de verificación exitosa
        return true;
    }
    
}
