package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

}
