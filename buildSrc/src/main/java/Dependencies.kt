object Versions {
    const val buildGradleVersion = "7.0.3"
    const val firebaseCrashAnalyticGradleVersion = "2.7.1"
    const val googleServicesVersion =  "4.3.8"
    const val kotlinVersion = "1.6.0"

    const val appcompatVersion = "1.3.1"
    const val coreKtxVersion = "1.6.0"
    const val materialVersion = "1.4.0"
    const val recyclerViewVersion = "1.2.1"
    const val activityVersion = "1.4.0"
    const val fragmentVersion = "1.4.0"
    const val constraintLayoutVersion = "2.1.1"
    const val cardViewVersion = "1.0.0"
    const val lifecycleVersion = "2.4.0"
    const val preferenceVersion = "1.1.1"
    const val swipeLayoutVersion = "1.1.0"
    const val retrofitVersion = "2.9.0"
    const val hiltVersion = "2.39.1"
    const val picassoVersion = "2.71828"
    const val coilVersion = "1.4.0"
    const val timberVersion = "5.0.1"
    const val rxJava3Version = "3.1.2"
    const val rxAndroid3Version = "3.0.0"
    const val okhttp3Version = "4.9.0"
    const val gsonVersion = "2.8.8"
    const val sdpVersion = "1.0.6"
    const val roomVersion = "2.3.0"
    const val mapVersion = "17.0.1"
    const val mapLocationVersion = "18.0.0"
    const val mapPlaceVersion = "2.4.0"
    const val firebaseBomVersion= "28.2.1"
    const val circleImageViewVersion = "3.1.0"
    const val navigationVersion = "2.3.5"
    const val navigationSafeArgsVersion = "2.3.5"
    const val coroutinesVersion = "1.5.1"
    const val leakcanaryVersion = "2.7"
    const val playCoreVersion = "1.10.2"
    const val playCoreKtxVersion = "1.8.1"
    const val facebookVersion = "12.0.1"
    const val facebookMarketingVersion = "7.0.1"
    const val viewPagerDotIndicatorVersion = "4.2"

    const val espressoVersion = "3.4.0"
    const val junitVersion = "4.13.2"
    const val junitExtVersion = "1.1.3"
}

object Dependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Versions.lifecycleVersion}"
    const val preference = "androidx.preference:preference:${Versions.preferenceVersion}"
    const val swipeToRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeLayoutVersion}"

    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgsVersion}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val rxJava3adapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofitVersion}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val okhHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}"
    const val okhHttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    const val rxJava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxJava3Version}"
    const val rxJava3Android = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroid3Version}"

    const val picasso = "com.squareup.picasso:picasso:${Versions.picassoVersion}"
    const val coil = "io.coil-kt:coil:${Versions.coilVersion}"
    const val sdp = "com.intuit.sdp:sdp-android:${Versions.sdpVersion}"
    const val ssp = "com.intuit.ssp:ssp-android:${Versions.sdpVersion}"
    const val circleImageview = "de.hdodenhof:circleimageview:${Versions.circleImageViewVersion}"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanaryVersion}"
    const val dotIndicator = "com.tbuonomo:dotsindicator:${Versions.viewPagerDotIndicatorVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"


    const val playStoreCore = "com.google.android.play:core:${Versions.playCoreVersion}"
    const val playStoreKtx = "com.google.android.play:core-ktx:${Versions.playCoreKtxVersion}"

    const val facebookSdk = "com.facebook.android:facebook-android-sdk:${Versions.facebookVersion}"
    const val facebookMarketing = "com.facebook.android:facebook-marketing:${Versions.facebookMarketingVersion}"

    const val mapSdk = "com.google.android.gms:play-services-maps:${Versions.mapVersion}"
    const val mapLocation = "com.google.android.gms:play-services-location:${Versions.mapLocationVersion}"
    const val mapPlaces = "com.google.android.libraries.places:places:${Versions.mapPlaceVersion}"

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"
    const val firebaseCore = "com.google.firebase:firebase-core"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    const val junit = "junit:junit:${Versions.junitVersion}"
    const val extJunit = "androidx.test.ext:junit:${Versions.junitExtVersion}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
}