import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Standalone FaithLink game for VS Code.
 *
 * Compile: javac faithlink.java
 * Run:     java faithlink
 */
public class faithlink {
    private static final int FRIENDSHIP_THRESHOLD = 8;
    private static final int ROMANCE_THRESHOLD = 15;

    private final Scanner scanner = new Scanner(System.in);
    private final RomanceCharacter aira = new Aira();
    private final RomanceCharacter liana = new Liana();
    private final List<RomanceCharacter> characters = List.of(aira, liana);

    public static void main(String[] args) {
        new faithlink().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            showStartMenu();
            int menuChoice = readNumberInRange("Enter your choice: ", 1, 3);
            switch (menuChoice) {
                case 1 -> startGame();
                case 2 -> {
                    showGameInstructions();
                    waitForEnter();
                }
                case 3 -> {
                    System.out.println("Thanks for playing FaithLink. See you next time!");
                    running = false;
                }
                default -> throw new IllegalStateException("Unexpected menu choice.");
            }
        }
    }

    private void showStartMenu() {
        System.out.println("\n=== FaithLink ===");
        System.out.println("A Choice-Based Romance Story Game");
        System.out.println("1. Start Game");
        System.out.println("2. Instructions");
        System.out.println("3. Exit");
    }

    private void startGame() {
        boolean playAgain;
        do {
            Player player = new Player(readName(), characters);
            showGameInstructions();
            waitForEnter();

            playScene(player, createAiraMeeting());
            playScene(player, createLianaMeeting());
            playRelationshipBranch(player);
            showMidGameMenu(player);

            RomanceCharacter finalChoice = chooseCharacter(player);
            showEnding(player, finalChoice);
            playAgain = readYesOrNo("\nPlay again? (y/n): ");
        } while (playAgain);
    }

    private String readName() {
        System.out.println("\n=== Enter Your Name ===");
        while (true) {
            System.out.print("What is your name? ");
            String name = readLine().trim();
            if (!name.isBlank()) {
                return name;
            }
            System.out.println("Please enter a name before starting.");
        }
    }

    private void showGameInstructions() {
        System.out.println("\n=== Game Instructions ===");
        System.out.println("- You will meet two romance characters.");
        System.out.println("- Choose your replies using the numbered menu.");
        System.out.println("- Your choices affect your relationship points with each character.");
        System.out.println("- At the end, you will choose one character.");
        System.out.println("- The ending depends on your choice and relationship points.");
        System.out.printf("- %d+ points gives a romance ending; %d-%d gives a friendship ending.%n",
                ROMANCE_THRESHOLD, FRIENDSHIP_THRESHOLD, ROMANCE_THRESHOLD - 1);
    }

    private void waitForEnter() {
        System.out.print("Press Enter to continue...");
        readLine();
    }

    
    
    }



    /**
     * Earlier dialogue changes the next story moment, not only the point total.
     */
    

    private void showMidGameMenu(Player player) {
        boolean talkedToAira = false;
        boolean talkedToLiana = false;

        while (true) {
            showRelationshipStatus(player);
            System.out.println("You can still talk to both of them.");
            System.out.println("Keep making choices and build your bond!");
            System.out.println("1. Talk to Aira");
            System.out.println("2. Talk to Liana");
            System.out.println("3. View Status");
            System.out.println("4. Continue");

            int choice = readNumberInRange("Enter your choice: ", 1, 4);
            switch (choice) {
                case 1 -> {
                    if (talkedToAira) {
                        System.out.println("You have already spent extra time with Aira.");
                    } else {
                        playScene(player, createAiraTalk());
                        talkedToAira = true;
                    }
                }
                case 2 -> {
                    if (talkedToLiana) {
                        System.out.println("You have already spent extra time with Liana.");
                    } else {
                        playScene(player, createLianaTalk());
                        talkedToLiana = true;
                    }
                }
                case 3 -> showRelationshipStatus(player);
                case 4 -> {
                    return;
                }
                default -> throw new IllegalStateException("Unexpected mid-game choice.");
            }
        }
    }

    private void showRelationshipStatus(Player player) {
        System.out.println("\n--- Check Relationship Points ---");
        for (Map.Entry<String, Integer> entry : player.getRelationshipPoints().entrySet()) {
            int points = entry.getValue();
            System.out.printf("%s: %d point%s%n", entry.getKey(), points,
                    Math.abs(points) == 1 ? "" : "s");
        }
    }

    private RomanceCharacter chooseCharacter(Player player) {
        System.out.println("\n--- Choose Your Character ---");
        System.out.println("Who do you want to be with?");
        System.out.println("1. Aira");
        System.out.println("2. Liana");

        int choice = readNumberInRange("Enter your choice: ", 1, 2);
        RomanceCharacter finalChoice = choice == 1 ? aira : liana;
        player.addPoints(finalChoice, 3);

        System.out.printf("You chose %s!%n", finalChoice.getName());
        System.out.println("The story will now continue based on your relationship with them...");
        System.out.printf("(+3 relationship points with %s)%n", finalChoice.getName());
        return finalChoice;
    }

    private void showEnding(Player player, RomanceCharacter finalChoice) {
        int points = player.getPoints(finalChoice);
        System.out.printf("%n=== Ending: %s ===%n", finalChoice.getName());

        if (points >= ROMANCE_THRESHOLD) {
            System.out.printf("Your choices showed that you truly care about %s. "
                    + "They feel happy and special with you.%n", finalChoice.getName());
            System.out.printf("%s: \"I'm so glad you chose me. You're really special to me.\"%n",
                    finalChoice.getName());
            printEndingScore(finalChoice, points);
            System.out.println("Good / Romance Ending");
            System.out.printf("You and %s start a new chapter together.%n", finalChoice.getName());
        } else if (points >= FRIENDSHIP_THRESHOLD) {
            System.out.printf("Your choices were mostly calm and kind. %s appreciates you, "
                    + "but feels there is still something to learn about each other.%n",
                    finalChoice.getName());
            System.out.printf("%s: \"You're a great person. I hope we can be closer someday.\"%n",
                    finalChoice.getName());
            printEndingScore(finalChoice, points);
            System.out.println("Okay / Friendship Ending");
            System.out.println("You remain friends, and there might be a chance in the future.");
        } else {
            System.out.printf("Your choices did not build enough trust with %s. "
                    + "They decide it is best to take a different path.%n", finalChoice.getName());
            System.out.printf("%s: \"Maybe it is better if we just stay friends.\"%n",
                    finalChoice.getName());
            printEndingScore(finalChoice, points);
            System.out.println("Bad / Independent Ending");
            System.out.println("You have time to reflect, and the story ends here for now.");
        }
    }

    private void printEndingScore(RomanceCharacter character, int points) {
        System.out.printf("Final Relationship Points with %s: %d%n", character.getName(), points);
    }

    private int readNumberInRange(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = readLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }
                System.out.printf("Please enter a number from %d to %d.%n", minimum, maximum);
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private boolean readYesOrNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = readLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input ended before the game could finish.");
        }
        return scanner.nextLine();
    }

    private enum ChoiceTone {
        WARM,
        HONEST,
        PLAYFUL,
        SUPPORTIVE,
        RESERVED,
        DISMISSIVE
    }

    private abstract static class RomanceCharacter {
        private final String name;
        private final String personality;

        RomanceCharacter(String name, String personality) {
            this.name = name;
            this.personality = personality;
        }

        String getName() {
            return name;
        }

        String getPersonality() {
            return personality;
        }

        abstract String respondTo(ChoiceTone tone);
    }

    private static final class Aira extends RomanceCharacter {
        Aira() {
            super("Aira", "thoughtful and quietly confident");
        }

        @Override
        String respondTo(ChoiceTone tone) {
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

    private static final class Liana extends RomanceCharacter {
        Liana() {
            super("Liana", "energetic and adventurous");
        }

        @Override
        String respondTo(ChoiceTone tone) {
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

    private static final class Player {
        private String name;
        private final Map<String, Integer> relationshipPoints = new LinkedHashMap<>();

        Player(String name, Collection<RomanceCharacter> characters) {
            setName(name);
            for (RomanceCharacter character : characters) {
                relationshipPoints.put(character.getName(), 0);
            }
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Player name cannot be blank.");
            }
            this.name = name.trim();
        }

        void addPoints(RomanceCharacter character, int points) {
            if (points < 0) {
                throw new IllegalArgumentException("Points to add cannot be negative.");
            }
            changePoints(character, points);
        }

        void removePoints(RomanceCharacter character, int points) {
            if (points < 0) {
                throw new IllegalArgumentException("Points to remove cannot be negative.");
            }
            changePoints(character, -points);
        }

        private void changePoints(RomanceCharacter character, int amount) {
            String characterName = character.getName();
            if (!relationshipPoints.containsKey(characterName)) {
                throw new IllegalArgumentException("Unknown character: " + characterName);
            }
            relationshipPoints.merge(characterName, amount, Integer::sum);
        }

        int getPoints(RomanceCharacter character) {
            Integer points = relationshipPoints.get(character.getName());
            if (points == null) {
                throw new IllegalArgumentException("Unknown character: " + character.getName());
            }
            return points;
        }

        Map<String, Integer> getRelationshipPoints() {
            return Collections.unmodifiableMap(relationshipPoints);
        }
    }

    private static final class Choice {
        private final int number;
        private final String text;
        private final RomanceCharacter character;
        private final int relationshipChange;
        private final ChoiceTone tone;
        private final String neutralOutcome;

        Choice(int number, String text, RomanceCharacter character, int relationshipChange,
                ChoiceTone tone, String neutralOutcome) {
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

        int getNumber() {
            return number;
        }

        String getText() {
            return text;
        }

        String applyTo(Player player) {
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
                    : String.format("%s relationship: %+d point%s.", character.getName(),
                    relationshipChange, Math.abs(relationshipChange) == 1 ? "" : "s");
            return character.respondTo(tone) + System.lineSeparator() + pointMessage;
        }
    }

    private static final class Scene {
        private final int sceneNumber;
        private final String title;
        private final String dialogue;
        private final List<Choice> choices;

        Scene(int sceneNumber, String title, String dialogue, List<Choice> choices) {
            if (choices == null || choices.isEmpty()) {
                throw new IllegalArgumentException("A scene needs at least one choice.");
            }
            this.sceneNumber = sceneNumber;
            this.title = title;
            this.dialogue = dialogue;
            this.choices = List.copyOf(choices);
        }

        void showScene(Player player) {
            System.out.printf("%n--- Scene %d: %s ---%n", sceneNumber, title);
            System.out.println(dialogue.replace("{player}", player.getName()));
        }

        void showChoices() {
            System.out.println("What will you say?");
            for (Choice choice : choices) {
                System.out.printf("%d. %s%n", choice.getNumber(), choice.getText());
            }
        }

        Choice getChoice(int number) {
            return choices.stream()
                    .filter(choice -> choice.getNumber() == number)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("That choice is not available."));
        }

        int getChoiceCount() {
            return choices.size();
        }
    }
}
