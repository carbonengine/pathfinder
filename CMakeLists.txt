<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
cmake_minimum_required(VERSION 3.16)
project(CarbonTemplateProject VERSION 1.0.0)

include(cmake/CcpBuildConfigurations.cmake)
include(cmake/CcpTargetConfigurations.cmake)

ccp_add_library(target_name SHARED)

get_target_property(_SOURCES target_name SOURCES)
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
source_group(TREE "${CMAKE_CURRENT_SOURCE_DIR}"
        PREFIX "Sources"
        FILES ${_SOURCES}
)

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
target_compile_definitions(EvePathfinder PRIVATE BLUEBUILD)

target_precompile_headers(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/stdafx.h)
target_include_directories(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})
target_include_directories(EvePathfinder PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/../../../carbon/src)

if(APPLE)
    set_target_properties(
        EvePathfinder
        PROPERTIES
            PREFIX ""
            SUFFIX ".so"
    )
    target_link_libraries(EvePathfinder PRIVATE "-framework CoreFoundation -framework Cocoa")
endif()
set_target_properties(EvePathfinder PROPERTIES OUTPUT_NAME "_pyevepathfinder")

if(WIN32)
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
            elseif(APPLE)
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
=======
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
=======
>>>>>>> template/carbonengine/pathfinder-updates
set_target_properties(target_name PROPERTIES OUTPUT_NAME "_target_name")

target_include_directories(target_name
        PUBLIC
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/include>
        $<INSTALL_INTERFACE:include>
)

# Provide an install target if this is the top level project only
if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
    # Determine if/when to generate/build documentation. The rules are as follows:
    # - If running locally - Build documentation is OFF by default
    # - If running on a build agent (TeamCity) - Build documentation is ON by default
    # - If -DBUILD_DOCUMENTATION=ON/OFF flag is explicitly set, use it.
    set(BUILD_DOCUMENTATION_DEFAULT_FLAG OFF)
    if(DEFINED ENV{TEAMCITY_VERSION})
        set(BUILD_DOCUMENTATION_DEFAULT_FLAG ON)
    endif()
    message(STATUS "Document generation settings: default=${BUILD_DOCUMENTATION_DEFAULT_FLAG}, option BUILD_DOCUMENTATION=${BUILD_DOCUMENTATION}")
    option(BUILD_DOCUMENTATION "Build Documentation" ${BUILD_DOCUMENTATION_DEFAULT_FLAG})

    if(BUILD_DOCUMENTATION)
        # Set up sphinx build for Python autodoc comments.
        set(SPHINX_SOURCE ${CMAKE_CURRENT_SOURCE_DIR}/doc/source)
        set(SPHINX_BUILD ${CMAKE_CURRENT_BINARY_DIR}/doc/build)

        find_package(Python3 COMPONENTS Interpreter REQUIRED)
        include(cmake/CcpDocsGenerator.cmake)

        create_carbon_docs_sphinx_target(
                PYTHON_EXE ${Python3_EXECUTABLE}
                VENV_NAME docs_generation_venv
                PYTHONPATH_ENV $ENV{PYTHONPATH}
                SPHINX_SOURCE ${SPHINX_SOURCE}
                SPHINX_BUILD ${SPHINX_BUILD}
                DOXYGEN_SRC_FILES ${SRC_FILES}
                SPHINX_TARGET_NAME Sphinx
                DOXYGEN_TARGET_NAME Doxygen
                INSTALL_DESTINATION documentation
        )

        add_dependencies(Sphinx target_name)
    endif()


    option(INSTALL_TO_MONOLITH "Install this package to a perforce branch vendor folder - provided for backwards compatability" OFF)
    if (INSTALL_TO_MONOLITH)
        include(cmake/CcpMonolithVendorConfig.cmake)
        include(cmake/CcpPackageConfigHelpers.cmake)

        # Install rule to ensure that our runtime and linker files are in the expected, platform-specific folders
        install(
                TARGETS target_name
                EXPORT package_nameTarget
                CONFIGURATIONS ${CMAKE_CONFIGURATION_TYPES}
                LIBRARY DESTINATION ${CCP_VENDOR_LIB_PATH}
                ARCHIVE DESTINATION ${CCP_VENDOR_LIB_PATH}
                RUNTIME DESTINATION ${CCP_VENDOR_BIN_PATH}
        )

        # Generate target configuration for the monolith
        configure_ccp_vendor_config_file(
                TARGET target_name
                DESTINATION "${CMAKE_CURRENT_BINARY_DIR}/package_nameConfig.cmake"
                DEPENDENCIES ""
        )
        install(FILES ${CMAKE_CURRENT_BINARY_DIR}/package_nameConfig.cmake DESTINATION .)
        install(DIRECTORY python DESTINATION . FILES_MATCHING PATTERN "*.py")
    else ()
        # Install rule for available public headers
        install(
                TARGETS target_name
                EXPORT package_nameTarget
                CONFIGURATIONS ${CMAKE_CONFIGURATION_TYPES}
                DESTINATION ${CMAKE_INSTALL_PREFIX}/share/package_name
                ARCHIVE DESTINATION lib
                LIBRARY DESTINATION lib
                RUNTIME DESTINATION bin
        )

        install(EXPORT package_nameTarget
                DESTINATION share/package_name
                FILE package_name.cmake
        )

        install(
                FILES package_nameConfig.cmake
                DESTINATION ${CMAKE_INSTALL_PREFIX}/share/package_name)
    endif()

    install(DIRECTORY include DESTINATION . FILES_MATCHING PATTERN "*.h")

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
=======
endif()
>>>>>>> template/carbonengine/pathfinder-updates
