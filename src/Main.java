import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Starts FaithLink and controls the console menu, story flow, and endings.
 */
public final class Main {
    private static final int FRIENDSHIP_THRESHOLD = 8;
    private static final int ROMANCE_THRESHOLD = 15;

    private final Scanner scanner = new Scanner(System.in);
    private final RomanceCharacter aira = new Aira();
    private final RomanceCharacter liana = new Liana();
    private final List<RomanceCharacter> characters = List.of(aira, liana);

    public static void main(String[] args) {
        new Main().run();
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

    private void playScene(Player player, Scene scene) {
        scene.showScene(player);
        scene.showChoices();
        int selectedNumber = readNumberInRange("Your reply: ", 1, scene.getChoiceCount());
        System.out.println(scene.getChoice(selectedNumber).applyTo(player));
    }

    private Scene createAiraMeeting() {
        return new Scene(
                1,
                "Meet Aira",
                "You see a girl sitting alone near the school garden.\n"
                        + "She looks at you and smiles shyly.\n"
                        + "Aira: \"Hi... I'm Aira. You're new here, right?\"",
                List.of(
                        new Choice(1, "\"Yeah, I'm new. Nice to meet you!\"", aira, 5,
                                ChoiceTone.WARM, ""),
                        new Choice(2, "\"Um... hi.\"", aira, 3,
                                ChoiceTone.RESERVED, ""),
                        new Choice(3, "\"Do you need help with something?\"", aira, 4,
                                ChoiceTone.SUPPORTIVE, "")));
    }

    private Scene createLianaMeeting() {
        return new Scene(
                2,
                "Meet Liana",
                "Later, you see another girl in the library.\n"
                        + "She's reading a book and looks up at you.\n"
                        + "Liana: \"Oh, hi! Do you also like reading?\"",
                List.of(
                        new Choice(1, "\"Yeah, I love reading too!\"", liana, 5,
                                ChoiceTone.HONEST, ""),
                        new Choice(2, "\"Not really, but it's nice.\"", liana, 3,
                                ChoiceTone.RESERVED, ""),
                        new Choice(3, "\"What book are you reading?\"", liana, 4,
                                ChoiceTone.WARM, "")));
    }

    /**
     * Earlier dialogue changes the next story moment, not only the point total.
     */
    private void playRelationshipBranch(Player player) {
        int airaPoints = player.getPoints(aira);
        int lianaPoints = player.getPoints(liana);

        if (airaPoints > lianaPoints) {
            playScene(player, new Scene(
                    3,
                    "Aira's Invitation",
                    "Because of your earlier conversation, Aira finds {player} in the garden.\n"
                            + "Aira: \"I was hoping we could talk a little more.\"",
                    List.of(
                            new Choice(1, "\"I'd like that. Tell me about your favorite flowers.\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "You spend a quiet moment getting to know Aira better."),
                            new Choice(2, "\"Sure. How has your day been?\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "Aira shares a little about their day before the bell rings."),
                            new Choice(3, "\"Maybe another time.\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "Aira nods and gives you space."))));
        } else if (lianaPoints > airaPoints) {
            playScene(player, new Scene(
                    3,
                    "Liana's Invitation",
                    "Because of your earlier conversation, Liana waves {player} over in the library.\n"
                            + "Liana: \"I saved a seat for you. Want to see what I found?\"",
                    List.of(
                            new Choice(1, "\"Absolutely. Show me what you're reading.\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "You join Liana and discover a book neither of you can put down."),
                            new Choice(2, "\"Sure. What caught your attention?\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "Liana happily explains why the book matters to them."),
                            new Choice(3, "\"Not right now.\"", null, 0,
                                    ChoiceTone.RESERVED,
                                    "Liana puts the book away and says maybe later."))));
        } else {
            playScene(player, new Scene(
                    3,
                    "A Shared Moment",
                    "Your first conversations leave both Aira and Liana curious about {player}.\n"
                            + "They invite you to join them near the courtyard fountain.",
                    List.of(
                            new Choice(1, "Join them and listen to their stories.", null, 0,
                                    ChoiceTone.RESERVED,
                                    "The three of you trade stories until the next class begins."),
                            new Choice(2, "Ask what they enjoy most about the school.", null, 0,
                                    ChoiceTone.RESERVED,
                                    "Their answers give you a glimpse of two very different personalities."),
                            new Choice(3, "Say goodbye and promise to talk later.", null, 0,
                                    ChoiceTone.RESERVED,
                                    "You part on friendly terms and look forward to seeing them again."))));
        }
    }

    private Scene createAiraTalk() {
        return new Scene(
                4,
                "Garden Conversation",
                "Aira is watering the garden after class. They seem happy to see {player}.\n"
                        + "Aira: \"Would you like to help me choose a flower to plant?\"",
                List.of(
                        new Choice(1, "\"Of course. Let's choose one together.\"", aira, 7,
                                ChoiceTone.SUPPORTIVE, ""),
                        new Choice(2, "\"Sure, that sounds nice.\"", aira, 3,
                                ChoiceTone.WARM, ""),
                        new Choice(3, "\"That sounds boring. I'll pass.\"", aira, -3,
                                ChoiceTone.DISMISSIVE, "")));
    }

    private Scene createLianaTalk() {
        return new Scene(
                5,
                "Library Conversation",
                "Liana waves from a quiet table covered with books.\n"
                        + "Liana: \"I found a poem I think you might like. Want to read it with me?\"",
                List.of(
                        new Choice(1, "\"Yes. Read it with me.\"", liana, 7,
                                ChoiceTone.HONEST, ""),
                        new Choice(2, "\"Sure, I can stay for a little while.\"", liana, 3,
                                ChoiceTone.WARM, ""),
                        new Choice(3, "\"No, I don't have time for that.\"", liana, -3,
                                ChoiceTone.DISMISSIVE, "")));
    }

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
}
