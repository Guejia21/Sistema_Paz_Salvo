package co.edu.unicauca.arealaboratorios.fachadaServices.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapper {
    @Bean 
    public ModelMapper generarMapper() {
        ModelMapper objMapper = new ModelMapper();
        return objMapper; //El objeto retornado se almacena en el contenedor de Spring
    }
}

