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

package com.oguzbabaoglu.fancymarkers;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Base marker implementation.
 *
 * @author Oguz Babaoglu
 */
public abstract class CustomMarker {

    private MarkerManager markerManager;
    private boolean selected;

    /**
     * @return the map marker if it exists on the map.
     */
    @Nullable
    public Marker getMarker() {

        if (markerManager == null) {
            throw new IllegalStateException("getMarker() called before onAdd()!!");
        }

        return markerManager.getMarker(this);
    }

    /**
     * Internal method for setting connected Marker Manager.
     *
     * @param markerManager manager to set
     */
    void setMarkerManager(MarkerManager markerManager) {
        this.markerManager = markerManager;
    }

    /**
     * Sets the state of the marker.
     * Will call {@link #onStateChange(boolean)} if state is changed.
     *
     * @param selected state
     */
    public void setSelected(boolean selected) {

        if (this.selected != selected) {
            this.selected = selected;
            if (onStateChange(selected)) {
                updateView();
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * Updates the marker icon if it exists on the map.
     * Should be called after {@link #onAdd()}.
     */
    public void updateView() {
        final Marker marker = getMarker();

        if (marker != null) {
            marker.setIcon(getBitmapDescriptor());
        }
    }

    /**
     * Internal prepare method. Ensures all markers have a position.
     *
     * @param markerOptions marker options for the marker
     */
    void prepareMarker(MarkerOptions markerOptions) {
        markerOptions.position(getPosition());
        onPrepareMarker(markerOptions);
    }

    /**
     * Called before the marker is created. Set marker options for the marker here.
     *
     * @param markerOptions marker options for the marker
     */
    public void onPrepareMarker(MarkerOptions markerOptions) {
        markerOptions.icon(getBitmapDescriptor());
    }

    /**
     * Called after the marker is created and added to map.
     */
    public void onAdd() {
        // For rent.
    }

    /**
     * Called when the marker changes selected state.
     * Any change in the view should be made here.
     * {@link #updateView()} will be called if this returns true.
     *
     * @param selected New state
     * @return true if update required
     */
    public boolean onStateChange(boolean selected) {
        return false;
    }

    /**
     * Create the bitmap that represents the icon for this marker.
     * See {@link com.google.android.gms.maps.model.BitmapDescriptorFactory}
     * and {@link BitmapGenerator}
     *
     * @return Bitmap descriptor of icon.
     */
    public abstract BitmapDescriptor getBitmapDescriptor();

    /**
     * All markers need to supply location information.
     *
     * @return Location of marker.
     */
    public abstract LatLng getPosition();
}
