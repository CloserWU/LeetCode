cmake_minimum_required(VERSION 3.15)
project(C__)

set(CMAKE_CXX_STANDARD 14)

add_executable(C__
        include/Array.h
        include/BTree.h
        include/Date.h
        include/Test1.h
        include/Utils.h
        src/Array.cpp
        src/BTree.cpp
        src/Date.cpp
        src/main.cpp
        src/Test1.cpp
        src/Utils.cpp src/machine/machine.cpp)
