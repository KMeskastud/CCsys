module com.example.ccsys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ccsys to javafx.fxml;
    exports com.example.ccsys;
}