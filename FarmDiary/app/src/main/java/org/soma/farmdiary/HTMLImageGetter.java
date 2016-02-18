package org.soma.farmdiary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lminjae on 2016. 2. 18..
 */
public class HTMLImageGetter implements Html.ImageGetter {
    Context c;
    TextView v;

    public HTMLImageGetter(Context c, TextView v) {
        this.c = c;
        this.v = v;
    }

    @Override
    public Drawable getDrawable(String source) {
        URLDrawable drawable = new URLDrawable();
        new ASync(drawable).execute(source);
        return drawable;
    }

    @SuppressWarnings("deprecation")
    private class URLDrawable extends BitmapDrawable {
        Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.setBounds(getBounds());
                Log.i("Resolution", String.format("%d %d", drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
                drawable.draw(canvas);
            }
        }
    }

    private class ASync extends AsyncTask<String, Void, Void> {
        URLDrawable drawable;

        public ASync(URLDrawable drawable) {
            this.drawable = drawable;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URLConnection urlConnection = new URL(params[0]).openConnection();
                urlConnection.connect();
                Bitmap bm = BitmapFactory.decodeStream(new BufferedInputStream(urlConnection.getInputStream()));

                drawable.drawable = new BitmapDrawable(c.getResources(), bm);

                drawable.setBounds(0, 0, bm.getWidth() / 2, bm.getHeight() / 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            v.setText(v.getText());
        }
    }
}
