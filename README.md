# PulsatingButton
A simple Android UI library to create a Growing and Shrinking button. It's pulse rate and size of pulse offset can be controlled.
<br>
<center><img height = 500 width = 250 src=https://user-images.githubusercontent.com/18504765/67836177-e6ff0800-fb11-11e9-96ce-74575b0f9cff.gif ></center>


# Usage:

XML:
```
    <com.ayusch.pulsatingbutton.PulsatingButton
        android:id="@+id/pulsating_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:buttonColor="@color/colorAccent"
        app:horizontalOffset="40"
        app:pulseDuration="1000"
        app:text="Submit"
        app:textColor="@android:color/white"
        app:verticalOffset="40" />
```

Kotlin:

```
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pulsating_button.startAnimation()
    }
}
```


# Download

For Maven:
```
<dependency>
  <groupId>com.ayusch.pulsatingbutton</groupId>
  <artifactId>pulsatingbutton</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
For Gradle:
```
implementation 'com.ayusch.pulsatingbutton:pulsatingbutton:1.0.0'
```


# Developed By

Ayusch: https://ayusch.com

Email: contact@ayusch.com

### License
```
   Copyright (C) 2019 Ayusch Jain
   Copyright (C) 2011 Android Open Source Project

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

### Contributing to Pulsating Button
All pull requests are welcome.
