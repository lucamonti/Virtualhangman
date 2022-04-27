package com.example.hangmanvisual;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//player class
class User {
    private String userName;
    private double pointCount;

    public User(String userName, double pointCount) {
        this.userName = userName;
        this.pointCount = pointCount;
    }

    public String getUserName() {
        return userName;
    }

    public double getPointCount() {
        return pointCount;
    }
}


public class main extends Application {
    /**
     * Layout (shapes & controls) for scenes & windows of application
     */
    //hangman & flower stick figure objects
    private final Hangman hangman = new Hangman();
    private final Flower flower = new Flower();

    //shapes & controls for welcome scene
    Label titleLbl = new Label("Virtual Hangman");
    Button multiplayerBtn = new Button("Multiplayer");
    Button soloBtn = new Button("Solo");
    CheckBox PGModeChk = new CheckBox("PG mode");

    //quit button
    Button quitBtn = new Button("Quit");

    //language choices drop-down
    private String[] languages = {"English", "French", "Spanish", "Italian"};
    private ComboBox<String> languageCboBx = new ComboBox<>();
    Text pickLang = new Text("Pick a language: ");

    //shapes & controls for solo name input scene
    Text soloMsg = new Text(50, 50, "Enter your name:");
    TextField enterUserNameTF = new TextField();
    Text userNameText = new Text();

    //shapes & controls for multiplayer word input scene
    Text multiMsg = new Text(50, 50, "Enter a word (up to 10 letters):");
    TextField enterGuessWordTF = new TextField();
    Text guessWordText = new Text();

    //enter text to submit user input
    Text enterInput = new Text(20, 250, "Click ENTER to submit your input");
    Text enterLetter = new Text(20, 250, "Type your chosen letter");

    //shapes for stick figure word guessing layout
    Text scoreText = new Text("Score: ");
    Text lettersUsedText = new Text("Letters used: ");

    //guessing word to string in scene
    Text wordGuessInScene = new Text();

    //Error input label
    Label errorLengthLbl = new Label("ERROR: USE 10 LETTERS MAX");
    Label errorInputLbl = new Label("ERROR: USE LETTERS ONLY");

    //restart button
    Button restartBtn = new Button("Restart");

    //player guesses
    List<Character> userCharGuesses = new ArrayList<>();

    //table for scoreboard
    TableView scoreTable = new TableView();
    private static List<User> scoreBoard = new ArrayList<>();

    //mask boolean array
    boolean[] mask;

    // method times how long (in s) it takes for user to input a letter
    public double getTimeForAttempt() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter char to start time");
        char s = scan.next().charAt(0);
        long startTime = System.currentTimeMillis();

        System.out.println("Enter char to end time");
        char e = scan.next().charAt(0);
        long endTime = System.currentTimeMillis();

        double timeInSeconds = (endTime - startTime) / 1000.0;
        return timeInSeconds;
    }


    /**
     * Variable initialization
     */
    final int MAX_ATTEMPTS = 10;
    int attemptCount = 0;

    ArrayList<Character> inputChars = new ArrayList<>();
    ArrayList<Character> wrongChars = new ArrayList<>();

    //String form of user inputted word (Text)
    String wordToGuess = enterGuessWordTF.getText();
    String chosenRandomWord;

    double pointCount = 0;


    // method to generate a random word from the chosen input file
    public static String chooseRandomWord(File inputFile) {
        String randomWord = "";
        try {
            Scanner fileScan = new Scanner(inputFile);
            ArrayList<String> words = new ArrayList<>();
            while (fileScan.hasNext()) {
                words.add(fileScan.nextLine());
            }
            Random rand = new Random();
            randomWord = words.get(rand.nextInt(words.size()));
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return randomWord;
    }


    /**
     * Methods
     */
    //method to restart entire game
    public void restartGame() {
        wordToGuess = "";
        pointCount = 0;
        attemptCount = 10;
        userCharGuesses.clear();
    }

    //method to check if input char is contained in word
    public boolean[] isCharInWord(char c) {
       // boolean isWrongGuess = true;
        boolean[] inputCharArr = new boolean[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == c){
                inputCharArr[i] = true;
                //isWrongGuess = false;
            }
        }
//        if (isWrongGuess == true) {
//
//            attemptCount++;
//        }
        return inputCharArr;
    }

//    public char[] returnWord(boolean[] input) {
//        char[] charArray = new char[input.length];
//        for (int i = 0; i < input.length; i++) {
//            if (input[i] = true) {
//                charArray[i] = wordToGuess.charAt(i);
//            }
//        }
//        return charArray;
//    }

    // method calculates the final score
    public double getFinalScore(int pointCount, String wordToGuess) {
        return pointCount / wordToGuess.length();
    }

    // method sets an input ___, the same as input word length, in the stick figure scene
    public String setUpWordToGuess(String wordToGuess, boolean[] filt) {
        StringBuilder emptyWord = new StringBuilder();
        for (int i = 0; i < wordToGuess.length(); i++) {
            if(!filt[i]) {
                emptyWord.append('_');
            } else {
                emptyWord.append(wordToGuess.charAt(i));
            }
            emptyWord.append(' ');
        }
        return emptyWord.toString();
    }

    // method to get current word
    public String getCurrentWord(String wordToGuess) {
        String chosenWord = chooseRandomWord(new File("words.txt"));
        String currentWord = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            if (userCharGuesses.contains(chosenWord.charAt(i))) {
                currentWord = wordToGuess.replace(wordToGuess.charAt(i), chosenWord.charAt(i));
            } else {
                currentWord = wordToGuess.replace(wordToGuess.charAt(i), '_');
            }
        }
        return currentWord;
    }

    public String getCurrentWord() {
        String currentWord = "";
        for (char c : wordToGuess.toCharArray()) {
            if (inputChars.contains(c)) {
                currentWord += c + " ";
            } else {
                currentWord += "_ ";
            }
        }
        return currentWord;
    }


    //point allocation method
//    public int getGuesses() {
//        for (char c : inputChars) {
//            //if wrong guess
//            if (wordToGuess.indexOf(c) == -1) {
//                attemptCount++;
//                wrongChars.add(c);
//                //if right guess
//            } else {
//                ///POINT SYSTEM HERE
//                // 0s - 5s
//                pointCount += 2;
//                // 6s - 15s
//                pointCount += 1;
//                // 16s - 30s
//                pointCount += 0.5;
//                // 31s+
//                attemptCount += 1;
//            }
//        }
//        return attemptCount;
//    }


    // method returns true if game is won
    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!inputChars.contains(c)) {
                return false;
            }
        }
        return true;
    }

    // method returns true if number of attempts has been exceeded ie game is lost
    public boolean isGameLost() {
        return attemptCount > MAX_ATTEMPTS;
    }

    // method checks if game is over - sets primaryStage to winning scene if game is won, else sets primaryStage to losing scene if game is lost
    public void isGameOver() {
        if (isGameLost()) {
            setLosingScene();
        } else if (isGameWon()) {
            setWinningScene();
        }
    }

    // method checks if inputted char is valid ie a letter
    public static boolean isValidChar(char c) {
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    // method checks if inputted String is valid ie all letters
    public boolean invalidString(String ui) {
        boolean validity = false;
        for (char c : ui.toCharArray()) {
            if (!isValidChar(c)) {
                validity = true;
            } else {
                validity = false;
            }
        }
        return validity;
    }

    // method sets winning scene
    public Scene setWinningScene() {
        VBox winningLayout = new VBox();

        winningLayout.setSpacing(20);
        winningLayout.setPadding(new Insets(20));

        Text winningText = new Text("CONGRATULATIONS! You guessed the word!");
        Text finalScoreText = new Text("Your final score is: ");
        Image iceCreamImg = new Image("file:iceCream.jpg");
        ImageView viewIceCream = new ImageView(iceCreamImg);
        viewIceCream.setFitHeight(200);
        viewIceCream.setFitWidth(200);

        if (isGameWon()) {
            winningLayout.getChildren().add(scoreTable);
        }

        winningLayout.getChildren().addAll(restartBtn, winningText, finalScoreText, viewIceCream);
        Scene winningScene = new Scene(winningLayout, 500, 350);
        return winningScene;
    }

    // method sets losing scene
    public Scene setLosingScene() {
        VBox losingLayout = new VBox();

        losingLayout.setSpacing(20);
        losingLayout.setPadding(new Insets(20));

        Text losingText = new Text("FAIL! You lose!");
        Text finalScoreText = new Text("Your final score is: ");
        Image skullImg = new Image("file:skull.jpg");
        ImageView viewSkull = new ImageView(skullImg);
        viewSkull.setFitHeight(200);
        viewSkull.setFitWidth(200);

        if (isGameLost()) {
            losingLayout.getChildren().add(scoreTable);
        }

        losingLayout.getChildren().addAll(restartBtn, losingText, finalScoreText, viewSkull);
        Scene losingScene = new Scene(losingLayout, 500, 350);
        return losingScene;
    }

    // method converts letter input into CAPS, otherwise throws error if input is invalid (ie numbers/symbols)
    public static void standardizeChar(char inputChar) {
        int encodedChar;
        char finalChar = inputChar;
        String wordToGuess = "";

        if (inputChar >= 97 && inputChar <= 122) {
            encodedChar = inputChar - 126;
            finalChar = (char) (encodedChar);
        } else {
            if (isValidChar(inputChar)) {
                finalChar = inputChar;
            } else try {
                if (inputChar < 97 || inputChar > 123) {
                    throw new RuntimeException("ERROR: USE LETTERS ONLY");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        wordToGuess += finalChar;
    }

    //method to check that input is max 10 chars long
    public boolean inputTooLong(String ui) {
        if (ui.length() > 10) {
            return true;
        } else {
            return false;
        }
    }

    //method to update scoreboard
    public void updateScoreBoard() {
        scoreTable.getItems().clear();
        for (User p : scoreBoard) {
            scoreTable.getItems().add(p);
        }
    }

    //method for scoreboard
    public static void addScores(User u) {
        int boardSize = scoreBoard.size();
        int index = 0;
        if (scoreBoard.isEmpty()) {
            scoreBoard.add(new User(u.getUserName(), u.getPointCount()));
        } else {
            scoreBoard.add(index, new User(u.getUserName(), u.getPointCount()));
        }
    }

    // method draws a part of the Hangman (standard mode) for every attempt (ie input of letter not contained in the word)
    public void drawHangman(int attemptCount) {
        Hangman hangman = new Hangman();
        switch (attemptCount) {
            case 1:
                hangman.drawBase1();
                break;
            case 2:
                hangman.drawStand2();
                break;
            case 3:
                hangman.drawTop3();
                break;
            case 4:
                hangman.drawConnect4();
                break;
            case 5:
                hangman.drawHead5();
                break;
            case 6:
                hangman.drawBody6();
                break;
            case 7:
                hangman.drawLeftArm7();
                break;
            case 8:
                hangman.drawRightArm8();
                break;
            case 9:
                hangman.drawLeftLeg9();
                break;
            case 10:
                hangman.drawRightLeg10();
                break;
        }
    }

    // method draws a part of the Flower (PG mode) for every attempt (ie input of letter not contained in the word)
    public void drawFlower(int attemptCount) {
        Flower flower = new Flower();
        switch (attemptCount) {
            case 1:
                flower.drawStem1();
                break;
            case 2:
                flower.drawPetal2();
                break;
            case 3:
                flower.drawPetal3();
                break;
            case 4:
                flower.drawPetal4();
                break;
            case 5:
                flower.drawPetal5();
                break;
            case 6:
                flower.drawPetal6();
                break;
            case 7:
                flower.drawPetal7();
                break;
            case 8:
                flower.drawPetal8();
                break;
            case 9:
                flower.drawPetal9();
                break;
            case 10:
                flower.drawPetal10();
                break;
        }
    }


    @Override
    public void start(Stage primaryStage) {
        //scoreboard
        TableColumn<String, User> cl1 = new TableColumn<>("Username");
        cl1.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Integer, User> cl2 = new TableColumn<>("Score");
        cl2.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreTable.getColumns().add(cl1);
        scoreTable.getColumns().add(cl2);

//         addScores(new Players(userNameText.getText()), pointCount);


        /**
         * Setting scenes and adding elements to scenes for application
         */
        //formatting shapes & controls
        VBox quitBtnLayout = new VBox();
        quitBtnLayout.setSpacing(20);
        quitBtnLayout.setPadding(new Insets(20));
        quitBtnLayout.getChildren().add(quitBtn);
        quitBtnLayout.setAlignment(Pos.CENTER_LEFT);

        VBox errorLayout = new VBox();
        errorLayout.setSpacing(20);
        errorLayout.setPadding(new Insets(20));
        errorLayout.setAlignment(Pos.CENTER);
        errorInputLbl.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 24px;");
        errorLengthLbl.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 24px;");

        // Welcome scene
        VBox mainLayout = new VBox();
        TilePane buttonsPane = new TilePane();
        HBox langLayout = new HBox();

        mainLayout.setSpacing(20);
        mainLayout.setPadding(new Insets(20));
        langLayout.setSpacing(20);
        langLayout.setPadding(new Insets(20));

        ObservableList<String> items = FXCollections.observableArrayList(languages);
        languageCboBx.getItems().addAll(items);
        langLayout.getChildren().addAll(pickLang, languageCboBx);

        titleLbl.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 30));

//        titleLbl.setStyle("-fx-border-color: red; -fx-border-width: 2");
//        titleLbl.setContentDisplay(ContentDisplay.CENTER);

        mainLayout.setAlignment(Pos.CENTER);
        buttonsPane.setAlignment(Pos.CENTER);
        langLayout.setAlignment(Pos.CENTER);

        //System.out.println(getTimeForAttempt());

        mainLayout.getChildren().addAll(titleLbl, buttonsPane, PGModeChk, langLayout);
        buttonsPane.getChildren().addAll(multiplayerBtn, soloBtn);

        Scene welcomeScene = new Scene(mainLayout, 500, 350);


        // Solo scene with name input
        VBox soloLayout = new VBox();

        soloLayout.setSpacing(20);
        soloLayout.setPadding(new Insets(20));

        soloLayout.setAlignment(Pos.CENTER);

        soloLayout.getChildren().addAll(soloMsg, enterUserNameTF, enterInput, errorLayout);
        Scene soloScene = new Scene(soloLayout, 500, 350);


        // Multi scene with word input
        VBox multiLayout = new VBox();

        multiLayout.setSpacing(20);
        multiLayout.setPadding(new Insets(20));

        multiLayout.setAlignment(Pos.CENTER);

        multiLayout.getChildren().addAll(quitBtnLayout, multiMsg, enterGuessWordTF, enterInput, errorLayout);
        Scene multiScene = new Scene(multiLayout, 500, 350);


        // Stick figure scene:
        //      - build Hangman by default (ie standard mode)
        //      - build Flower if PG mode checkbox is ticked
        HBox mainGameHBox = new HBox();
        mainGameHBox.setSpacing(20);
        mainGameHBox.setPadding(new Insets(20));
        mainGameHBox.setAlignment(Pos.CENTER);

        VBox stickFigureVBox = new VBox();
        stickFigureVBox.setSpacing(20);
        stickFigureVBox.setPadding(new Insets(20));

        HBox timeHBox = new HBox();
        timeHBox.setSpacing(20);
        timeHBox.setPadding(new Insets(20));

        VBox infoVBox = new VBox();
        infoVBox.setSpacing(20);
        infoVBox.setPadding(new Insets(20));
        infoVBox.setAlignment(Pos.CENTER);

        errorInputLbl.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 24px;");

        infoVBox.getChildren().addAll(quitBtnLayout, scoreText, lettersUsedText, wordGuessInScene, enterLetter);
        mainGameHBox.getChildren().addAll(stickFigureVBox, infoVBox);

        Scene stickFigureScene = new Scene(mainGameHBox, 500, 350);


        /**
         * Setting event handler actions
         */
        // in solo mode:
        //      - build Flower in stickFigure scene if PG mode checkbox is ticked
        //      - build Hangman in stickFigure scene by default (ie standard mode)
        soloBtn.setOnAction(e -> {
            if (PGModeChk.isSelected()) {
                stickFigureVBox.getChildren().add(flower);
            } else {
                stickFigureVBox.getChildren().add(hangman);
            }
            primaryStage.setScene(soloScene);
        });

        //choose random word from corresponding file based on language chosen by user
        languageCboBx.setOnAction(e -> {
            if (languageCboBx.getValue().equals(languages[0])) { //english
                chosenRandomWord = chooseRandomWord(new File("words.txt"));
            }
            if (languageCboBx.getValue().equals(languages[1])) { //french
                chosenRandomWord = chooseRandomWord(new File("words.txt"));
            }
            if (languageCboBx.getValue().equals(languages[2])) { //spanish
                chosenRandomWord = chooseRandomWord(new File("words.txt"));
            }
            if (languageCboBx.getValue().equals(languages[3])) { //italian
                chosenRandomWord = chooseRandomWord(new File("words.txt"));
            }
        });

        quitBtn.setOnMouseClicked(e -> {
            primaryStage.setScene(setLosingScene());
            scoreText.setText("" + pointCount);
            addScores(new User(enterUserNameTF.getText(), pointCount));
        });

        // receive name input from user in TextField
        enterUserNameTF.setOnAction(e -> {
            userNameText.setText(enterUserNameTF.getText());
            errorLayout.getChildren().removeAll(errorInputLbl, errorLengthLbl);
        });

        // receive word input from user in TextField
        enterGuessWordTF.setOnAction(e -> {
            guessWordText.setText(enterGuessWordTF.getText());
            errorLayout.getChildren().removeAll(errorInputLbl, errorLengthLbl);
        });

        // if multiplayer mode is chosen:
        //      - build Flower in stickFigure scene if PG mode checkbox is ticked
        //      - build Hangman in stickFigure scene by default (ie standard mode)
        multiplayerBtn.setOnAction(e -> {
            if (PGModeChk.isSelected()) {
                stickFigureVBox.getChildren().add(flower);
            } else {
                stickFigureVBox.getChildren().add(hangman);
            }
            primaryStage.setScene(multiScene);
        });

        // in solo mode, redirect to stickFigureScene when ENTER key is pressed after entering name
        enterUserNameTF.setOnKeyPressed(e -> {
            for (char c : wordToGuess.toCharArray()) {
                if (!isValidChar(c)) {
                    stickFigureVBox.getChildren().add(errorInputLbl);
                }
            }
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (!(inputTooLong("" + userNameText.getText())) && !(invalidString(("" + userNameText.getText())))) {
                    userNameText.setText(enterGuessWordTF.getText());
                    wordGuessInScene.setText(setUpWordToGuess("" + userNameText.getText(), mask));
                    primaryStage.setScene(stickFigureScene);
                    long startTime = System.currentTimeMillis();
                } else {
                    if (inputTooLong("" + userNameText.getText())) {
                        errorLayout.getChildren().add(errorLengthLbl);
                        primaryStage.setScene(multiScene);
                    }
                    if (invalidString(("" + userNameText.getText()))) {
                        errorLayout.getChildren().add(errorInputLbl);
                        primaryStage.setScene(multiScene);
                    }
                }
                if (isGameWon()) {
                    setWinningScene();
                } else if (isGameLost()) {
                    setLosingScene();
                }
            }
        });

        // in multiplayer mode, redirect to stickFigureScene when ENTER key is pressed after entering word
        enterGuessWordTF.setOnKeyPressed(e -> {
            for (char c : wordToGuess.toCharArray()) {
                if (!isValidChar(c)) {
                    stickFigureVBox.getChildren().add(errorInputLbl);
                }
            }
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (!(inputTooLong("" + guessWordText.getText())) && !(invalidString(("" + guessWordText.getText())))) {
                    guessWordText.setText(enterGuessWordTF.getText());
                    wordGuessInScene.setText(setUpWordToGuess("" + userNameText.getText(), mask));
                    primaryStage.setScene(stickFigureScene);
                    long startTime = System.currentTimeMillis();
                } else {
                    if (inputTooLong("" + guessWordText.getText())) {
                        errorLayout.getChildren().add(errorLengthLbl);
                        primaryStage.setScene(multiScene);
                    }
                    if (invalidString(("" + guessWordText.getText()))) {
                        errorLayout.getChildren().add(errorInputLbl);
                        primaryStage.setScene(multiScene);
                    }
                }
            }
        });

        restartBtn.setOnMouseClicked(e -> {
            primaryStage.setScene(welcomeScene);
            restartGame();
        });


        // basic format
        primaryStage.setTitle("VirtualHangman");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

}