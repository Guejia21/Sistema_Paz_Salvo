package co.edu.unicauca.arealaboratorios.fachadaServices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.arealaboratorios.capaAccesoDatos.modelo.Prestamo;
import co.edu.unicauca.arealaboratorios.capaAccesoDatos.repositorio.PrestamoRepositorioLaboratorio;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.PrestamoDTOLaboratorio;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;
import co.edu.unicauca.arealaboratorios.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTOLaboratorios;

@Service
public class SolicitudPazSalvoImpl implements SolicitudPazSalvoInt {
    @Autowired
    private PrestamoRepositorioLaboratorio repositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RespuestaPazSalvoDTOLaboratorios consultarPazYSalvo(PeticionPazSalvoDTO peticion) {
        List<Prestamo> pendientes = repositorio.obtenerPrestamosNoDevueltosPorEstudiante(peticion.getIdEstudiante());
        List<PrestamoDTOLaboratorio> prestamosDTO = pendientes.stream()
                .map(p -> modelMapper.map(p, PrestamoDTOLaboratorio.class))
                .collect(Collectors.toList());

        boolean pazYSalvo = prestamosDTO.isEmpty();
        return new RespuestaPazSalvoDTOLaboratorios(pazYSalvo, prestamosDTO);
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