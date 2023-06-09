package br.com.jonatas.apilogin.controller;

import br.com.jonatas.apilogin.record.LoginDto;
import br.com.jonatas.apilogin.record.TokenDto;
import br.com.jonatas.apilogin.record.UserDto;
import br.com.jonatas.apilogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/v1/users", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            var users = this.userService.users();
            return ResponseEntity.ok(users);
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getAll(@PathVariable("email") String email) {
        try {
            var user = this.userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> post(@RequestBody UserDto user) {
        try {
            this.userService.createUser(user);
            return ResponseEntity.ok("\"message\": \"User created successfully.\"");
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login){
        try{
            var token = this.userService.login(login);
            return ResponseEntity.ok(new TokenDto(token));
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
