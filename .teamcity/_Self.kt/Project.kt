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
// Copyright © 2025 CCP ehf.

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
package _Self

import _Self.buildTypes.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot


object Project : Project({

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
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-pathfinder"
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates
=======
    description = "Build / Publish pipeline for https://github.com/ccpgames/carbon-template"
>>>>>>> template/carbonengine/pathfinder-updates

    params {
        /* before changing carbon_ref, make sure to disable automatic settings synchronization on teamcity */
        param("carbon_ref", "refs/heads/main")
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        param("carbon-pipeline-tools-ref", "refs/heads/main")
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
=======
        param("carbon-pipeline-tools-ref", "refs/tags/v0.1.0")
>>>>>>> template/carbonengine/pathfinder-updates
    }
    
    subProject(Windows.Project)
    subProject(MacOS.Project)

    buildType(PublishToPerforce)
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
    buildType(SyncToMirror)
>>>>>>> template/carbonengine/pathfinder-updates
=======
    buildType(SyncToMirror)
>>>>>>> template/carbonengine/pathfinder-updates
=======
    buildType(SyncToMirror)
>>>>>>> template/carbonengine/pathfinder-updates
=======
    buildType(SyncToMirror)
>>>>>>> template/carbonengine/pathfinder-updates
})
