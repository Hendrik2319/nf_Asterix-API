package com.example.asterixapi;

import lombok.With;

@With
public record Character(String id, String name, int age, String job) {
}
