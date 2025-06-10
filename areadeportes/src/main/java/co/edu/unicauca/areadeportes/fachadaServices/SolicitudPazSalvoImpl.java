package co.edu.unicauca.areadeportes.fachadaServices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.areadeportes.capaAccesoDatos.modelo.Prestamo;
import co.edu.unicauca.areadeportes.capaAccesoDatos.repositorio.PrestamoRepositorioDeportes;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.PrestamoDTODeportes;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.areadeportes.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTODeportes;

@Service
public class SolicitudPazSalvoImpl implements SolicitudPazSalvoInt {
    @Autowired
    private PrestamoRepositorioDeportes repositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RespuestaPazSalvoDTODeportes consultarPazYSalvo(PeticionPazSalvoDTO peticion) {
        List<Prestamo> prestamosPendientes = repositorio.obtenerPrestamosNoDevueltosPorEstudiante(peticion.getIdEstudiante());
        List<PrestamoDTODeportes> prestamosDTO = prestamosPendientes.stream()
                .map(prestamo -> modelMapper.map(prestamo, PrestamoDTODeportes.class))
                .collect(Collectors.toList());

        boolean pazYSalvo = prestamosDTO.isEmpty();
        return new RespuestaPazSalvoDTODeportes(pazYSalvo, prestamosDTO);
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
