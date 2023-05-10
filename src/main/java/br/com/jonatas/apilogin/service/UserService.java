package br.com.jonatas.apilogin.service;

import br.com.jonatas.apilogin.model.User;
import br.com.jonatas.apilogin.record.LoginDto;
import br.com.jonatas.apilogin.record.UserDto;
import br.com.jonatas.apilogin.repository.UserRepository;
import br.com.jonatas.apilogin.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UserDto> users() {

        List<UserDto> usersDto = this.userRepository.findAll()
                .stream().map((user) ->
                        new UserDto(
                                user.getFullName(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword()
                        ))
                .collect(Collectors.toList());

        return usersDto;
    }

    public void createUser(UserDto userDto) {

        this.userRepository.findByEmail(userDto.email())
                .ifPresent((u) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email ja esta sendo usado");
                });

        User user = User.builder()
                .fullName(userDto.fullName())
                .username(userDto.username())
                .email(userDto.email())
                .password(userDto.password())
                .build();


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

    public String login(LoginDto login){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.username(), login.password());

        Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);

        User userToken = (User) authenticate.getPrincipal();
        System.out.println(userToken);
        return TokenUtil.encoder(userToken);
    }
}
