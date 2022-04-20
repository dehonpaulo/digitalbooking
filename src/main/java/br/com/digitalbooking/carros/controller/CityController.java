package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.dto.CityDTO;
import br.com.digitalbooking.carros.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> selectAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> select(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityService.select(id));
    }

    @PostMapping
    public ResponseEntity<CityDTO> create(@RequestBody CityDTO cityDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityService.create(cityDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cityService.update(id, cityDTO.noId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        cityService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}