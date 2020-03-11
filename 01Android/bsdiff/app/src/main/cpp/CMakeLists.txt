cmake_minimum_required(VERSION 3.4.1)


file(GLOB bzip2_source ${CMAKE_SOURCE_DIR}/bzip/*.c)

# for native-lib
add_library(native-lib ## so name --> libnative-lib.so
        SHARED # .a or .so
        native-lib.cpp
        bspatch.c
        ${bzip2_source}
        ) # source code

find_library(log-lib log)
target_link_libraries(native-lib ${log-lib})