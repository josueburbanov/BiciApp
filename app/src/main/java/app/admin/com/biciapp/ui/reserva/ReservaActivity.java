package app.admin.com.biciapp.ui.reserva;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import app.admin.com.biciapp.ui.bicicletas_estacion.EstacionBicicletasActivity;
import app.admin.com.biciapp.ui.instrucciones_retiro.InstruccionesRetiroActivity;
import app.admin.com.biciapp.ui.map_estaciones.MapsActivity;
import app.josueburbano.com.biciapp.R;
import app.admin.com.biciapp.datos.modelos.Bicicleta;
import app.admin.com.biciapp.datos.modelos.Estacion;
import app.admin.com.biciapp.datos.modelos.Reserva;
import app.admin.com.biciapp.ui.login.LoginClienteView;

import static app.admin.com.biciapp.ui.login.LoginActivity.CLIENT_VIEW;

public class ReservaActivity extends AppCompatActivity {

    private LoginClienteView clienteView;
    private Estacion estacionView;
    private Bicicleta bicicletaView;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";
    public static final String RESERVA_VIEW = "app.josueburbano.com.biciapp.RESERVA";

    EditText dateEditText;
    EditText startTimeEditText;
    EditText endTimeEditText;

    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    int mes = c.get(Calendar.MONTH);
    int dia = c.get(Calendar.DAY_OF_MONTH);
    int anio = c.get(Calendar.YEAR);
    int horaInicio = c.get(Calendar.HOUR_OF_DAY);
    int minutoInicio = c.get(Calendar.MINUTE) + 2;
    int horaFin = c.get(Calendar.HOUR_OF_DAY) + 1;
    int minutoFin = c.get(Calendar.MINUTE) + 2;

    private ReservaViewModel viewModel;

    private Reserva reservaPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        //Obtener los objetos provenientes de la activity anterior (EstacionBicicletasActivty)
        Intent intent = getIntent();
        clienteView = (LoginClienteView) intent.getSerializableExtra(CLIENT_VIEW);
        estacionView = (Estacion) intent.getSerializableExtra(MapsActivity.ESTACION_VIEW);
        bicicletaView = (Bicicleta) intent.getSerializableExtra(EstacionBicicletasActivity.BICICLETA_VIEW);

        final TextView estacionTextView = findViewById(R.id.step1TextView);
        final TextView bicicletaTextView = findViewById(R.id.bicicletaTextView);
        dateEditText = findViewById(R.id.dateEditText);
        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);

        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
        final int mesActual = mes + 1;
        //Formateo el día obtenido: antepone el 0 si son menores de 10
        String diaFormateado = (dia < 10) ? CERO + String.valueOf(dia) : String.valueOf(dia);
        //Formateo el mes obtenido: antepone el 0 si son menores de 10
        String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
        //Muestro la fecha con el formato deseado
        dateEditText.setText(diaFormateado + BARRA + mesFormateado + BARRA + anio);

        //Formateo el hora obtenido: antepone el 0 si son menores de 10
        String horaFormateada = (horaInicio < 10) ? String.valueOf(CERO + horaInicio) : String.valueOf(horaInicio);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minutoInicio < 10) ? String.valueOf(CERO + minutoInicio) : String.valueOf(minutoInicio);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM;
        if (horaInicio < 12) {
            AM_PM = "a.m.";
        } else {
            AM_PM = "p.m.";
        }
        //Muestro la hora con el formato deseado
        startTimeEditText.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);

        //Formateo el hora obtenido: antepone el 0 si son menores de 10
        String horaFormateadaFin = (horaFin < 10) ? String.valueOf(CERO + horaFin) : String.valueOf(horaFin);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateadoFin = (minutoFin < 10) ? String.valueOf(CERO + minutoFin) : String.valueOf(minutoFin);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        String AM_PM_Fin;
        if (horaFin < 12) {
            AM_PM_Fin = "a.m.";
        } else {
            AM_PM_Fin = "p.m.";
        }
        //Muestro la hora con el formato deseado
        endTimeEditText.setText(horaFormateadaFin + DOS_PUNTOS + minutoFormateadoFin + " " + AM_PM_Fin);


        estacionTextView.setText("Estacion: " + estacionView.getNombre());
        bicicletaTextView.setText("Bicicleta " + bicicletaView.getModelo() + "< >");


        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    selectDate(view);
                } else {

                }
            }
        });

        startTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectStartTime(v);
                } else {

                }
            }
        });

        endTimeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectEndTime(v);
                } else {

                }
            }
        });


        viewModel = ViewModelProviders.of(this, new ReservaViewModelFactory())
                .get(ReservaViewModel.class);

        viewModel.getReservaCompletada().observe(this, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                if (reserva == null) {

                } else if (reservaPost.getIdBici().equals(reserva.getIdBici()) &&
                        reservaPost.getIdCliente().equals(reserva.getIdCliente()) &&
                        reservaPost.getFecha().equals(reserva.getFecha()) &&
                        reservaPost.getHoraInicio().equals(reserva.getHoraInicio()) &&
                        reservaPost.getHoraFin().equals(reserva.getHoraFin())){
                    String success = getString(R.string.reserva);
                    Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();

                    //El estado de la bici sigue siendo disponible
                    Intent intent = new Intent(getApplicationContext(), InstruccionesRetiroActivity.class);
                    intent.putExtra(RESERVA_VIEW, reserva);
                    intent.putExtra(EstacionBicicletasActivity.BICICLETA_VIEW, bicicletaView);
                    intent.putExtra(MapsActivity.ESTACION_VIEW, estacionView);
                    intent.putExtra(CLIENT_VIEW, clienteView);
                    startActivity(intent);
                }
            }
        });


        viewModel.getReservaActiva().observe(this, new Observer<Reserva>() {
            @Override
            public void onChanged(@Nullable Reserva reserva) {
                if (reserva == null || !reserva.isActiva()) {
                    viewModel.makeReserva(reservaPost);
                    viewModel.getReservaActiva().removeObserver(this);
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido reservar debido a " +
                            "que ya tiene una reserva activa. El " +
                            reserva.getFecha() + " a las " + reserva.getHoraInicio() + " " +
                            "hasta las " + reserva.getHoraFin(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método controlador para la acción del Button para desplegar el dialogDate
    public void selectDate(View view) {
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                dateEditText.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                anio = year;
                mes = month;
                dia = dayOfMonth;
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    public void selectStartTime(View view) {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                startTimeEditText.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                horaInicio = hourOfDay;
                minutoInicio = minute;
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horaInicio, minutoInicio, false);

        recogerHora.show();
    }

    public void selectEndTime(View view) {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                endTimeEditText.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                horaFin = hourOfDay;
                minutoFin = minute;
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horaFin, minutoFin, false);

        recogerHora.show();
    }

    public void postReserva(final View view) {
        reservaPost = gatherFieldsReserva();

        if (reservaPost == null) {
            Toast.makeText(getApplicationContext(), "Por favor revise que la fecha y/o horas " +
                    "correspondan", Toast.LENGTH_LONG).show();
        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirmación")
                    .setMessage("Fecha: " + reservaPost.getFecha() + "\n Hora inicial: " + reservaPost.getHoraInicio()
                            + "\nHora entrega: " + reservaPost.getHoraFin() + "\nBiclicleta: " + bicicletaView.getModelo())
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.obtenerReservaActiva(clienteView.getId());
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }

    private Reserva gatherFieldsReserva() {
        Reserva reserva = new Reserva();
        reserva.setIdCliente(clienteView.getId());
        reserva.setIdBici(bicicletaView.getId());
        Date date = new GregorianCalendar(anio, mes, dia).getTime();
        if (System.currentTimeMillis() < date.getTime()) {
            return null;
        }
        reserva.setFecha(DateFormat.format("yyyy/MM/dd", date).toString());
        Date horaInicioRes = new GregorianCalendar(anio, mes, dia, horaInicio, minutoInicio).getTime();
        if (new Date().after(horaInicioRes)) {
            return null;
        }
        reserva.setHoraInicio(DateFormat.format("HH:mm", horaInicioRes).toString());
        Date horaFinRes = new GregorianCalendar(anio, mes, dia, horaFin, minutoFin).getTime();
        if (new Date().after(horaFinRes) || new Date().after(horaInicioRes)) {
            return null;
        }
        reserva.setHoraFin(DateFormat.format("HH:mm", horaFinRes).toString());
        reserva.setActiva(true);
        reserva.setConcretada(false);
        return reserva;
    }
}
