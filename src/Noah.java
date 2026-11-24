/**
 * An outgoing and dependable romance character.
 */
public final class Noah extends RomanceCharacter {
    public Noah() {
        super("Noah", "outgoing and dependable");
    }

    @Override
    public String respondTo(ChoiceTone tone) {
        return switch (tone) {
            case WARM -> "Noah grins. \"You make this easy to enjoy.\"";
            case HONEST -> "Noah nods. \"Thanks for being real with me.\"";
            case PLAYFUL -> "Noah laughs. \"I like your sense of humor.\"";
            case SUPPORTIVE -> "Noah smiles with relief. \"That means a lot to me.\"";
            case RESERVED -> "Noah gives an understanding nod. \"That's okay. Take your time.\"";
            case DISMISSIVE -> "Noah looks hurt. \"I was only trying to be friendly.\"";
        };
    }
}