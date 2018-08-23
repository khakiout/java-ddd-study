package com.khakiout.study.ddddemo.infrastructure.spring;

import com.khakiout.study.ddddemo.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringUserRepository extends CrudRepository<User, String> {

}
