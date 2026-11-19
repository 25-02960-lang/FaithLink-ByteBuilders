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
                                ChoiceTone.SUPPORTIVE, "")));    private Scene createLianaMeeting() {
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
    }private void playRelationshipBranch(Player player) {
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
    }private void playRelationshipBranch(Player player) {
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
