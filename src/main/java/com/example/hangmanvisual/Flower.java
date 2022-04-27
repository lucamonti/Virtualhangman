package com.example.hangmanvisual;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Flower extends Pane {
    /**
     * FLOWER ===================================
     */
    Flower() {
        Group flower = new Group();

        Line stem_1 = new Line(150, 175, 150, 300);

//        Ellipse petal_base = new Ellipse(150,215,10,40);

        ArrayList<Ellipse> ellipses = new ArrayList<>();

        Ellipse petal_2 = new Ellipse(127, 207, 10, 40);
        petal_2.setRotate(36.0);
        ellipses.add(petal_2);

        Ellipse petal_3 = new Ellipse(112, 188, 10, 40);
        petal_3.setRotate(72.0);
        ellipses.add(petal_3);

        Ellipse petal_4 = new Ellipse(111, 165, 10, 40);
        petal_4.setRotate(102.0);
        ellipses.add(petal_4);

        Ellipse petal_5 = new Ellipse(125, 140, 10, 40);
        petal_5.setRotate(144.0);
        ellipses.add(petal_5);

        Ellipse petal_6 = new Ellipse(150, 135, 10, 40);
        ellipses.add(petal_6);

        Ellipse petal_7 = new Ellipse(175, 140, 10, 40);
        petal_7.setRotate(216.0);
        ellipses.add(petal_7);

        Ellipse petal_8 = new Ellipse(189, 165, 10, 40);
        petal_8.setRotate(252.0);
        ellipses.add(petal_8);

        Ellipse petal_9 = new Ellipse(188, 188, 10, 40);
        petal_9.setRotate(288.0);
        ellipses.add(petal_9);

        Ellipse petal_10 = new Ellipse(173, 207, 10, 40);
        petal_10.setRotate(324.0);
        ellipses.add(petal_10);

        for (Ellipse e : ellipses) {
            e.setFill(Color.TRANSPARENT);
            e.setStroke(Color.BLACK);
        }

        flower.getChildren().addAll(stem_1, petal_2, petal_3, petal_4, petal_5, petal_6, petal_7, petal_8, petal_9, petal_10);
        getChildren().add(flower);
    }

    public Node drawStem1() {
        Line stem_1 = new Line(150, 175, 150, 300);
        return stem_1;
    }

    public Node drawPetal2() {
        Ellipse petal_2 = new Ellipse(127, 207, 10, 40);
        petal_2.setRotate(36.0);
        return petal_2;
    }

    public Node drawPetal3() {
        Ellipse petal_3 = new Ellipse(112, 188, 10, 40);
        petal_3.setRotate(72.0);
        return petal_3;

    }

    public Node drawPetal4() {
        Ellipse petal_4 = new Ellipse(111, 165, 10, 40);
        petal_4.setRotate(102.0);
        return petal_4;
    }

    public Node drawPetal5() {
        Ellipse petal_5 = new Ellipse(125, 140, 10, 40);
        petal_5.setRotate(144.0);
        return petal_5;
    }

    public Node drawPetal6() {
        Ellipse petal_6 = new Ellipse(150, 135, 10, 40);
        return petal_6;
    }

    public Node drawPetal7() {
        Ellipse petal_7 = new Ellipse(175, 140, 10, 40);
        petal_7.setRotate(216.0);
        return petal_7;
    }

    public Node drawPetal8() {
        Ellipse petal_8 = new Ellipse(189, 165, 10, 40);
        petal_8.setRotate(252.0);
        return petal_8;
    }

    public Node drawPetal9() {
        Ellipse petal_9 = new Ellipse(188, 188, 10, 40);
        petal_9.setRotate(288.0);
        return petal_9;
    }

    public Node drawPetal10() {
        Ellipse petal_10 = new Ellipse(173, 207, 10, 40);
        petal_10.setRotate(324.0);
        return petal_10;
    }

}
