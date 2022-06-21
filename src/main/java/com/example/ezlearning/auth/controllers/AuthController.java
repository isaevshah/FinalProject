package com.example.ezlearning.auth.controllers;

import com.example.ezlearning.auth.entity.Activity;
import com.example.ezlearning.auth.entity.Role;
import com.example.ezlearning.auth.entity.User;
import com.example.ezlearning.auth.services.UserDetailsImpl;
import com.example.ezlearning.auth.services.UserService;
import com.example.ezlearning.auth.exception.UserOrEmailExistsException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

import static com.example.ezlearning.auth.services.UserService.DEFAULT_ROLE;

@RestController
@RequestMapping("/auth")
@Log
@Setter
@Getter
public class AuthController {

    private UserService userService;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {

        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;

    }
    // регистрация нового пользователя
    @PutMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) throws RoleNotFoundException { // здесь параметр user используется, чтобы передать все данные пользователя для регистрации
        if (userService.userExists(user.getUsername(), user.getEmail())) {
            throw new UserOrEmailExistsException("User or email already exists");
        }

        Role userRole = userService.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new RoleNotFoundException("Default Role USER not found."));
        user.getRoles().add(userRole);

        user.setPassword(encoder.encode(user.getPassword())); // hash the password


        Activity activity = new Activity();
        activity.setUser(user);
        activity.setUuid(UUID.randomUUID().toString());

        userService.register(user, activity); // сохранить пользователя в БД

          return ResponseEntity.ok().build(); // просто отправляем статус 200-ОК (без каких-либо данных) - значит регистрация выполнилась успешно
    }

    // активация пользователя (чтобы мог авторизоваться и работать дальше с приложением)
    @PostMapping("/activate-account")
    public ResponseEntity<Boolean> activateUser(@RequestBody String uuid) { // true - успешно активирован

        // проверяем UUID пользователя, которого хотим активировать
        Activity activity = userService.findActivityByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("Activity Not Found with uuid: " + uuid));

        // если пользователь уже был ранее активирован
        //by myself
//        if (activity.isActivated())
//            throw new UserAlreadyActivatedException("User already activated");

        // возвращает кол-во обновленных записей (в нашем случае должна быть 1)
        int updatedCount = userService.activate(uuid); // активируем пользователя

        return ResponseEntity.ok(updatedCount == 1); // 1 - значит запись обновилась успешно, 0 - что-то пошло не так
    }

    // залогиниться по паролю пользователя
    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody User user) {

        // проверяем логин-пароль
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        // добавляем Spring-контейнер инфу об авторизации
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // UserDetailsImpl - спец объект, который хранится в Spring контейнере и содержит данные пользователя
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // активирован пользователь или нет
        if(!userDetails.isActivated()) {
            throw new DisabledException("User disabled"); // клиенту отправим ошибку что пользователь не активен
        }

        return ResponseEntity.ok().body(userDetails.getUser());


    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<JsonException> handleException(Exception ex) {
//        return new ResponseEntity(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
//    }


}
