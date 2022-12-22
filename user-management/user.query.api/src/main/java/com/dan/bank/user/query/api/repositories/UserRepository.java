package com.dan.bank.user.query.api.repositories;

import com.dan.bank.user.core.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'$or' : [{'firstName': {$regex : ?0, $options: 'i'}}, {'lastName': {$regex : ?0, $options: 'i'}}, {'email': {$regex : ?0, $options: 'i'}}, {'account.username': {$regex : ?0, $options: 'i'}}]}")
    List<User> findByFilterRegex(String filter);
}
