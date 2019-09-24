package com.pkrawczynski.jwt.repository;

import com.pkrawczynski.jwt.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsUserByUsername(String username);
    User findByUsername(String user);

}
