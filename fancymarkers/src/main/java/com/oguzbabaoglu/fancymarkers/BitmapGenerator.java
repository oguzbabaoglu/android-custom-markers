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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Utility class for creating bitmap icons from views.
 *
 * @author Oguz Babaoglu
 */
public final class BitmapGenerator {

    private BitmapGenerator() {
    }

    /**
     * Returns a bitmap icon showing a screenshot of the view passed in.
     *
     * @param view View to convert
     * @return Bitmap icon of view
     */
    public static BitmapDescriptor fromView(View view) {

        final int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);

        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();

        view.layout(0, 0, width, height);

        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        view.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}