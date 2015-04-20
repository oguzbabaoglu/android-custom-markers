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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.oguzbabaoglu.fancymarkers.CustomMarker;
import com.oguzbabaoglu.fancymarkers.IconMarker;
import com.oguzbabaoglu.fancymarkers.MarkerManager;

import java.util.ArrayList;

/**
 * @author Oguz Babaoglu
 */
public class ExampleActivity extends ActionBarActivity implements View.OnClickListener {

    private static final LatLng CENTER = new LatLng(36.778261, -119.417932);
    private static final int ZOOM = 6;

    private static final LatLng[] LOCATIONS = new LatLng[]{
            new LatLng(37.71515248, -120.7508706),
            new LatLng(36.37386931, -118.47269228),
            new LatLng(36.84395714, -121.66135364),
            new LatLng(37.44175323, -119.08338699),
            new LatLng(35.36812138, -120.75986352)
    };

    private static ImageLoader imageLoader;

    private MarkerManager<IconMarker> iconMarkerManager;
    private MarkerManager<ColorMarker> colorMarkerManager;
    private MarkerManager<NetworkMarker> networkMarkerManager;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        findViewById(R.id.button_icon_markers).setOnClickListener(this);
        findViewById(R.id.button_color_markers).setOnClickListener(this);
        findViewById(R.id.button_network_markers).setOnClickListener(this);

        final SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map_container, mapFragment);
        transaction.commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                iconMarkerManager = new MarkerManager<>(googleMap);
                colorMarkerManager = new MarkerManager<>(googleMap);
                networkMarkerManager = new MarkerManager<>(googleMap);

                iconMarkerManager.setOnMarkerClickListener(new DisableClick<IconMarker>());
                colorMarkerManager.setOnMarkerClickListener(new DisableClick<ColorMarker>());
                networkMarkerManager.setOnMarkerClickListener(new DisableClick<NetworkMarker>());

                map = googleMap;
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER, ZOOM));
            }
        });

        // Keep a static image loader instance.
        if (imageLoader == null) {

            final RequestQueue queue = Volley.newRequestQueue(this);
            imageLoader = new ImageLoader(queue, new NoImageCache());
        }
    }

    @Override
    public void onClick(View v) {

        iconMarkerManager.clear();
        colorMarkerManager.clear();
        networkMarkerManager.clear();

        switch (v.getId()) {

            case R.id.button_icon_markers:
                map.setOnMarkerClickListener(iconMarkerManager);
                iconMarkerManager.addMarkers(createIconMarkers());
                break;

            case R.id.button_color_markers:
                map.setOnMarkerClickListener(colorMarkerManager);
                colorMarkerManager.addMarkers(createColorMarkers());
                break;

            case R.id.button_network_markers:
                map.setOnMarkerClickListener(networkMarkerManager);
                networkMarkerManager.addMarkers(createNetworkMarkers());
                break;
        }
    }

    /**
     * Icons courtesy of https://mapicons.mapsmarker.com/
     */
    private ArrayList<IconMarker> createIconMarkers() {

        final ArrayList<IconMarker> iconMarkers = new ArrayList<>(LOCATIONS.length);

        iconMarkers.add(new IconMarker(LOCATIONS[0], R.drawable.bread, R.drawable.bread_c));
        iconMarkers.add(new IconMarker(LOCATIONS[1], R.drawable.butcher, R.drawable.butcher_c));
        iconMarkers.add(new IconMarker(LOCATIONS[2], R.drawable.fruits, R.drawable.fruits_c));
        iconMarkers.add(new IconMarker(LOCATIONS[3], R.drawable.grocery, R.drawable.grocery_c));
        iconMarkers.add(new IconMarker(LOCATIONS[4], R.drawable.patisserie, R.drawable.patisserie_c));

        return iconMarkers;
    }

    private ArrayList<ColorMarker> createColorMarkers() {

        final ArrayList<ColorMarker> colorMarkers = new ArrayList<>(LOCATIONS.length);

        for (LatLng location : LOCATIONS) {
            colorMarkers.add(new ColorMarker(this, location));
        }

        return colorMarkers;
    }

    private ArrayList<NetworkMarker> createNetworkMarkers() {

        final ArrayList<NetworkMarker> networkMarkers = new ArrayList<>(LOCATIONS.length);

        for (LatLng location : LOCATIONS) {
            networkMarkers.add(new NetworkMarker(this, location, imageLoader));
        }

        return networkMarkers;
    }

    /**
     * Not interested in caching images.
     */
    private static class NoImageCache implements ImageLoader.ImageCache {

        @Override
        public Bitmap getBitmap(String url) {
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            // Do nothing
        }
    }

    /**
     * Marker click listener that always returns true.
     */
    private static class DisableClick<T extends CustomMarker>
            implements MarkerManager.OnMarkerClickListener<T> {

        @Override
        public boolean onMarkerClick(T marker) {
            return true;
        }
    }

}
