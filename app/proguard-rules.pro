# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep class * extends androidx.fragment.app.Fragment{}
-keep class androidx.lifecycle.** { *; }
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tech.b2simulator.data.local.entity.** { *; }
-keep class com.tech.b2simulator.domain.model.** { *; }
-keep class * implements androidx.lifecycle.LifecycleOwner { public <init>(...); }
-keep class * implements androidx.lifecycle.LifecycleObserver { public <init>(...); }
#gson
-keep class com.google.gson.stream.** { *; }

-keepnames class androidx.lifecycle.ViewModel
-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable
#Android navigation
-keepnames class com.tech.b2simulator.domain.common.CategoryType
-keepnames class com.tech.b2simulator.domain.common.CategoryType

-keepclassmembers class * extends androidx.lifecycle.ViewModel { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver { <init>(...); }
-keepclassmembers class * implements androidx.lifecycle.LifecycleOwner { <init>(...); }
-keepclassmembers class androidx.lifecycle.Lifecycle$State { *; }
-keepclassmembers class androidx.lifecycle.Lifecycle$Event { *; }


#retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Exceptions

#OKhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**
