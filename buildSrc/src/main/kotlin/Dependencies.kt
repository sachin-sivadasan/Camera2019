/**
 * Created by Mfluid on 11/15/2018.
 */
object Versions {

    internal const val retrofit_version = "2.3.0"
    internal const val design_version = "26.1.0"
    internal const val retrofit2_version = "2.2.0"
    internal const val life_cycle_version = "1.1.0"
    internal const val dagger2_version = "2.19"
    internal const val room1_version = "1.1.1"
    internal const val room2_version = "2.1.0-alpha02"
    internal const val lifecycle1_version = "1.1.1"
    internal const val lifecycle2_version = "2.0.0"
    internal const val constraint_layout_version = "1.1.3"
    internal const val butterknife_version = "10.1.0"
    internal const val okhttp3_version = "3.7.0"
    internal const val gradle_plugin_version = "3.4.1"
    internal const val kotlin_version = "1.3.20"
    internal const val glide_version = "4.9.0"
}

object PluginsDeps {
    var gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle_plugin_version}"
}

object KotlinDeps {
    var kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    var kotlin_std_lib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"
    var kotlin_reflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_version}"
    var kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    var android_ktx = "androidx.core:core-ktx:0.2"
}

object AndroidSupportDeps {
    var dep_constraint_layout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout_version}"
}

object AndroidXSupportDeps {
    var dep_constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"
}

object ButterKnifeDeps {
    var dep_butterknife = "com.jakewharton:butterknife:${Versions.butterknife_version}"
    var dep_butterknife_compiler = "com.jakewharton:butterknife-compiler:${Versions.butterknife_version}"
}
object MapDeps {
    // Below is the Google Play Services dependency required for using the Google Maps Android API
    var dep_map = "com.google.android.gms:play-services-maps:15.0.1"
    var dep_location = "com.google.android.gms:play-services-location:15.0.1"
}
object RetrofitDeps {
    var main = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    var gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
}

object Dagger2Deps {
    var dagger = "com.google.dagger:dagger:${Versions.dagger2_version}"
    var dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2_version}"
    var android = "com.google.dagger:dagger-android:${Versions.dagger2_version}"
    var android_support = "com.google.dagger:dagger-android-support:${Versions.dagger2_version}"
    var android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger2_version}"
}

object Rx1xDepts {
    var deps_main = "io.reactivex:rxjava:1.3.8"
    var deps_android = "io.reactivex:rxandroid:1.2.1"
    var deps_adapter = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit_version}"
}

object DesignDepts {
    var deps_cardview = "com.android.support:cardview-v7:${Versions.design_version}"
}

object ImageDepts {
    var deps_glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    var deps_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
}

object OkHttp3Depts {
    var deps_okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3_version}"
    var logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3_version}"
}

object Rx2xDepts {
    var rxjava = "io.reactivex.rxjava2:rxjava:2.2.4"
    var rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.2"
}

object Retrofit2Deps {
    var retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    var converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
    var adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2_version}"
}

object ArchComponentsRoom1 {
    var deps_runtime = "android.arch.persistence.room:runtime:${Versions.room1_version}"
    var deps_compiler = "android.arch.persistence.room:compiler:${Versions.room1_version}" // use kapt for Kotlin
    // optional - RxJava support for Room
    var rxjava2 = "android.arch.persistence.room:rxjava2:${Versions.room1_version}"
    // optional - Guava support for Room, including Optional and ListenableFuture
    var guaua = "android.arch.persistence.room:guava:${Versions.room1_version}"
    // Test helpers
    var testing = "android.arch.persistence.room:testing:${Versions.room1_version}"
}

object ArchComponentsRoom2 {
    var runtime = "androidx.room:room-runtime:${Versions.room2_version}"
    var compiler = "androidx.room:room-compiler:${Versions.room2_version}" // use kapt for Kotlin
    // optional - RxJava support for Room
    var rxjava2 = "androidx.room:room-rxjava2:${Versions.room2_version}"
    // optional - Guava support for Room, including Optional and ListenableFuture
    var guaua = "androidx.room:room-guava:${Versions.room2_version}"
    // Test helpers
    var testing = "androidx.room:room-testing:${Versions.room2_version}"
}

object ArchComponents {
    var permission = "pub.devrel:easypermissions:3.0.0"
}
object ArchComponentsLifecycle1 {
    // ViewModel and LiveData
    var extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle1_version}"
    // alternatively - just ViewModel
    var viewmodelAndroid = "android.arch.lifecycle:viewmodel:${Versions.lifecycle1_version}" // for Android
    var viewmodelKotlin = "android.arch.lifecycle:viewmodel:${Versions.lifecycle1_version}" // for Kotlin
    // alternatively - just LiveData
    var livedata = "android.arch.lifecycle:livedata:${Versions.lifecycle1_version}"
    // alternatively - Lifecycles only (no ViewModel or LiveData).Support library depends on this lightweight import
    var runtime = "android.arch.lifecycle:runtime:${Versions.lifecycle1_version}"

    var compiler = "android.arch.lifecycle:compiler:${Versions.lifecycle1_version}" // use kapt for Kotlin
    // alternately - if using Java8, use the following instead of compiler
    var common_java8 = "android.arch.lifecycle:common-java8:${Versions.lifecycle1_version}"

    // optional - ReactiveStreams support for LiveData
    var reactivestreams = "android.arch.lifecycle:reactivestreams:${Versions.lifecycle1_version}"

    // optional - Test helpers for LiveData
    var testing = "android.arch.core:core-testing:${Versions.lifecycle1_version}"
}

object ArchComponentsLifecycle2 {
    // ViewModel and LiveData
    var extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle2_version}"
    // alternatively - just ViewModel
    var viewmodelAndroid = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle2_version}" // for Android
    var viewmodelKotlin = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle2_version}" // for Kotlin
    // alternatively - just LiveData
    var livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle2_version}"
    // alternatively - Lifecycles only (no ViewModel or LiveData). Some UI AndroidX libraries use this lightweight import for Lifecycle
    var runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle2_version}"

    var compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle2_version}" // use kapt for Kotlin
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    var common_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle2_version}"

    // optional - ReactiveStreams support for LiveData
    var reactivestreams =
        "androidx.lifecycle:lifecycle-reactivestreams:${Versions.lifecycle2_version}" // use -ktx for Kotlin

    // optional - Test helpers for LiveData
    var testing = "androidx.arch.core:core-testing:${Versions.lifecycle2_version}"
}