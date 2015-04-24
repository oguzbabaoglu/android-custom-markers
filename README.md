FancyMarkers
============
A utility library for adding custom markers to Android map.

Markers can use icons or layouts, respond to touches and load network images.

![ ](/images/markers.gif)

Including In Your Project
-------------------------
```groovy
dependencies {
    compile 'com.oguzbabaoglu:fancymarkers:0.2'
}
```

Usage
-----
*For a working implementation see the `examples/` folder.*

   1. Extend the `CustomMarker` class for your custom marker or use a simple `IconMarker` with icon resources.
   ```java
    @Override
    public boolean onStateChange(boolean selected) {
        myCustomView.setBackgroundColor(selected ? selectedColor : defaultColor);
        return true;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapGenerator.fromView(myCustomView);
    }
   ```

   2. Create a `MarkerManager` for your map.
   ```java
    MarkerManager<MyMarker> markerManager;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        markerManager = new MarkerManager<>(googleMap);
    }
   ```
   3. Add your markers.
   ```java
    markerManager.addMarker(new MyMarker());
    
   ```

License
=======

```
Copyright 2015 Oguz Babaoglu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
