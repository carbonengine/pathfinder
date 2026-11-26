# Copyright © 2021 CCP ehf.

cmake_minimum_required(VERSION 3.16)
project(EvePathfinder VERSION 1.0.0)

include(cmake/CcpTargetConfigurations.cmake)
include(cmake/CcpDocsGenerator.cmake)
include(cmake/CcpPackageConfigHelpers.cmake)
include(cmake/CcpBuildConfigurations.cmake)

find_package(carbon-blueexposure REQUIRED CONFIG)
find_package(carbon-core REQUIRED CONFIG)

set(SRC_FILES
    EveDjikstrasGoal.cpp
    EveDjikstrasGoal.h
    EveDjikstrasGoal_Blue.cpp
    EveFloodFillGoal.cpp
    EveFloodFillGoal.h
    EveMap.cpp
    EveMap.h
    EveMap_Blue.cpp
    EveMapNodes.h
    EveMapPathfinderCache.cpp
    EveMapPathfinderCache.h
    EveMapPathfinderCache_Blue.cpp
    EvePathfinder.cpp
    EvePathfinder.h
    EveStandardFloodfillGoal.cpp
    EveStandardFloodfillGoal.h
    EveStandardFloodfillGoal_Blue.cpp
    IEvePathfinderGoal_Blue.cpp
    PResultBeResult.h
    stdafx.cpp
    stdafx.h
    Include/IEvePathfinderGoal.h
)

ccp_add_library(EvePathfinder SHARED ${SRC_FILES})
target_link_libraries(EvePathfinder PRIVATE BlueExposure)

set(PUBLIC_HEADER_FILES Include/IEvePathfinderGoal.h)

get_target_property(_SOURCES EvePathfinder SOURCES)
source_group(TREE "${CMAKE_CURRENT_SOURCE_DIR}"
        PREFIX "Sources"
        FILES ${_SOURCES}
)

target_compile_definitions(EvePathfinder PRIVATE BLUEBUILD)

target_precompile_headers(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/stdafx.h)
target_include_directories(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})
target_include_directories(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/../../../carbon/src)

if(APPLE OR LINUX)
    set_target_properties(
        EvePathfinder
        PROPERTIES
            PREFIX ""
            SUFFIX ".so"
    )
endif()
if(APPLE)
    target_link_libraries(EvePathfinder PRIVATE "-framework CoreFoundation -framework Cocoa")
endif()
set_target_properties(EvePathfinder PROPERTIES OUTPUT_NAME "_pyevepathfinder")

if(WIN32 OR LINUX)
    if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
        option(BUILD_TESTING "Build and run tests" ON)
        include(CTest)
        if(BUILD_TESTING)
            find_package(carbon-exefile-interpreter REQUIRED CONFIG)
            ccp_prepare_interpreter(
                    TARGET EvePathfinder
                    BUILD_TYPE ${CMAKE_BUILD_TYPE}
                    COMMAND_OUT EXEFILE_INTERPRETER
                    PYTHONPATH_OUT PYTHONPATH
                    RUN_DIR $<TARGET_FILE_DIR:EvePathfinder>
                    BLUE_DIR_OUT BLUE_LOCATION_DIR
            )

            if (WIN32)
                set(PYTHONPATH "${PYTHONPATH}\;$<TARGET_FILE_DIR:EvePathfinder>\;${CMAKE_CURRENT_SOURCE_DIR}/python")
            elseif(APPLE OR LINUX)
                set(PYTHONPATH "${PYTHONPATH}:$<TARGET_FILE_DIR:EvePathfinder>:${CMAKE_CURRENT_SOURCE_DIR}/python")
            endif ()

            if (WIN32)
                add_custom_command(
                        TARGET EvePathfinder POST_BUILD
                        COMMAND ${CMAKE_COMMAND} -E copy_if_different $<TARGET_RUNTIME_DLLS:EvePathfinder> $<TARGET_FILE_DIR:EvePathfinder>
                        COMMAND_EXPAND_LISTS
                )
            endif ()

            find_package(Python)

            execute_process(
                    COMMAND ${Python3_EXECUTABLE} discover.py
                    OUTPUT_VARIABLE PYTHON_TESTS_STR
                    OUTPUT_STRIP_TRAILING_WHITESPACE
                    ERROR_STRIP_TRAILING_WHITESPACE
                    RESULT_VARIABLE PYTHON_TEST_DISCOVERY_RETVAL
                    ERROR_VARIABLE PYTHON_TEST_DISCOVERY_ERRORS
                    WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/tests/python
            )

            if(${PYTHON_TEST_DISCOVERY_RETVAL})
                message(FATAL_ERROR "Error encountered during test discovery:\n=== stderr: ===\n${PYTHON_TEST_DISCOVERY_ERRORS}\n=== stdout: ===\n${PYTHON_TESTS_STR}")
            endif()
            separate_arguments(PYTHON_TESTS UNIX_COMMAND ${PYTHON_TESTS_STR})
            foreach(PYTHON_TEST ${PYTHON_TESTS})
                message(STATUS "adding test ${PYTHON_TEST}")
                add_test(NAME ${PYTHON_TEST}
                    COMMAND ${EXEFILE_INTERPRETER} /inherit /buildflavor=$<LOWER_CASE:$<CONFIG>> /py -m unittest -v ${PYTHON_TEST}
                    WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/tests/python
                )
                set_tests_properties(${PYTHON_TEST} PROPERTIES
                    ENVIRONMENT "PYTHONPATH=${PYTHONPATH};BUILDFLAVOR=$<LOWER_CASE:$<CONFIG>>"
                )
            endforeach()
        endif()
    endif()
endif()


#install(TARGETS EvePathfinder)
# Provide an install target iff this is the top level project only
if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
    option(INSTALL_TO_MONOLITH "Install this package to a perforce branch vendor folder - provided for backwards compatability" OFF)
    if(INSTALL_TO_MONOLITH)
        # Install rule to ensure that our runtime and linker files are in the expected, platform-specific folders
        install(
            TARGETS EvePathfinder
            EXPORT EvePathfinderTargets
            CONFIGURATIONS ${CMAKE_CONFIGURATION_TYPES}
            LIBRARY DESTINATION lib/${CCP_PLATFORM}/${CCP_ARCHITECTURE}/${CCP_TOOLSET}/
            ARCHIVE DESTINATION lib/${CCP_PLATFORM}/${CCP_ARCHITECTURE}/${CCP_TOOLSET}/
            RUNTIME DESTINATION bin/${CCP_PLATFORM}/${CCP_ARCHITECTURE}/${CCP_TOOLSET}/
        )
        # Install rule for available public headers
        install(FILES ${PUBLIC_HEADER_FILES} DESTINATION include)

        message(STATUS "lib/${CCP_PLATFORM}/${CCP_ARCHITECTURE}/${CCP_TOOLSET}/")

        configure_ccp_vendor_config_file(
            TARGET EvePathfinder
            DESTINATION "${CMAKE_CURRENT_BINARY_DIR}/EvePathfinderConfig.cmake"
            DEPENDENCIES ""
        )
        install(FILES ${CMAKE_CURRENT_BINARY_DIR}/EvePathfinderConfig.cmake DESTINATION .)
    else()
        # Install rule to ensure that our runtime and linker files are in the expected, platform-specific folders
        install(
                TARGETS EvePathfinder
                EXPORT EvePathfinderTargets
                CONFIGURATIONS ${CMAKE_CONFIGURATION_TYPES}
                LIBRARY DESTINATION lib
                ARCHIVE DESTINATION lib
                RUNTIME DESTINATION bin
        )

        install(EXPORT EvePathfinderTargets
                DESTINATION share/carbon-pathfinder
                FILE carbon-pathfinderConfig.cmake
        )
    endif()
endif()
