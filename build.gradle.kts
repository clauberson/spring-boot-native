import org.gradle.kotlin.dsl.graalvmNative

plugins {
    java
    jacoco
    id("com.diffplug.spotless") version "6.18.0"
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.20"
    id("org.hibernate.orm") version ("6.2.2.Final")
}

group = "com.softwaremill"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation(platform("io.micrometer:micrometer-bom:1.11.0"))
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-observation")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit", module = "junit")
    }

    runtimeOnly("com.h2database:h2")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required.set(true)
        html.outputLocation.set(file("$buildDir/jacoco/html"))
    }
}

tasks.compileJava {
    dependsOn(tasks.clean)
}

spotless {
    java {
        target("src/main/java/**/*.java")
        palantirJavaFormat()
        indentWithSpaces()
        formatAnnotations()
        removeUnusedImports()
        trimTrailingWhitespace()
        importOrder("com.softwaremill", "java", "javax", "jakarta", "org", "com", "lombok")
    }

    kotlinGradle {
        ktlint()
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}

hibernate {
    enhancement {
        enableDirtyTracking.set(true)
        enableLazyInitialization.set(true)
        enableExtendedEnhancement.set(false)
    }
}

graalvmNative {
    binaries {
        names.forEach { binaryName ->
            named(binaryName) {
                classpath("./aot/")
            }
        }
    }
}
