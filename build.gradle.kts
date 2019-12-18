import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.resolutionstrategy.ComponentFilter
import com.github.benmanes.gradle.versions.updates.resolutionstrategy.ComponentSelectionWithCurrent
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.util.regex.Pattern

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVersion")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply(plugin = "com.github.ben-manes.versions")


allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}


tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}


tasks.withType<DependencyUpdatesTask> {
    outputDir = "${project.buildDir.path.replace(project.projectDir.path + "/", "")}/dependencyUpdates"
    reportfileName = "update-dependencies.md"
    checkForGradleUpdate = true
    rejectVersionIf {
        this.candidate.version.let {
            Pattern.compile("(?i)(alpha)|(beta)|(rc)").matcher(it).find()
        }
    }
    outputFormatter = closureOf<com.github.benmanes.gradle.versions.reporter.result.Result> {
        val output = StringBuffer()
            .append("| Group:Module | Current version | Latest version |\n")
            .append("| ---------- | ---------- | ---------- |\n")
        this.outdated.dependencies.forEach {
            output.append("| [${it.group}:${it.name}](${it.projectUrl}) | ${it.version} | ${it.available.integration ?: it.available.milestone} |\n")
        }
        File(outputDir, reportfileName).let {
            it.ensureParentDirsCreated()
            it.createNewFile()
            project.file(it).writeText(output.toString(), Charsets.UTF_8)
        }
    }
}

