package br.com.clickbus.challenge.controller;

import br.com.clickbus.challenge.dto.PlaceDTO;
import br.com.clickbus.challenge.entity.Place;
import br.com.clickbus.challenge.exception.PlaceNotFoundException;
import br.com.clickbus.challenge.exception.ResourceNotFoundException;
import br.com.clickbus.challenge.service.PlaceService;
import io.swagger.annotations.Api;

import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Api("places")
@RestController
@RequestMapping("places")
public class PlaceController {

    @Autowired
    private PlaceService service;

    @Autowired
    private ModelMapper modelMapper; 

    @PostMapping
    public ResponseEntity<PlaceDTO> create(@RequestBody @Valid PlaceDTO dto) {

        Place place = modelMapper.map(dto, Place.class);
        place.setCreatedAt(LocalDateTime.now());
        Place savedPlace = service.save(place);
       // System.out.println("Saved Place: " + savedPlace); 
    
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(savedPlace, PlaceDTO.class));
    }


    @GetMapping("{id}")
    public ResponseEntity<PlaceDTO> findById(@PathVariable Long id) {
        Place place = service.findById(id)
            .orElseThrow(() -> new PlaceNotFoundException("Place com id " + id + " não encontrado"));
        
        System.out.println("Place: " + place); // Para depuração

        return ResponseEntity.ok().body(modelMapper.map(place, PlaceDTO.class));
    }


    @GetMapping
    public ResponseEntity<Iterable<PlaceDTO>> findAll() {
        Iterable<PlaceDTO> places = PlaceDTO.convertToList(service.findAll());
        return ResponseEntity.ok(places);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Iterable<PlaceDTO>> findByName(@PathVariable String name) {
        List<Place> places = service.findByName(name);

        if (places.isEmpty()) {
            throw new PlaceNotFoundException("Place com nome " + name + " não encontrado");
        }
        
        Iterable<PlaceDTO> placesDTO = places.stream()
            .map(place -> modelMapper.map(place, PlaceDTO.class)) 
            .collect(Collectors.toList());
    
        return ResponseEntity.ok().body(placesDTO); 
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> alter(@PathVariable Long id, @RequestBody @Valid PlaceDTO placeDTO) {
        Place place = service.findById(id).orElseThrow(() -> new PlaceNotFoundException("Place com id " + id + " não encontrado"));
        Place alterPlace = service.alter(place, placeDTO);
        //System.out.println("Alter Place: " + alterPlace);
        return ResponseEntity.ok().body(modelMapper.map(alterPlace, PlaceDTO.class));
    }
}
