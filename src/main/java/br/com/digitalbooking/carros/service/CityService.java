package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.dto.CityDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.City;
import br.com.digitalbooking.carros.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<CityDTO> selectAll() {
        // transforma a lista de objetos do tipo City em uma lista de DTOs e a retorna
        return cityRepository.findAll().stream().map(city -> {
            return new CityDTO(city);
        }).collect(Collectors.toList());
    }

    public CityDTO select(Long id) {
        // se houver um registro no DB com o id informado, ele será retornado como um dto
        // caso contrário, uma exceção é lançada
        return new CityDTO(cityRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Não há nenhuma cidade com o id " + id);
        }));
    }

    public CityDTO create(CityDTO cityDTO) {
        // se o name (atributo essencial) for nulo, lança uma exceção
        if (cityDTO.getName() == null) throw new UnreportedEssentialFieldException("Campo name não informado");
        // se o address (atributo essencial) for nulo, lança uma exceção
        if (cityDTO.getAddress() == null) throw new UnreportedEssentialFieldException("Campo address não informado");
        // se tentar criar uma cidade com um nome que já existe no DB, lança uma exceção
        if (cityRepository.findByName(cityDTO.getName()) != null) throw new UniqueAttributeValueRepeatedException("Já existe uma cidade no banco de dados com o nome " + cityDTO.getName());

        // instancia uma nova cidade, salva no banco de dados e retorna como um dto
        City city = new City(cityDTO.noId());
        return new CityDTO(cityRepository.save(city)).noId();
    }

    public CityDTO update(Long id, CityDTO cityDTO) {
        // tenta encontrar um registro no DB com o id informado, caso não haja, lança uma exceção
        City city = cityRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Não há nenhuma cidade com o id " + id);
        });
        // caso o novo nome já exista em algum atributo difente do que está sendo modificado, lança uma exceção
        if (cityRepository.findByName(cityDTO.getName()) != null && cityRepository.findByName(cityDTO.getName()).getId() != id) {
            throw new UniqueAttributeValueRepeatedException("Já existe uma cidade no banco de dados com o nome " + cityDTO.getName());
        }

        // modifica apenas os campos não nulos passados no corpo da requisição
        if (cityDTO.getName() != null) city.setName(cityDTO.getName());
        if (cityDTO.getAddress() != null) city.setAddress(cityDTO.getAddress());

        // altera a cidade no banco de dados e retorna sua nova forma como um dto
        return new CityDTO(cityRepository.save(city)).noId();
    }

    public void delete(Long id) {
        // verifica se há alguma cidade no banco de dados com o id informado. Caso não haja, lança uma exceção
        this.select(id);

        // exclui a cidade do banco de dados
        cityRepository.deleteById(id);
    }
}
