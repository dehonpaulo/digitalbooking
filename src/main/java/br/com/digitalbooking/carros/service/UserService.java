package br.com.digitalbooking.carros.service;

import br.com.digitalbooking.carros.config.security.TokenService;
import br.com.digitalbooking.carros.dto.UserDTO;
import br.com.digitalbooking.carros.exceptions.EntityNotFoundException;
import br.com.digitalbooking.carros.exceptions.UniqueAttributeValueRepeatedException;
import br.com.digitalbooking.carros.exceptions.UnreportedEssentialFieldException;
import br.com.digitalbooking.carros.model.User;
import br.com.digitalbooking.carros.repository.RoleRepository;
import br.com.digitalbooking.carros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenService tokenService;

    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getName() == null) throw new UnreportedEssentialFieldException("Campo name não informado");
        if (userDTO.getLastName() == null) throw new UnreportedEssentialFieldException("Campo lastName não informado");
        if (userDTO.getEmail() == null) throw new UnreportedEssentialFieldException("Campo email não informado");
        if (userDTO.getPassword() == null) throw new UnreportedEssentialFieldException("Campo password não informado");
        if (userRepository.findByEmail(userDTO.getEmail()) != null) throw new UniqueAttributeValueRepeatedException("Email já cadastrado");

        User user = new User(null, userDTO.getName(), userDTO.getLastName(), userDTO.getEmail(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                roleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role não existente")));

        return new UserDTO(userRepository.save(user)).noPassword();
    }

    public UserDTO update(UserDTO userDTO, String authorization) {
        Long idUser = tokenService.getIdUser(authorization.substring(7, authorization.length()));
        User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getLastName() != null) user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null) user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        return new UserDTO(userRepository.save(user)).noPassword();
    }

    public void delete(String authorization) {
        Long idUser = tokenService.getIdUser(authorization.substring(7, authorization.length()));
        userRepository.deleteById(idUser);
    }
}
