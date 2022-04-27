module com.example.hangmanvisual {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hangmanvisual to javafx.fxml;
    exports com.example.hangmanvisual;
}