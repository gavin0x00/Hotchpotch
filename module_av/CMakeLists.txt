
# 设置Cmake的最低版本号
cmake_minimum_required(VERSION 3.6.0)

#############################################################引入头文件开始###############################################################################
# 引入opencv 头文件
set(opencv2-headers ${CMAKE_SOURCE_DIR}/src/main/cpp/include)
include_directories(${opencv2-headers})


#############################################################引入头文件结束###############################################################################


#############################################################引入链接库开始###############################################################################

# 导入opencv_java4 动态库
add_library(libopencv_java4 SHARED IMPORTED)
set_target_properties(libopencv_java4
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/libs/${CMAKE_ANDROID_ARCH_ABI}/libopencv_java4.so)

#############################################################引入链接库结束###############################################################################

# 定义自己写的总库
add_library(hello_opencv

        SHARED

        ${CMAKE_SOURCE_DIR}/src/main/cpp/hello.cpp
        )

# 如果需要其他NDK原生库的话，可自行在此处添加
find_library(log-lib

             log)



# 将第三方库以及自己写的库关联到需要用的NDK原生库中
target_link_libraries(
        hello_opencv libopencv_java4 jnigraphics
        ${log-lib})