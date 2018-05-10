-keep class com.squareup.** {*;}
-keep interface com.squareup.**{*;}
-dontwarn com.squareup.**

-dontwarn okio.**

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

#test aaaaaaaaaa
-keep class com.google.common.** {*;}
-dontwarn com.google.common.**

#rxandroid
-keep class io.reactivex.** {*;}
-dontwarn io.reactivex.**
-keep class com.jakewharton.** {*;}
-dontwarn com.jakewharton.**

#round image view
-keep class com.makeramen.** {*;}
-dontwarn com.makeramen.**

#fabric
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#giu static method
-keep class manulife.manulifesop.util.** {*;}

#ahbottomnavigation
-keep class com.aurelhubert.** { *; }
-dontwarn com.aurelhubert.**

#indicatorseekbar
-keep class com.github.warkiz.widget.** { *; }
-dontwarn com.github.warkiz.widget.**

#indicator
-keep class chick.indicator.** { *; }
-dontwarn chick.indicator.**

#không cần nửa vì dã proguard ở thư viện rồi
#chick.circular_progress_bar
#-keep class chick.circular_progress_bar.** { *; }
#-dontwarn chick.circular_progress_bar.**

#cn.refactor.library
-keep class cn.refactor.library.** { *; }
-dontwarn cn.refactor.library.**

#ca.barrenechea.header-decor:header-decor:0.2.8
-keep class ca.barrenechea.header-decor.** { *; }
-dontwarn ca.barrenechea.header-decor.**

#net.cachapa.expandablelayout:expandablelayout:2.9.2
-keep class net.cachapa.expandablelayout.** { *; }
-dontwarn net.cachapa.expandablelayout.**

#com.github.sundeepk.compactcalendarview
-keep class com.github.sundeepk.compactcalendarview.** { *; }
-dontwarn com.github.sundeepk.compactcalendarview.**

#butter knife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }



#https://github.com/leolin310148/ShortcutBadger/issues/46
-keep class me.leolin.shortcutbadger.impl.AdwHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.ApexHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.AsusHomeLauncher { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.DefaultBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.NewHtcHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.NovaHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.SolidHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.SonyHomeBadger { <init>(...); }
-keep class me.leolin.shortcutbadger.impl.XiaomiHomeBadger { <init>(...); }

#sweet message
 -keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
 }

#-dontobfuscate

## New rules for EventBus 3.0.x ##
# http://greenrobot.org/eventbus/documentation/proguard/

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}



# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
#-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

#loại bỏ các tập tin xử lý thừa
-dontpreverify
#Chỉ định để đóng gói lại tất cả các tập tin lớp được đổi tên,
#-repackageclasses ''
#Chỉ định truy cập của các lớp và các thành viên lớp có thể được mở rộng trong khi biên dịch
-allowaccessmodification
#Chỉ định tối ưu hóa
-optimizations !code/simplification/arithmetic

-keepattributes *Annotation*,Exceptions, InnerClasses
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService


# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
#-keepclassmembers public class * extends android.view.View {
#   void set*(***);
#   *** get*();
#}

# We want to keep methods in Activity that could be used in the XML attribute onClick
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.** {*;}

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

