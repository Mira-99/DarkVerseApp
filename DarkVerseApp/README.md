# DarkVerse App

تطبيق أندرويد حديث مبني بـ Kotlin مع دمج Firebase.

## المواصفات

- **اسم التطبيق**: DarkVerse
- **Package Name**: com.darkverse.app
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 33 (Android 13)
- **Compile SDK**: 33
- **Java Version**: 17

## التقنيات المستخدمة

- Kotlin
- Firebase Analytics
- Firebase Authentication
- Firebase Realtime Database
- Material Design Components
- View Binding

## البناء والتشغيل

1. تأكد من تثبيت Android Studio
2. استنسخ المشروع أو قم بتحميله
3. افتح المشروع في Android Studio
4. قم بمزامنة Gradle
5. شغل التطبيق على جهاز أو محاكي

## ملاحظات

- تأكد من وجود ملف `google-services.json` صحيح في مجلد `app`
- يتطلب اتصال بالإنترنت لخدمات Firebase

## الهيكل

```
DarkVerseApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/darkverse/app/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── mipmap-*/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
├── gradle/wrapper/
├── build.gradle
├── settings.gradle
└── gradle.properties
```

