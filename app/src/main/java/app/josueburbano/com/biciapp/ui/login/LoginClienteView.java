package app.josueburbano.com.biciapp.ui.login;

import java.io.Serializable;

public class LoginClienteView implements Serializable {
    private String nombreDisplay;
    private String id;
    LoginClienteView(String displayName, String id) {
        this.nombreDisplay = displayName;
        this.id = id;
    }
    String getDisplayName() {
        return nombreDisplay;
    }
    public String getId(){return id;}
}
