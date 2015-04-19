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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.oguzbabaoglu.fancymarkers.BitmapGenerator;
import com.oguzbabaoglu.fancymarkers.CustomMarker;

import java.util.Random;

/**
 * @author Oguz Babaoglu
 */
public class ColorMarker extends CustomMarker {

    private static final int TINT = 0x66B2FF;

    private LatLng position;
    private View colorView;

    public ColorMarker(Context context, LatLng position) {
        this.position = position;

        colorView = LayoutInflater.from(context).inflate(R.layout.view_color_marker, null);
    }

    @Override
    public boolean onStateChange(boolean selected) {

        return selected;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {

        colorView.setBackgroundColor(generateRandomColor());
        return BitmapGenerator.fromView(colorView);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    private int generateRandomColor() {

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        red = (red + Color.red(TINT)) >> 1;
        green = (green + Color.green(TINT)) >> 1;
        blue = (blue + Color.blue(TINT)) >> 1;

        return Color.rgb(red, green, blue);
    }
}
