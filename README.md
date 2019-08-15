# How to: AltBeacon with AlarmManager for Android 8.0+

A major problem when you're working with beacons is the Android Oreo and Pie services limitation, thus to remedy these shortcomings, this projects uses AlarmManagers + Services + Broadcastes with AltBeacon library for beacon background detection without the weird fixed notification.

For this, there's no need to use the monitoring function from the library, only the ranging one, which you can set for ranging the region each 10 seconds.
