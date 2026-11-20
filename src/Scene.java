import java.util.List;

/**
 * A short story scene with dialogue and numbered choices.
 */
public final class Scene {
    private final int sceneNumber;
    private final String title;
    private final String dialogue;
    private final List<Choice> choices;

    public Scene(int sceneNumber, String title, String dialogue, List<Choice> choices) {
        if (choices == null || choices.isEmpty()) {
            throw new IllegalArgumentException("A scene needs at least one choice.");
        }
        this.sceneNumber = sceneNumber;
        this.title = title;
        this.dialogue = dialogue;
        this.choices = List.copyOf(choices);
    }

    public void showScene(Player player) {
        System.out.printf("%n--- Scene %d: %s ---%n", sceneNumber, title);
        System.out.println(dialogue.replace("{player}", player.getName()));
    }

    public void showChoices() {
        System.out.println("What will you say?");
        for (Choice choice : choices) {
            System.out.printf("%d. %s%n", choice.getNumber(), choice.getText());
        }
    }

    public Choice getChoice(int number) {
        return choices.stream()
                .filter(choice -> choice.getNumber() == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("That choice is not available."));
    }

    public int getChoiceCount() {
        return choices.size();
    }
}
