# Добавление MapKit

### Начало работы
- Следовать гайду: [Mapkit SDK](https://yandex.ru/maps-api/docs/mapkit/android/generated/getting_started.html)
    - Получить ключ api
    - Подключить библиотеки:
        1. Добавить
      ```kotlin
      maven { 
          url = uri("https://maven.google.com/")
      }
      ``` 
      в файл settings.gradle.kts, раздел repository:

      ```kotlin
      dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
              google()
              mavenCentral()
              maven {
                  url = uri("https://maven.google.com/")
              }
          }
      }
  
      ```
        2. Добавить
      ```kotlin
      implementation("com.yandex.android:maps.mobile:4.33.0-full")
      ```
      в build.gardle.kts раздел Module
    - Добавить API ключ
        1. В файл local.properties
      ```
      MAPKIT_API_KEY=YOR_API_KEY
      ```
        2. Установить buildConfig = true
      ```kotlin
      buildFeatures {
          compose = true
          buildConfig = true
      }
      ```
        3. добавить buildConfigField в defaultConfig в build.gradle.kts
      ```kotlin
      buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
      ```
        4. добвить функцию mapkitApiKey в build.gradle.kts
      ```kotlin
      val mapkitApiKey: String by lazy {
          val props = Properties()
          val localPropsFile = rootProject.file("local.properties")
          if (localPropsFile.exists()) {
              props.load(localPropsFile.inputStream())
          }
          props.getProperty("MAPKIT_API_KEY", "")
      }
  
      ```
        5. установть 'minSdk = 26' в build.gradle.kts
    - добавить в onCreate в класс Applicaton
      ```kotlin
      MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
      MapKitFactory.initialize(this)
      ```
    - добавить в onStop в MainActivity
      ```kotlin
      MapKitFactory.getInstance().onStop()
      ```
    - добавить в onCreate в MainActivity
      ```kotlin
      MapKitFactory.getInstance().onStart()
      ```
       