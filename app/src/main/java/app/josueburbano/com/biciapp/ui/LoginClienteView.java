package app.josueburbano.com.biciapp.ui;

import java.io.Serializable;

public class LoginClienteView implements Serializable {
    private String nombreDisplay;
    LoginClienteView(String displayName) {
        this.nombreDisplay = displayName;
    }
    String getDisplayName() {
        return nombreDisplay;
    }
}
