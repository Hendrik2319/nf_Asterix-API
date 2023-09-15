package com.example.asterixapi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsterixRepo extends MongoRepository<Character, String> {
}
