Preferences Editor
==================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PreferencesEditor-green.svg?style=true)](https://android-arsenal.com/details/1/2967)

Preferences Editor makes it easy to add, edit, remove keys from shared preferences files.

It defines a BroadcastReceiver that will make changes to your shared preferences based on received actions and data.


Usage
-----

Emulate broadcast with adb shell:
```shell
# replace org.example.app with your application id

# Add a value to default shared preferences.
adb shell 'am broadcast -a org.example.app.sp.PUT --es key key_name --es value "hello world!"'

# Remove a value to default shared preferences.
adb shell 'am broadcast -a org.example.app.sp.REMOVE --es key key_name'

# Clear all default shared preferences.
adb shell 'am broadcast -a org.example.app.sp.CLEAR --es key key_name'

# It's also possible to specify shared preferences file.
adb shell 'am broadcast -a org.example.app.sp.PUT --es name Game --es key level --ei value 10'

# Data types
adb shell 'am broadcast -a org.example.app.sp.PUT --es key string --es value "hello world!"'
adb shell 'am broadcast -a org.example.app.sp.PUT --es key boolean --ez value true'
adb shell 'am broadcast -a org.example.app.sp.PUT --es key float --ef value 3.14159'
adb shell 'am broadcast -a org.example.app.sp.PUT --es key int --ei value 2015'
adb shell 'am broadcast -a org.example.app.sp.PUT --es key long --el value 9223372036854775807'

# Restart application process after making changes
adb shell 'am broadcast -a org.example.app.sp.CLEAR --ez restart true'
```


Install
-------

1\. Add the JitPack repository to your build file

```groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

2\. Add the dependency (recommended to add only to debug build type)

```groovy
debugCompile 'com.github.jfsso:PreferencesEditor:1.0.0'
```


License
-------

    Copyright (C) 2015 João Orui

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
