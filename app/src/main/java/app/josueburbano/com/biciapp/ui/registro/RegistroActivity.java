package app.josueburbano.com.biciapp.ui.registro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.josueburbano.com.biciapp.R;
import app.josueburbano.com.biciapp.datos.modelos.Cliente;
import app.josueburbano.com.biciapp.ui.map_estaciones.MapsViewModel;
import app.josueburbano.com.biciapp.ui.map_estaciones.MapsViewModelFactory;

public class RegistroActivity extends AppCompatActivity {

    EditText editTextNombre;
    EditText editTextApellidos;
    EditText editTextUsuario;
    EditText editTextPassw;
    EditText editTextPasswConf;
    EditText editTextEmail;
    EditText editTextTelefono;
    EditText editTextCedula;
    RegistroViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        viewModel = ViewModelProviders.of(this, new RegistroViewModelFactory())
                .get(RegistroViewModel.class);

        viewModel.getCliente().observe(this, new Observer<Cliente>() {
            @Override
            public void onChanged(@Nullable Cliente cliente) {
                if(cliente != null){
                    Toast.makeText(getApplicationContext(), "Registro exitoso! ", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Hubo un problema al registrarse ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void postCliente(View view){

        Cliente cliente = new Cliente();
        cliente.setNombre(editTextNombre.getText().toString()+" "+editTextApellidos.getText().toString());
        cliente.setCedula(editTextCedula.getText().toString());
        cliente.setCorreoElectronico(editTextEmail.getText().toString());
        cliente.setTelefono(editTextTelefono.getText().toString());
        cliente.setPassw(editTextPasswConf.getText().toString());
        cliente.setUsuario(editTextUsuario.getText().toString());

        viewModel.crearCliente(cliente);





    }
}
