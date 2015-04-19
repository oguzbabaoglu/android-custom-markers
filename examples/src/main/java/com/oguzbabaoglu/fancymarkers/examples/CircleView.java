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
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Oguz Babaoglu
 */
public class CircleView extends FrameLayout {

    private final Path path;

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final float radius = context.getResources().getDimension(R.dimen.marker_size) / 2;

        path = new Path();
        path.addCircle(radius, radius, radius, Path.Direction.CW);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.clipPath(path);
        super.draw(canvas);
    }
}
