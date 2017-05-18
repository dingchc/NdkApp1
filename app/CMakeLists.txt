# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(path_project /Users/Ding/WorkSpace/project_studio/NdkApp1)

include_directories(${path_project}/app/src/main/cpp/include)

#include_directories(libs/include)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/native-lib.cpp )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

#添加FFmpeg预编译的so库
add_library( avutil
             SHARED
             IMPORTED
            )

#设置两个预编译的库的路径
set_target_properties(
                       avutil
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libavutil-54.so
                    )

add_library( swresample
             SHARED
             IMPORTED
            )

set_target_properties(
                       swresample
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libswresample-1.so
                    )

add_library( avcodec
             SHARED
             IMPORTED
            )

set_target_properties(
                       avcodec
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libavcodec-56.so
                    )

add_library( avformat
             SHARED
             IMPORTED
            )

set_target_properties(
                       avformat
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libavformat-56.so
                    )

add_library( swscale
             SHARED
             IMPORTED
            )

set_target_properties(
                       swscale
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libswscale-3.so
                    )

add_library( postproc
             SHARED
             IMPORTED
            )

set_target_properties(
                       postproc
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libpostproc-53.so
                    )

add_library( avfilter
             SHARED
             IMPORTED
            )

set_target_properties(
                       avfilter
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libavfilter-5.so
                    )

add_library( avdevice
             SHARED
             IMPORTED
            )

set_target_properties(
                       avdevice
                       PROPERTIES IMPORTED_LOCATION
                       ${path_project}/app/libs/${ANDROID_ABI}/libavdevice-56.so
                    )

#YUV转RGB需要的库
add_library( yuv
             SHARED
             IMPORTED
              )

set_target_properties(
                      yuv
                      PROPERTIES IMPORTED_LOCATION
                      ${path_project}/app/libs/${ANDROID_ABI}/libyuv_static.so
                      )

find_library(
            android-lib
            android
            )

find_library(
              log-lib
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries(
                       native-lib
                       ${log-lib}
                       ${android-lib}
                       avutil
                       swresample
                       avcodec
                       avformat
                       swscale
                       postproc
                       avfilter
                       avdevice
                       yuv
                       )