package app.josueburbano.com.biciapp.ui;



import android.support.annotation.Nullable;


public class LoginResultado {
    @Nullable
    private LoginClienteView success;
    @Nullable
    private Integer error;

    LoginResultado(@Nullable Integer error) {
        this.error = error;
    }

    LoginResultado(@Nullable LoginClienteView success) {
        this.success = success;
    }

    @Nullable
    LoginClienteView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}


