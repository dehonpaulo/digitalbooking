package br.com.digitalbooking.carros.controller;

import br.com.digitalbooking.carros.dto.bookingDTOs.BookingByIdResponseDTO;
import br.com.digitalbooking.carros.dto.bookingDTOs.BookingRequestDTO;
import br.com.digitalbooking.carros.dto.bookingDTOs.BookingResponseDTO;
import br.com.digitalbooking.carros.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponseDTO>> selectAll() {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingByIdResponseDTO> select(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.select(id));
    }

    @GetMapping("/byUser/{idUser}")
    public ResponseEntity<List<BookingResponseDTO>> selectByUser(@PathVariable Long idUser,  @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.selectByUser(idUser, authorization));
    }

    @GetMapping("/byProduct/{idProduct}")
    public ResponseEntity<List<BookingResponseDTO>> selectByProduct(@PathVariable Long idUser) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.selectByProduct(idUser));
    }

    @PostMapping
    public ResponseEntity<BookingByIdResponseDTO> create(@RequestBody BookingRequestDTO bookingDTO,  @RequestHeader(value = "Authorization") String authorization) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(bookingDTO, authorization));
    }
}