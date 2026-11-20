/**
 * An energetic character who enjoys books and new experiences.
 */
public final class Liana extends RomanceCharacter {
    public Liana() {
        super("Liana", "energetic and adventurous");
    }

    @Override
    public String respondTo(ChoiceTone tone) {
        return switch (tone) {
            case WARM -> "Liana beams. \"You have a way of making ordinary days feel brighter.\"";
            case HONEST -> "Liana grins. \"Honest answers are my favorite kind.\"";
            case PLAYFUL -> "Liana laughs loudly. \"Okay, you win that round!\"";
            case SUPPORTIVE -> "Liana gives you a grateful high-five. \"Thanks for having my back.\"";
            case RESERVED -> "Liana tilts their head. \"You seem far away today. Everything okay?\"";
            case DISMISSIVE -> "Liana's smile fades. \"That was not very fair.\"";
        };
    }
}
