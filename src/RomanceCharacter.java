/**
 * Shared details and behavior for a character the player can build a relationship with.
 */
public abstract class RomanceCharacter {
    private final String name;
    private final String personality;

    protected RomanceCharacter(String name, String personality) {
        this.name = name;
        this.personality = personality;
    }

    public String getName() {
        return name;
    }

    public String getPersonality() {
        return personality;
    }

    /**
     * Each character reacts differently to the same type of player choice.
     */
    public abstract String respondTo(ChoiceTone tone);
}
