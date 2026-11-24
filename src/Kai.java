/**
 * A calm and creative romance character.
 */
public final class Kai extends RomanceCharacter {
    public Kai() {
        super("Kai", "calm and creative");
    }

    @Override
    public String respondTo(ChoiceTone tone) {
        return switch (tone) {
            case WARM -> "Kai smiles. \"You have a way of making people feel welcome.\"";
            case HONEST -> "Kai nods. \"I respect that. Honesty makes things easier.\"";
            case PLAYFUL -> "Kai laughs. \"Okay, that was a good one.\"";
            case SUPPORTIVE -> "Kai relaxes. \"Thanks. I appreciate you being there.\"";
            case RESERVED -> "Kai gives a small nod. \"No pressure. We can talk whenever you're ready.\"";
            case DISMISSIVE -> "Kai looks disappointed. \"I thought we were getting along.\"";
        };
    }
}