import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation("org.jetbrains:annotations:24.0.0")
}

plugins {
    id ("com.github.johnrengelman.shadow")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("AtmApp")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.atm.AtmApp"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
