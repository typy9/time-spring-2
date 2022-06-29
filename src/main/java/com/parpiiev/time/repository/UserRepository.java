package com.parpiiev.time.repository;

import com.parpiiev.time.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("select u from User u where u.login=:login")
    Optional<User> findByLogin(@Param(value = "login") String login);

    @Modifying @Transactional
    @Query("update User u set u.username =:username, u.login =:login, u.password =:password where u.userId =:id")
    void updateUserById(@Param(value = "id") int id, @Param(value = "username") String username,
                        @Param(value = "login") String login, @Param(value = "password") String password);

    @Query("select u.role from User u where u.login=:login and u.password=:password")
    Optional<String> getUserRole(@Param(value = "login") String login, @Param(value = "password") String password);

}
