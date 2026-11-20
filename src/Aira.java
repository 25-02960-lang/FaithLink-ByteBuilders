/**
 * A thoughtful character who enjoys quiet moments and gardens.
 */
public final class Aira extends RomanceCharacter {
    public Aira() {
        super("Aira", "thoughtful and quietly confident");
    }

    @Override
    public String respondTo(ChoiceTone tone) {
        return switch (tone) {
            case WARM -> "Aira smiles. \"That is kind of you to say.\"";
            case HONEST -> "Aira nods slowly. \"I appreciate you being straightforward with me.\"";
            case PLAYFUL -> "Aira laughs under their breath. \"You always find a way to surprise me.\"";
            case SUPPORTIVE -> "Aira's shoulders relax. \"Thank you. I needed to hear that.\"";
            case RESERVED -> "Aira gives a polite nod, but the moment stays a little distant.";
            case DISMISSIVE -> "Aira looks away. \"I thought you would understand.\"";
        };
    }
}
