package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.config.security.TokenService;
import br.com.digitalbooking.carros.dto.bookingDTOs.BookingByIdResponseDTO;
import br.com.digitalbooking.carros.dto.bookingDTOs.BookingRequestDTO;
import br.com.digitalbooking.carros.dto.bookingDTOs.BookingResponseDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.*;
import br.com.digitalbooking.carros.repository.BookingRepository;
import br.com.digitalbooking.carros.repository.CityRepository;
import br.com.digitalbooking.carros.repository.ProductRepository;
import br.com.digitalbooking.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    protected EntityManager entityManager;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TokenService tokenService;

    public List<BookingResponseDTO> selectAll() {
        return bookingRepository.findAll().stream().map(booking -> {
            return new BookingResponseDTO(booking);
        }).collect(Collectors.toList());
    }

    public List<BookingResponseDTO> selectByUser(Long idUser, String authorization) {
        // captura o id do usuário logado
        Long idUserAuthorized = tokenService.getIdUser(authorization.substring(7, authorization.length()));

        // verifica se o user enviado na solicitação é mesmo usuário logado, se não for lança exception
        if(idUser != idUserAuthorized) throw new UnreportedEssentialFieldException("Usuário inválido");

        // Tenta encontrar o usuário que foi passado
        User user = userRepository.findById(idUser).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum usuário com o id " + idUser);
        });


        // retorna uma lista com todos os bookings do usuário
        return bookingRepository.findByUser(user).stream().map(booking -> {
            return new BookingResponseDTO(booking);
        }).collect(Collectors.toList());
    }

    public BookingByIdResponseDTO select(Long id) {
        return new BookingByIdResponseDTO((bookingRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum booking com o id " + id);
        })));
    }

    public List<BookingResponseDTO> selectByProduct(Long idProduct) {
        // Tenta encontrar o produto que foi passado
        Product product = productRepository.findById(idProduct).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum produto com o id " + idProduct);
        });


        // retorna uma lista com todos os bookings do produto
        return bookingRepository.findByProduct(product).stream().map(booking -> {
            return new BookingResponseDTO(booking);
        }).collect(Collectors.toList());
    }

    public BookingByIdResponseDTO create(BookingRequestDTO bookingRequestDTO, String authorization) {
        // captura o id do usuário logado
        Long idUser = tokenService.getIdUser(authorization.substring(7, authorization.length()));

        // verifica se o user enviado na solicitação é mesmo usuário logado, se não for lança exception
        if(bookingRequestDTO.getIdUser() != idUser) throw new UnreportedEssentialFieldException("Usuário inválido");

        // instancia um novo booking
        Booking booking = new Booking();

        // verifica se o campo data foi enviado nulo
        if(bookingRequestDTO.getCheckIn() == null ) throw new UnreportedEssentialFieldException("Valor do campo checkIn não informado");

        // atribui o dado de data ao objeto
        booking.setCheckIn(bookingRequestDTO.getCheckIn());

        // verifica se o campo data foi enviado nulo
        if(bookingRequestDTO.getCheckOut() == null ) throw new UnreportedEssentialFieldException("Valor do campo checkIn não informado");


        // atribui o dado de data ao objeto
        booking.setCheckOut(bookingRequestDTO.getCheckOut());

        // verifica se o campo pickUp foi enviado nulo
        if(bookingRequestDTO.getPickUp() == null ) throw new UnreportedEssentialFieldException("Valor do campo pickUp não informado");

        // atribui o dado de pickUp ao objeto
        booking.setPickUp(bookingRequestDTO.getPickUp());

        // procura se existe o produto informado
        Product product = productRepository.findById(bookingRequestDTO.getIdUser()).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum produto com o id " + bookingRequestDTO.getIdUser());
        });

        // atribui o produto ao objeto
        booking.setProduct(product);

        // verifica se existe o usuário informado
        User user = userRepository.findById(bookingRequestDTO.getIdUser()).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhum usuário com o id " + bookingRequestDTO.getIdUser());
        });

        // verifica se o usuário que está fazendo a reserva é o mesmo que está na requisição
        // TO-DO

        // atribui o usuario ao objeto
        booking.setUser(user);

        // procura se existem bookings com aquela data, com aquele produto e com status reserved, se existem retorna uma exeção
        String status = "Reserved";
        // if (bookingRepository.findByProductAndCheckinAndCheckoutAndStatus(product, bookingRequestDTO.getCheckIn(), bookingRequestDTO.getCheckOut(), status ) != null ) { throw new UniqueAttributeValueRepeatedException("Essa data já está reservada");}

        // Atribuindo o status reserved para a nova reserva
        booking.setStatus("Reserved");
        // salva o novo booking no banco de dados
        Booking bookingSaved = bookingRepository.save(booking);

        BookingByIdResponseDTO bookingByIdResponseDTO = new BookingByIdResponseDTO(bookingSaved);

        return bookingByIdResponseDTO;
    }

}