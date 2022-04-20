package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.dto.CategoryDTO;
import br.com.digitalbooking.carros.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> selectAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> select(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.select(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(categoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.update(id, categoryDTO.noId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
