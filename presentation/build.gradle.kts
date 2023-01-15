import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("android.presentation")
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // GsonConverter & Retrofit2 & Okhttp3 For Network Connection
    implementation(libs.bundles.gson)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.interceptor)

    // Glide Library For Image
    implementation(libs.glide.glide)
    annotationProcessor(libs.glide.compiler)

    //Navigation
    implementation(libs.bundles.androidx.navigation)
    androidTestImplementation(libs.androidx.navigation.test)

    // life cycle scope
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.bundles.kotlinx.coroutine)

    //Room
    kapt(libs.androidx.room.compiler)
    implementation(libs.bundles.room)

    // 카카오 로그인
    implementation(libs.kakao.sdk)

    // Circle ImageView
    implementation(libs.circle.imageview)

    // skeleton Ui
    implementation(libs.facebook.shimmer)

    // Fragment Result API
    implementation(libs.androidx.fragment.request)

    // Preferences Data Store
    implementation(libs.bundles.datastore)

    // Firebase Push
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    // Lotties
    implementation(libs.android.lottie)
}
