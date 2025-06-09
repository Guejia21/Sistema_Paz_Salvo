package co.edu.unicauca.arealaboratorios.fachadaServices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.arealaboratorios.capaAccesoDatos.modelo.Prestamo;
import co.edu.unicauca.arealaboratorios.capaAccesoDatos.repositorio.PrestamoRepositorioLaboratorio;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.PrestamoDTO;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTO;

@Service
public class SolicitudPazSalvoImpl implements SolicitudPazSalvoInt {
    @Autowired
    private PrestamoRepositorioLaboratorio repositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RespuestaPazSalvoDTO consultarPazYSalvo(PeticionPazSalvoDTO peticion) {
        List<Prestamo> pendientes = repositorio.obtenerPrestamosNoDevueltosPorEstudiante(peticion.getIdEstudiante());
        List<PrestamoDTO> prestamosDTO = pendientes.stream()
                .map(p -> modelMapper.map(p, PrestamoDTO.class))
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