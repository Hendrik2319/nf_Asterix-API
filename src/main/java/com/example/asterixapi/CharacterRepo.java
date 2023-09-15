package com.example.asterixapi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepo extends MongoRepository<Character, String> {
}
