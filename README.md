# OtakuBook
6th and final project of the Android Developer Nanodegree program by Udacity and Google.

This anime browser application is built on top of the [JIKAN Rest API](https://jikan.docs.apiary.io/#) to enable the user to browse 
and search for different animes.

## Features:
- showing posters of the popular airing animes and enable to user to open its details after tapping on it,
- title based search to show the matching titles and open any specific hit,
- login with Google account through the [Google Identity Platform](https://developers.google.com/identity/),
- included AdMob banner though the [Google Admob Service](https://www.google.com/admob/)
- realtime database (NoSql) to store the favorites of the logged in users provided by [Google Firebase](https://firebase.google.com/)

Note: To be able to run the project, you need to register your own version on [Google Firebase](https://console.firebase.google.com/) and add the generated google-services.json file to the app folder.

## Used libraries:
- Retrofit,
- ButterKnife,
- Picasso,
- Google Firebase and GMS libraries,
- Google Support libraries.

## Future Plans:
- Full redesign and extending the populated anime details content,
- Introduce ViewModel.

## Demo images about the app:
![img](https://github.com/GregBaggings/OtakuBook/blob/master/app/images/MainScreen.png)
![img](https://github.com/GregBaggings/OtakuBook/blob/master/app/images/SearchResultScreen.png)
![img](https://github.com/GregBaggings/OtakuBook/blob/master/app/images/AnimeDetailsScreen.png)
