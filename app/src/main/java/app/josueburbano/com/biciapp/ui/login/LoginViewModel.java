package app.josueburbano.com.biciapp.ui.login;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.ClienteRepository;
import app.josueburbano.com.biciapp.datos.modelos.Cliente;


public class LoginViewModel extends ViewModel {
    private LiveData<Cliente> cliente;
    private ClienteRepository clienteRepo;
    private MutableLiveData<LoginResultado> loginResultado = new MutableLiveData<>();
    private MutableLiveData<LoginActivityEstado> loginActvEstado = new MutableLiveData<>();

    LiveData<LoginActivityEstado> getLoginActivityEstado() {
        return loginActvEstado;
    }

    LiveData<LoginResultado> getLoginResult() {
        return loginResultado;
    }

    public LoginViewModel(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
        //Trae el cliente MLD del repositorio
        cliente = clienteRepo.getData();
    }

    public void login(String usuario, String passw) {
        cliente = clienteRepo.getCliente(usuario, passw);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginActvEstado.setValue(new LoginActivityEstado(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginActvEstado.setValue(new LoginActivityEstado(null, R.string.invalid_password));
        } else {
            loginActvEstado.setValue(new LoginActivityEstado(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public LiveData<Cliente> getCliente() {
        return this.cliente;
    }

    public void updateLoginActivityEstado(Cliente cliente) {
        if(cliente != null){
            loginResultado.setValue(new LoginResultado(new LoginClienteView(cliente.getUsuario(), cliente.getId())));
        }else{
            loginResultado.setValue(new LoginResultado(R.string.login_failed));
        }
    }
}
