package coffee.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//coffee.config.ModelMapperConfig needed because ModelMapper isn't registered in application context per default like in traditional Spring MVC
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

