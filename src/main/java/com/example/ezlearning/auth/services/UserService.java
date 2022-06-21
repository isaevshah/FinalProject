package com.example.ezlearning.auth.services;

import com.example.ezlearning.auth.entity.Activity;
import com.example.ezlearning.auth.entity.Role;
import com.example.ezlearning.auth.entity.User;
import com.example.ezlearning.auth.repository.ActivityRepository;
import com.example.ezlearning.auth.repository.RoleRepository;
import com.example.ezlearning.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    public static final String DEFAULT_ROLE = "USER";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ActivityRepository activityRepository;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, ActivityRepository activityRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }

    public void register(User user, Activity activity){
        userRepository.save(user);
        activityRepository.save(activity);
    }

    // Проверка наличия пользователя в БД
    public boolean userExists(String username, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return true;
        }

        return false;
    }

    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }

    public Activity saveActivity(Activity activity){
        return activityRepository.save(activity);
    }

    public Optional<Activity> findActivityByUuid(String uuid){
        return activityRepository.findByUuid(uuid);
    }

    // true сконвертируется в 1, т.к. указали @Type(type = "org.hibernate.type.NumericBooleanType") в классе Activity
    public int activate(String uuid){
        return activityRepository.changeActivated(uuid, true);
    }

    // false сконвертируется в 0, т.к. указали @Type(type = "org.hibernate.type.NumericBooleanType") в классе Activity
    public int deactivate(String uuid){
        return activityRepository.changeActivated(uuid, false);
    }
}
