package com.example.ezlearning.auth.repository;

import com.example.ezlearning.auth.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    // для проверки email username
    @Query("select case when count(u)>0 then true else false end from User u where lower(u.email) = lower(:email)")
    boolean existsByEmail(@Param("email") String email);

    @Query("select case when count(u)> 0 then true else false end from User u where lower(u.username) = lower(:username)")
    boolean existsByUsername(@Param("username") String username);

    // используем обертку Optional - контейнер, который хранит значение или null - позволяет избежать ошибки NullPointerException
    Optional<User> findByUsername(String username); // поиск по username

    // используем обертку Optional - контейнер, который хранит значение или null - позволяет избежать ошибки NullPointerException
    Optional<User> findByEmail(String email); // поиск по email
}
