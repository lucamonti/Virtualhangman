package com.example.hangmanvisual;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Hangman extends Pane {
    /**
     * HANGMAN ===================================
     */
    Hangman() {
        Group hangman = new Group();

        Line base_1 = new Line(20, 250, 180, 250);

        Line stand_2 = new Line(40, 80, 40, 250);

        Line top_3 = new Line(40, 80, 160, 80);

        Line connect_4 = new Line(100, 80, 100, 100);

        Circle head_5 = new Circle(100, 120, 18);
        head_5.setFill(Color.TRANSPARENT);
        head_5.setStroke(Color.BLACK);

        Line body_6 = new Line(100, 140, 100, 210);

        Line leftArm_7 = new Line(100, 165, 135, 165);

        Line rightArm_8 = new Line(65, 165, 100, 165);

        Line leftLeg_9 = new Line(75, 240, 100, 210);

        Line rightLeg_10 = new Line(100, 210, 125, 240);

        hangman.getChildren().addAll(base_1, stand_2, top_3, connect_4, head_5, body_6, leftArm_7, rightArm_8, leftLeg_9, rightLeg_10);

        getChildren().add(hangman);
    }

    public Node drawBase1() {
        Line base_1 = new Line(20, 250, 180, 250);
        return base_1;
    }

    public Node drawStand2() {
        Line stand_2 = new Line(40, 80, 40, 250);
        return stand_2;
    }

    public Node drawTop3() {
        Line top_3 = new Line(40, 80, 160, 80);
        return top_3;
    }

    public Node drawConnect4() {
        Line connect_4 = new Line(100, 80, 100, 100);
        return connect_4;
    }

    public Node drawHead5() {
        Circle head_5 = new Circle(100, 120, 18);
        head_5.setFill(Color.TRANSPARENT);
        head_5.setStroke(Color.BLACK);
        return head_5;
    }

    public Node drawBody6() {
        Line body_6 = new Line(100, 140, 100, 210);
        return body_6;
    }

    public Node drawLeftArm7() {
        Line leftArm_7 = new Line(100, 165, 135, 165);
        return leftArm_7;
    }

    public Node drawRightArm8() {
        Line rightArm_8 = new Line(65, 165, 100, 165);
        return rightArm_8;
    }

    public Node drawLeftLeg9() {
        Line leftLeg_9 = new Line(75, 240, 100, 210);
        return leftLeg_9;
    }

    public Node drawRightLeg10() {
        Line rightLeg_10 = new Line(100, 210, 125, 240);
        return rightLeg_10;
    }


}
