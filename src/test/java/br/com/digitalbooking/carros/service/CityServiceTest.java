package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.dto.CityDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.City;
import br.com.digitalbooking.carros.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.digitalbooking.carros.TestData.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resetCityData();
    }

    // -------------------------------- COMEÇO DOS TESTES DO MÉTODO selectAll --------------------------------
    @Test
    public void whenSelectAllThenReturnAListOfCityDTO() {
        Mockito.when(cityRepository.findAll()).thenReturn(List.of(city1));

        List<CityDTO> response = cityService.selectAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(city1DTO.getId(), response.get(0).getId());
        Assertions.assertEquals(city1DTO.getName(), response.get(0).getName());
        Assertions.assertEquals(city1DTO.getAddress(), response.get(0).getAddress());
        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
    }
    // --------------------------------- FIM DOS TESTES DO MÉTODO selectAll ----------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO select ----------------------------------
    @Test
    public void whenSelectReturnACityDTO() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city1));

        CityDTO response = cityService.select(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(city1DTO.getId(), response.getId());
        Assertions.assertEquals(city1DTO.getName(), response.getName());
        Assertions.assertEquals(city1DTO.getAddress(), response.getAddress());
        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


    @Test
    public void whenSelectThrowAnEntityNotFoundException() {
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            cityService.select(1L);
            Assertions.fail("A city with invalid id was returned");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id 1", ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }
    // ---------------------------------- FIM DOS TESTES DO MÉTODO select ----------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO create -------------------------------
    @Test
    public void whenCreateThenReturnACityDTO() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(null);

        CityDTO response = cityService.create(city1DTO.noId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(city1DTO.getName(), response.getName());
        Assertions.assertEquals(city1DTO.getAddress(), response.getAddress());
        Mockito.verify(cityRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(1)).save(Mockito.any(City.class));
    }

    @Test
    public void whenCreateACityWithoutNameThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(null);

        city1DTO.setName(null);

        try {
            CityDTO response = cityService.create(city1DTO.noId());
            Assertions.fail("A city without name was created");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Campo name não informado", ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(0)).save(Mockito.any(City.class));
    }

    @Test
    public void whenCreateACityWithoutAddressThenThrowAnUnreportedEssentialFieldException() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(null);

        city1DTO.setAddress(null);

        try {
            CityDTO response = cityService.create(city1DTO.noId());
            Assertions.fail("A city without address was created");
        } catch(Exception ex) {
            Assertions.assertEquals(UnreportedEssentialFieldException.class, ex.getClass());
            Assertions.assertEquals("Campo address não informado", ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(0)).save(Mockito.any(City.class));
    }

    @Test
    public void whenCreateACityWithARepeatedNameThenThrowAnUniqueAttributeValueRepeatedException() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(city1);

        try {
            CityDTO response = cityService.create(city1DTO.noId());
            Assertions.fail("A city without address was created");
        } catch(Exception ex) {
            Assertions.assertEquals(UniqueAttributeValueRepeatedException.class, ex.getClass());
            Assertions.assertEquals("Já existe uma cidade no banco de dados com o nome " + city1DTO.getName(), ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(0)).save(Mockito.any(City.class));
    }
    // ---------------------------------- FIM DOS TESTES DO MÉTODO create ----------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO update -------------------------------
    @Test
    public void whenUpdateThenReturnACityByIdDTO() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(city1));
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(null);

        CityDTO response = cityService.update(1L, city2DTO.noId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(city2DTO.getName(), response.getName());
        Assertions.assertEquals(city2DTO.getAddress(), response.getAddress());

        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(1)).save(Mockito.any(City.class));
    }

    @Test
    public void whenUpdateACityWithInvalidIdThrowAnEntityNotFoundException() {
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(cityRepository.findByName(Mockito.anyString())).thenReturn(null);

        Long id = 1L;

        try {
            CityDTO response = cityService.update(id, city2DTO.noId());
            Assertions.fail("A city with invalid id has been updated");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id " + id, ex.getMessage());
        }
        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(0)).findByName(Mockito.anyString());
        Mockito.verify(cityRepository, Mockito.times(0)).save(Mockito.any(City.class));
    }

    @Test
    public void whenUpdateACityWithRepeatedNameThrowAnUniqueAttributeValueRepeatedException() {
        city2DTO.setName("Natal");

        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        Mockito.when(cityRepository.findById(city2.getId())).thenReturn(Optional.of(city2));
        Mockito.when(cityRepository.findByName(city2DTO.getName())).thenReturn(city1);

        try {
            CityDTO response = cityService.update(city2DTO.getId(), city2DTO.noId());
            Assertions.fail();
        } catch(Exception ex) {
            Assertions.assertEquals(UniqueAttributeValueRepeatedException.class, ex.getClass());
            Assertions.assertEquals("Já existe uma cidade no banco de dados com o nome " + city2DTO.getName(),
                    ex.getMessage());
        } finally {
            Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
            Mockito.verify(cityRepository, Mockito.times(2)).findByName(Mockito.anyString());
            Mockito.verify(cityRepository, Mockito.times(0)).save(Mockito.any(City.class));
        }
    }
    // ---------------------------------- FIM DOS TESTES DO MÉTODO update ----------------------------------



    // ---------------------------------- COMEÇO DOS TESTES DO MÉTODO delete -------------------------------
    @Test
    public void whenDeleteThenTheCityIsDeleted() {
        Mockito.doNothing().when(cityRepository).deleteById(Mockito.anyLong());
        Mockito.when(cityRepository.findById(city1.getId())).thenReturn(Optional.of(city1));

        cityService.delete(city1.getId());

        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void whenDeleteACityWithInvalidIdThrowAnEntityNotFoundException() {
        Long id = 1L;

        Mockito.when(cityRepository.findById(id)).thenThrow(new EntityNotFoundException("Não há nenhuma cidade com o id " + id));
        Mockito.doNothing().when(cityRepository).deleteById(Mockito.anyLong());

        try {
            cityService.delete(id);
            Assertions.fail("A city with an invalid id has been deleted");
        } catch(Exception ex) {
            Assertions.assertEquals(EntityNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Não há nenhuma cidade com o id " + id, ex.getMessage());
        }

        Mockito.verify(cityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(cityRepository, Mockito.times(0)).deleteById(Mockito.anyLong());
    }
    // ---------------------------------- FIM DOS TESTES DO MÉTODO delete ----------------------------------
}
