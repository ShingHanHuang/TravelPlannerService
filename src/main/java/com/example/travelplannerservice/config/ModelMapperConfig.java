package com.example.travelplannerservice.config;

import com.example.travelplannerservice.dto.TripRequestDTO;
import com.example.travelplannerservice.dao.Trip;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Converter<String, LocalDate> stringToLocalDateConverter = context ->
                context.getSource() == null ? null : LocalDate.parse(context.getSource(), DateTimeFormatter.ISO_LOCAL_DATE);

        modelMapper.typeMap(TripRequestDTO.class, Trip.class).addMappings(mapper -> {
            mapper.using(stringToLocalDateConverter).map(TripRequestDTO::getTravelStartDates, Trip::setStartDate);
            mapper.using(stringToLocalDateConverter).map(TripRequestDTO::getTravelEndDates, Trip::setEndDate);
            mapper.map(TripRequestDTO::getDestination, Trip::setDestination);
            mapper.map(TripRequestDTO::getPreferences, Trip::setPreferences);
            mapper.map(TripRequestDTO::getItinerary, Trip::setItinerary);
        });
        return modelMapper;
    }
}
