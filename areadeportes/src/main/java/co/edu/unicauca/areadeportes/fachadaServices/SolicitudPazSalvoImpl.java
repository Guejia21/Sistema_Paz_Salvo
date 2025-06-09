package co.edu.unicauca.areadeportes.fachadaServices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.areadeportes.capaAccesoDatos.modelo.Prestamo;
import co.edu.unicauca.areadeportes.capaAccesoDatos.repositorio.PrestamoRepositorioDeportes;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.PrestamoDTO;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTO;

@Service
public class SolicitudPazSalvoImpl implements SolicitudPazSalvoInt {
    @Autowired
    private PrestamoRepositorioDeportes repositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RespuestaPazSalvoDTO consultarPazYSalvo(PeticionPazSalvoDTO peticion) {
        List<Prestamo> prestamosPendientes = repositorio.obtenerPrestamosNoDevueltosPorEstudiante(peticion.getIdEstudiante());
        List<PrestamoDTO> prestamosDTO = prestamosPendientes.stream()
                .map(prestamo -> modelMapper.map(prestamo, PrestamoDTO.class))
                .collect(Collectors.toList());

        boolean pazYSalvo = prestamosDTO.isEmpty();
        return new RespuestaPazSalvoDTO(pazYSalvo, prestamosDTO);
    }

    @Override
    public boolean eliminarPendientes(int idEstudiante) {
        List<Prestamo> pendientes = repositorio.obtenerPrestamosNoDevueltosPorEstudiante(idEstudiante);
        if (pendientes.isEmpty()) {
            return false;
        }
        repositorio.eliminarPrestamosNoDevueltosPorEstudiante(idEstudiante);
        return true;
    }
}
