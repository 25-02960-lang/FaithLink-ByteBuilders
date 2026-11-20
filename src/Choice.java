//A numbered reply the player can select within a scene.
public final class Choice {
    private final int number;
    private final String text;
    private final RomanceCharacter character;
    private final int relationshipChange;
    private final ChoiceTone tone;
    private final String neutralOutcome;

    public Choice(
            int number,
            String text,
            RomanceCharacter character,
            int relationshipChange,
            ChoiceTone tone,
            String neutralOutcome) {
        if (number < 1) {
            throw new IllegalArgumentException("Choice number must be positive.");
        }
        if (character == null && relationshipChange != 0) {
            throw new IllegalArgumentException("A relationship change needs a character.");
        }
        this.number = number;
        this.text = text;
        this.character = character;
        this.relationshipChange = relationshipChange;
        this.tone = tone;
        this.neutralOutcome = neutralOutcome;
    }

    public int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    //Applies this choice's relationship effect and returns its immediate story result.
    public String applyTo(Player player) {
        if (character == null) {
            return neutralOutcome;
        }

        if (relationshipChange >= 0) {
            player.addPoints(character, relationshipChange);
        } else {
            player.removePoints(character, Math.abs(relationshipChange));
        }

        String pointMessage = relationshipChange == 0
                ? "No relationship points changed."
                : String.format(
                        "%s relationship: %+d point%s.",
                        character.getName(),
                        relationshipChange,
                        Math.abs(relationshipChange) == 1 ? "" : "s");
        return character.respondTo(tone) + System.lineSeparator() + pointMessage;
    }
}
