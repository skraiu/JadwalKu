module com.mycompany.jadwalku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.jadwalku to javafx.fxml;
    exports com.mycompany.jadwalku;
}
