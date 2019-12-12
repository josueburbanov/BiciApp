package app.admin.com.biciapp.ui.login;

import java.io.Serializable;

public class LoginClienteView implements Serializable {
    private String nombreDisplay;
    private String id;
    private String rfid;
    LoginClienteView(String displayName, String id, String rfid) {
        this.nombreDisplay = displayName;
        this.id = id;
        this.rfid = rfid;
    }
    String getDisplayName() {
        return nombreDisplay;
    }
    public String getId(){return id;}
    public String getRfid(){return rfid;}
    public String getNombreDisplay(){return nombreDisplay;}
}
