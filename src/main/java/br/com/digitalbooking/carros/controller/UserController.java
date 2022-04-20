package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.dto.UserDTO;
import br.com.digitalbooking.carros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(userService.create(userDTO));
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDTO, authorization));
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestHeader(value = "Authorization") String authorization) {
        userService.delete(authorization);
        return new ResponseEntity(HttpStatus.OK);
    }
}
