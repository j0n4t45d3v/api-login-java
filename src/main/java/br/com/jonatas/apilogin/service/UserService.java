package br.com.jonatas.apilogin.service;

import br.com.jonatas.apilogin.model.User;
import br.com.jonatas.apilogin.record.UserDto;
import br.com.jonatas.apilogin.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDto userDto) {
        this.userRepository.findByEmail(userDto.email())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "email ja esta sendo usado"));

        ModelMapper modelMapper = new ModelMapper();

        User user = new User();

        modelMapper.map(userDto, user);
        this.userRepository.save(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "email nao encontrado"));

        UserDto userDto =
                new UserDto(user.getFullName(), user.getUsername(), user.getEmail(), user.getPassword());

        return userDto;
    }
}
