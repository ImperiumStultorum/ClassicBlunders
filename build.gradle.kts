// This is to suppress false warnings generated by a bug in IntelliJ
@file:Suppress("DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "FUNCTION_CALL_EXPECTED", "PropertyName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	`maven-publish`

	alias(libs.plugins.kotlin)
	alias(libs.plugins.quilt.loom)
}

val archives_base_name: String by project

val javaVersion = 17

repositories {

}

loom {
	accessWidenerPath = file("src/main/resources/adverse.accesswidener")
    mods {
        
    }
}

// All the dependencies are declared at gradle/libs.version.toml and referenced with "libs.<id>"
// See https://docs.gradle.org/current/userguide/platforms.html for information on how version catalogs work.
dependencies {
	minecraft(libs.minecraft)
	mappings(
		variantOf(libs.quilt.mappings) {
			classifier("intermediary-v2")
		}
	)

	modImplementation(libs.quilt.loader)
	modImplementation(libs.qfapi)
	modImplementation(libs.qkl)
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget = javaVersion.toString()
			// languageVersion: A.B of the kotlin plugin version A.B.C
			languageVersion = libs.plugins.kotlin.get().version.requiredVersion.substringBeforeLast('.')
		}
	}

	withType<JavaCompile>.configureEach {
		options.encoding = "UTF-8"
		options.isDeprecation = true
		options.release.set(javaVersion)
	}

	processResources {
		filteringCharset = "UTF-8"
		inputs.property("version", project.version)

		filesMatching("quilt.mod.json") {
			expand(
				mapOf(
					"version" to project.version
				)
			)
		}
	}

	javadoc {
		options.encoding = "UTF-8"
	}

	// Run `./gradlew wrapper --gradle-version <newVersion>` or `gradle wrapper --gradle-version <newVersion>` to update gradle scripts
	// BIN distribution should be sufficient for the majority of mods
	wrapper {
		distributionType = Wrapper.DistributionType.BIN
	}

	jar {
		from("LICENSE") {
			rename { "LICENSE_${archives_base_name}" }
		}
	}
}

val targetJavaVersion = JavaVersion.toVersion(javaVersion)
if (JavaVersion.current() < targetJavaVersion) {
	kotlin.jvmToolchain(javaVersion)

	java.toolchain {
		languageVersion.set(JavaLanguageVersion.of(javaVersion))
	}
}

java {
	withSourcesJar()
	withJavadocJar()

	sourceCompatibility = targetJavaVersion
	targetCompatibility = targetJavaVersion
}

publishing {
	publications {
		register<MavenPublication>("Maven") {
			from(components.getByName("java"))
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// repositories to publish to

	}
}