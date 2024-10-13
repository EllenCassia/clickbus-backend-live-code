package br.com.clickbus.challenge.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.clickbus.challenge.dto.PlaceDTO;
import br.com.clickbus.challenge.entity.Place;

@Configuration
public class ModelMapperConfig {

      @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Place.class, PlaceDTO.class).addMappings(mapper -> {
            mapper.map(Place::getName, PlaceDTO::setName);
            mapper.map(Place::getSlug, PlaceDTO::setSlug);
            mapper.map(Place::getCity, PlaceDTO::setCity);
            mapper.map(Place::getState, PlaceDTO::setState);
        });
        return modelMapper;
    }
     
}
