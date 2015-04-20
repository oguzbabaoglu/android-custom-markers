/*
 * Copyright (C) 2015 Oguz Babaoglu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oguzbabaoglu.fancymarkers.examples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.oguzbabaoglu.fancymarkers.BitmapGenerator;
import com.oguzbabaoglu.fancymarkers.CustomMarker;

/**
 * @author Oguz Babaoglu
 */
public class NetworkMarker extends CustomMarker implements ImageLoader.ImageListener {

    private static final String URL = "http://lorempixel.com/200/200?seed=";
    private static volatile int seed; // Bypass cache

    private LatLng position;
    private View view;
    private ImageView markerImage;
    private ImageView markerBackground;
    private ImageLoader imageLoader;

    public NetworkMarker(Context context, LatLng position, ImageLoader imageLoader) {
        this.position = position;

        view = LayoutInflater.from(context).inflate(R.layout.view_network_marker, null);
        markerImage = (ImageView) view.findViewById(R.id.marker_image);
        markerBackground = (ImageView) view.findViewById(R.id.marker_background);

        this.imageLoader = imageLoader;
    }

    @Override
    public void onAdd() {
        imageLoader.get(URL + seed++, this);
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapGenerator.fromView(view);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public boolean onStateChange(boolean selected) {

        if (selected) {
            markerBackground.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        } else {
            markerBackground.clearColorFilter();
        }

        return true;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

        final Bitmap bitmap = response.getBitmap();

        // Set image and update view
        markerImage.setImageBitmap(bitmap);
        updateView();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // Ignore
    }
}
