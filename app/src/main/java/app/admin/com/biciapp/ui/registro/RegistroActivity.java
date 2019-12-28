package app.admin.com.biciapp.ui.registro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.admin.com.biciapp.R;
import app.admin.com.biciapp.datos.modelos.Cliente;

public class RegistroActivity extends AppCompatActivity {

    EditText editTextNombre;
    EditText editTextApellidos;
    EditText editTextUsuario;
    EditText editTextPassw;
    EditText editTextPasswConf;
    EditText editTextEmail;
    EditText editTextTelefono;
    EditText editTextCedula;
    EditText editTextDireccion;
    RegistroViewModel viewModel;
    private boolean error = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassw = findViewById(R.id.editTextPassw);
        editTextPasswConf = findViewById(R.id.editTextPasswConf);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextCedula = findViewById(R.id.editTextCedula);
        editTextDireccion = findViewById(R.id.editTextDireccion);

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

        editTextCedula.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!validadorDeCedula(editTextCedula.getText().toString())){
                    editTextCedula.setError("Cédula inválida");
                    error = true;
                }else{
                    error = false;
                }

            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isEmailValid(editTextEmail.getText().toString())){
                    editTextEmail.setError("Email inválido");
                    error = true;
                }else{
                    error = false;
                }

            }
        });

        editTextPassw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextPassw.getText().toString().length() < 5){
                    editTextPassw.setError("Longitud mínima < 5");
                    error = true;
                }else{
                    error = false;
                }

            }
        });

        editTextPasswConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextPasswConf.getText().toString().length() < 5){
                    editTextPasswConf.setError("Longitud mínima < 5");
                    error = true;
                }else{
                    error = false;
                }
                if(!editTextPassw.getText().toString().equals(editTextPasswConf.getText().toString())){
                    editTextPassw.setError("Las constraseñas deben coincidir");
                    error = true;
                }else{
                    error = false;
                }

            }
        });

    }

    public void postCliente(View view){

        if(!error && checkCamposRequeridos()){
            Cliente cliente = new Cliente();
            cliente.setNombre(editTextNombre.getText().toString()+" "+editTextApellidos.getText().toString());
            cliente.setCedula(editTextCedula.getText().toString());
            cliente.setCorreoElectronico(editTextEmail.getText().toString());
            cliente.setTelefono(editTextTelefono.getText().toString());
            cliente.setPassw(editTextPasswConf.getText().toString());
            cliente.setUsuario(editTextUsuario.getText().toString());
            cliente.setDireccion(editTextDireccion.getText().toString());

            viewModel.crearCliente(cliente);
        }else{
            Toast.makeText(getApplicationContext(), "Olvidaste llenar alguno de los campos", Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkCamposRequeridos(){
        if(editTextNombre.getText().toString().length() > 0 || editTextApellidos.getText().toString().length() > 0 ||
        editTextCedula.getText().toString().length() > 0 || editTextUsuario.getText().toString().length() > 0 || editTextPasswConf.getText().toString().length() > 0 ||
        editTextPasswConf.getText().toString().length() > 0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }
}
