package app.admin.com.biciapp.ui.map_estaciones;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import app.josueburbano.com.biciapp.R;


public class InfoAdapter implements GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater = null;
    private TextView textViewstopName;
    private TextView arrivalTime;
    private ImageView bike_icon;

    public InfoAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        if (marker != null) {
            View v = inflater.inflate(R.layout.info_layout, null);
            bike_icon = (ImageView) v.findViewById(R.id.busicon);
            bike_icon.setImageResource(R.drawable.ic_action_name);
            textViewstopName = (TextView) v.findViewById(R.id.businfo);
            textViewstopName.setText(marker.getTitle());
            arrivalTime = (TextView) v.findViewById(R.id.arrivalinfo);
            arrivalTime.setText(marker.getSnippet());
            return (v);
        }
        return null;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return (null);
    }

    public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }
}