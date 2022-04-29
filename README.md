# WeatherAppSDK
Weather App SDK Android Integration

Weather App SDK Provides the following features:
=> Get Today Weather based on Latitude and longitude.
=> Get Wind Speed.
=> Getting weather for a specified future timeframe (1-7) days.
=> Retrieve the temperature in F/C.

Data Source: Open Weather OneCall Api.

Setup :
=> Add it in your root build.gradle at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

=> Add the dependency

	dependencies {
	        implementation 'com.github.Shubham-htf:WeatherAppSDK:v1.0.0'
	}

How to use:

=> Add Internet permission in you project.
=> Just Call this method to get Weather details.

	WeatherActivity.newInstance(
		currentActivity = this,
            appId = "YOUR API KEY",
            latitude = 28.4089,
            longitude = 77.3178
        )

	here *currentActivity* is context of the activity. and *appId* is the key of the *OpenWeather Api Key*
	and pass *latitude* and *longitude* of location.



