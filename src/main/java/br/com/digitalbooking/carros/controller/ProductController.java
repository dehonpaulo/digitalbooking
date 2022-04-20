package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.dto.productDTOs.ProductRequestDTO;
import br.com.digitalbooking.carros.dto.productDTOs.ProductResponseDTO;
import br.com.digitalbooking.carros.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value ="/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> selectAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> select(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.select(id));
    }

    @GetMapping("/byCity/{idCity}")
    public ResponseEntity<List<ProductResponseDTO>> selectByCity(@PathVariable Long idCity) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.selectByCity(idCity));
    }

    @GetMapping("/byCategory/{idCategory}")
    public ResponseEntity<List<ProductResponseDTO>> selectByCategory(@PathVariable Long idCategory) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.selectByCategory(idCategory));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(id, productRequestDTO.noId()));
    }


    //trae los productos disponibles por fechas y ciudad
    @GetMapping("/availables/{idCity}")
    public ResponseEntity<ProductResponseDTO> findAvailableProductsByDatesAndCities(@RequestParam @DateTimeFormat(pattern = "yyyy" +
            "-MM-dd") LocalDate checkIn, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut, @PathVariable Long idCity) throws Exception {
        return (ResponseEntity<ProductResponseDTO>) productService.findAvailableProductsByDatesAndCities(checkIn, checkOut, idCity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
