import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stores one player's name and relationship progress for the current game.
 */
public final class Player {
    private String name;
    private final Map<String, Integer> relationshipPoints = new LinkedHashMap<>();

    public Player(String name, Collection<RomanceCharacter> characters) {
        setName(name);
        for (RomanceCharacter character : characters) {
            relationshipPoints.put(character.getName(), 0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank.");
        }
        this.name = name.trim();
    }

    public void addPoints(RomanceCharacter character, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to add cannot be negative.");
        }
        changePoints(character, points);
    }

    public void removePoints(RomanceCharacter character, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to remove cannot be negative.");
        }
        changePoints(character, -points);
    }

    public int getPoints(RomanceCharacter character) {
        Integer points = relationshipPoints.get(character.getName());
        if (points == null) {
            throw new IllegalArgumentException("Unknown character: " + character.getName());
        }
        return points;
    }

    public Map<String, Integer> getRelationshipPoints() {
        return Collections.unmodifiableMap(relationshipPoints);
    }

    private void changePoints(RomanceCharacter character, int amount) {
        String characterName = character.getName();
        if (!relationshipPoints.containsKey(characterName)) {
            throw new IllegalArgumentException("Unknown character: " + characterName);
        }
        relationshipPoints.merge(characterName, amount, Integer::sum);
    }
}
