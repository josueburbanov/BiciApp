package app.josueburbano.com.biciapp.ui.registro;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import app.josueburbano.com.biciapp.datos.repos.ClienteRepository;
import app.josueburbano.com.biciapp.datos.modelos.Cliente;

public class RegistroViewModel extends ViewModel {
    private LiveData<Cliente> cliente;
    private ClienteRepository clientesRepo;

    public RegistroViewModel(ClienteRepository clientesRepo) {
        this.clientesRepo = clientesRepo;
        this.cliente = this.clientesRepo.getData();
    }

    //Para llamar
    public void crearCliente(Cliente cliente) {
        this.cliente = clientesRepo.postCliente(cliente);
    }

    //Para observar
    public LiveData<Cliente> getCliente() {
        return this.cliente;
    }

}
