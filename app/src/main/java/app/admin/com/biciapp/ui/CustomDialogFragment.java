package app.admin.com.biciapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomDialogFragment<T> extends DialogFragment {
    private String titulo;
    private String mensaje;
    private String mensajeBtnPositivo;
    private String mensajeBtnNegativo;
    private Method metodoBtnPositivo;
    public Method metodoBtnNegativo;
    public T objectCont;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getTitulo());
        builder.setMessage(getMensaje())
                .setPositiveButton(mensajeBtnPositivo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            metodoBtnPositivo.invoke(objectCont);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(mensajeBtnNegativo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            metodoBtnNegativo.invoke(objectCont);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeBtnPositivo() {
        return mensajeBtnPositivo;
    }

    public void setMensajeBtnPositivo(String mensajeBtnPositivo) {
        this.mensajeBtnPositivo = mensajeBtnPositivo;
    }

    public String getMensajeBtnNegativo() {
        return mensajeBtnNegativo;
    }

    public void setMensajeBtnNegativo(String mensajeBtnNegativo) {
        this.mensajeBtnNegativo = mensajeBtnNegativo;
    }

    public Method getMetodoBtnPositivo() {
        return metodoBtnPositivo;
    }

    public void setMetodoBtnPositivo(Method metodoBtnPositivo) {
        this.metodoBtnPositivo = metodoBtnPositivo;
    }

    public Method getMetodoBtnNegativo() {
        return metodoBtnNegativo;
    }

    public void setMetodoBtnNegativo(Method metodoBtnNegativo) {
        this.metodoBtnNegativo = metodoBtnNegativo;
    }
}
