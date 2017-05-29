# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangkai/Documents/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.support.v4.widget.CursorAdapter

-dontwarn com.dora.feed.view.**
-keep class com.dora.feed.view.** { *;}


-dontwarn com.umeng.message.**
-dontwarn com.tencent.**
-dontwarn com.dora.feed.widget.**
-dontwarn com.dora.feed.mvp.bean.**
-dontwarn com.sina.**
-dontwarn com.famlink.frame.**

-dontwarn tv.danmaku.ijk.**
-dontwarn com.famlink.frame.widget.**
-dontwarn android.support.v7.widget.GridLayoutManager.**
-dontwarn android.support.v7.widget.LinearLayoutManager.**
-dontwarn android.support.v7.widget.RecyclerView.**

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class com.dora.feed.R$* {
    public static final int *;
}

-keep public class com.umeng.fb.ui.ThreadView {
}

-keep class com.famlink.frame.mvp.bean.BaseResult{*;}
-keep class * extends com.famlink.frame.mvp.bean.BaseResult{*;}
-keep class com.famlink.frame.mvp.bean.**{*;}
-keep class com.famlink.frame.view.tab.tabfragment.**{*;}
-keep class com.famlink.frame.view.fragment.BaseFragment{*;}
-keep class com.famlink.frame.view.activity.**{*;}
-keep class com.tencent.**{*;}
-keep class com.tencent.mm.sdk.** { *;}
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
-keep class com.tencent.smtt.**{*;}
-keep class com.tencent.tbs.**{*;}
-keep class com.sina.**{*;}
-keep class tv.danmaku.ijk.** {*;}
-keep class com.famlink.frame.widget.** {*;}
-keep class com.google.gson.**{*;}
-keep class com.android.volley.**{*;}
-keep class org.xutils.**{*;}
-keep class com.lidroid.xutils.** { *; }
-keep public class * extends com.lidroid.xutils.**
-keepattributes Signature
-keep public interface com.lidroid.xutils.** {*;}
-dontwarn com.lidroid.xutils.**
-keepclasseswithmembers class com.jph.android.entity.** {
    <fields>;
    <methods>;
}

-dontwarn org.xutils.**
-keep class org.xutils.** { *;}
-keep class org.** { *;}
-dontwarn sun.security.**
-keep class sun.security.** { *;}

-keepclasseswithmembernames class * {
    native <methods>;
 }

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers class * extends android.support.v4.app.Fragment {
	*;
}

-keepclassmembers class * extends android.support.v4.app.FragmentActivity {
	*;
}

-keepclassmembers class * extends android.support.v7.app.ActionBarActivity {
	*;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}









