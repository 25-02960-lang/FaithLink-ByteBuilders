import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Starts FaithLink and controls the console menu, story flow, and endings.
 * Every romance route is available to every player.
 */
public final class Main {
    private static final int FRIENDSHIP_THRESHOLD = 8;
    private static final int ROMANCE_THRESHOLD = 15;

    private final Scanner scanner = new Scanner(System.in);
    private final RomanceCharacter aira = new Aira();
    private final RomanceCharacter liana = new Liana();
    private final RomanceCharacter kai = new Kai();
    private final RomanceCharacter noah = new Noah();
    private final List<RomanceCharacter> characters = List.of(aira, liana, kai, noah);

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
            playScene(player, createKaiMeeting());
            playScene(player, createNoahMeeting());
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
        System.out.println("- You will meet four romance characters.");
        System.out.println("- Choose your replies using the numbered menu.");
        System.out.println("- Your choices affect your relationship points with each character.");
        System.out.println("- Every romance route is open to every player.");
        System.out.println("- The ending depends on your choice and relationship points.");
