package com.example.asterixapi;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix")
public class AsterixController {

    private final CharacterRepo characterRepo;

    AsterixController(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    @GetMapping("/characters")
    public List<Character> getAllCharacters(
            @Nullable @RequestParam String name,
            @Nullable @RequestParam Integer age,
            @Nullable @RequestParam String job
    ) {
        List<Character> characters = new ArrayList<>();

        List<Character> allCharacters = characterRepo.findAll();
        for (Character character : allCharacters) {
            if (name!=null && !name.equals(character.name())) continue;
            if (age !=null && age !=       character.age () ) continue;
            if (job !=null && !job .equals(character.job ())) continue;
            characters.add(character);
        }

        return characters;
    }

    @PostMapping("/characters")
    public Character addCharacter(@RequestBody Character newCharacter) {
        return characterRepo.save(newCharacter);
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable String id) {
        return ResponseEntity.of(characterRepo.findById(id));
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<Character> updateCharacter(
            @PathVariable String id,
            @Nullable @RequestParam String name,
            @Nullable @RequestParam Integer age,
            @Nullable @RequestParam String job
    ) {
        Optional<Character> characterOpt = characterRepo.findById(id);
        if (characterOpt.isPresent()) {
            Character character = characterOpt.get();
            if (name!=null) character = character.withName(name);
            if (age !=null) character = character.withAge (age );
            if (job !=null) character = character.withJob (job );
            return ResponseEntity.ok(characterRepo.save(character));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable String id) {
        Optional<Character> characterOpt = characterRepo.findById(id);
        characterOpt.ifPresent(characterRepo::delete);
        return ResponseEntity.of(characterOpt);
    }

    @PostMapping("/characters/createDummyData")
    public String createDummyData() {
        characterRepo.saveAll(List.of(
                new Character("1", "Asterix", 35, "Krieger"),
                new Character("2", "Obelix", 35, "Lieferant"),
                new Character("3", "Miraculix", 60, "Druide"),
                new Character("4", "Majestix", 60, "Häuptling"),
                new Character("5", "Troubadix", 25, "Barden"),
                new Character("6", "Gutemine", 35, "Häuptlingsfrau"),
                new Character("7", "Idefix", 5, "Hund"),
                new Character("8", "Geriatrix", 70, "Rentner"),
                new Character("9", "Automatix", 35, "Schmied"),
                new Character("10", "Grockelix", 35, "Fischer")
        ));
        return "added %d entries".formatted(characterRepo.count());
    }


}
