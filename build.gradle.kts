plugins {
  kotlin("jvm") version "1.7.10"
  id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
  `maven-publish`
}

allprojects {
  repositories {
    maven(url = "https://plugins.gradle.org/m2/")
    mavenLocal()
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "kotlin")
  apply(plugin = "org.jlleitschuh.gradle.ktlint")
  apply(plugin = "maven-publish")

  ktlint { debug.set(true) }

  group = "run.cobalt.archetype"
  version = "2.0.9" // If you define version in each module's build script, you can assign the module a different version.

  java.sourceCompatibility = JavaVersion.VERSION_17

  tasks.compileKotlin {
    kotlinOptions {
      jvmTarget = "11"
    }
    dependsOn(tasks.processResources)
  }

  val sourceJar by tasks.creating(Jar::class) {
    archiveBaseName.set("cobalt-archetype-${project.name}")
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    doLast {
      copy {
        from("$buildDir/libs")
        include(archiveFileName.get())
        into("$rootDir/libs")
      }
    }
  }

  tasks.jar {
    archiveBaseName.set("cobalt-archetype-${project.name}")
    doLast {
      copy {
        from("$buildDir/libs")
        include(archiveFileName.get())
        into("$rootDir/libs")
      }
    }
    finalizedBy(sourceJar)
  }

  publishing {
    repositories {
      maven {
        name = "CobaltArchetype"
        url = uri("https://maven.pkg.github.com/cobaltinc/cobalt-archetype")
        credentials {
          username = project.findProperty("githubActor") as String? ?: System.getenv("GITHUB_ACTOR")
          password = project.findProperty("githubToken") as String? ?: System.getenv("GITHUB_TOKEN")
        }
      }
    }

    publications {
      register<MavenPublication>(project.name) {
        groupId = "${project.group}"
        artifactId = "cobalt-archetype-${project.name}"
        from(components["java"])
        afterEvaluate {
          version = project.version
        }
      }
    }
  }

  configurations {
    compileOnly {
      extendsFrom(configurations.annotationProcessor.get())
    }
  }

  tasks.withType<Test>().configureEach {
    useJUnitPlatform()
  }

  dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.6.3-native-mt")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
    testImplementation("io.kotest:kotest-assertions-core:5.3.2")
    testImplementation("io.kotest:kotest-property:5.3.2")
  }
}
