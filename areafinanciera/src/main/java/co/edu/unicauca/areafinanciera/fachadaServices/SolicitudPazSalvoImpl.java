package co.edu.unicauca.areafinanciera.fachadaServices;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unicauca.areafinanciera.capaAccesoDatos.modelo.Deuda;
import co.edu.unicauca.areafinanciera.capaAccesoDatos.repositorio.DeudaRepositorio;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DeudaDTOFinanciera;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTORespuesta.RespuestaPazSalvoDTOFinanciera;
import co.edu.unicauca.areafinanciera.fachadaServices.DTO.DTOPeticion.PeticionPazSalvoDTO;

@Service
public class SolicitudPazSalvoImpl implements SolicitudPazSalvoInt {
    @Autowired
    private DeudaRepositorio repositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RespuestaPazSalvoDTOFinanciera consultarPazYSalvo(PeticionPazSalvoDTO peticion) {
        List<Deuda> deudasNoPagadas = repositorio.obtenerDeudasNoPagadasPorEstudiante(peticion.getIdEstudiante());
        List<DeudaDTOFinanciera> deudasDTO = deudasNoPagadas.stream()
                .map(deuda -> modelMapper.map(deuda, DeudaDTOFinanciera.class))
                .collect(Collectors.toList());

        boolean pazYSalvo = deudasDTO.isEmpty();
        return new RespuestaPazSalvoDTOFinanciera(pazYSalvo, deudasDTO);
    }

    @Override
    public boolean eliminarPendientes(int idEstudiante) {
        List<Deuda> deudas = repositorio.obtenerDeudasNoPagadasPorEstudiante(idEstudiante);
        if (deudas.isEmpty()) {
            return false;
        }
        repositorio.eliminarDeudasNoPagadasPorEstudiante(idEstudiante);
        return true;
    }
}