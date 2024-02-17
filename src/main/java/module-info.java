module Connect4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires com.fazecast.jSerialComm;

    opens Connect4x to javafx.fxml;
    exports Connect4;
}