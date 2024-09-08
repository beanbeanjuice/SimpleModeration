import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("io.github.goooler.shadow") version("8.1.7")
    id("java")
}

group = "com.beanbeanjuice"
version = "0.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()

    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        name = "placeholder-api-repo"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    // Spigot/Paper
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT")

    // Folia
    compileOnly("dev.folia", "folia-api", "1.20.1-R0.1-SNAPSHOT")
    implementation("com.github.technicallycoded", "FoliaLib", "main-SNAPSHOT")  // Spigot/Paper/Folia Cross-Compatability https://github.com/TechnicallyCoded/FoliaLib

    // Mini Message
    implementation("net.kyori", "adventure-api", "4.17.0")  // Mini-Message API https://mvnrepository.com/artifact/net.kyori/adventure-api
    implementation("net.kyori", "adventure-text-minimessage", "4.17.0")  // Mini Message https://mvnrepository.com/artifact/net.kyori/adventure-text-minimessage
    implementation("net.kyori", "adventure-platform-bukkit", "4.3.4")  // Mini Message -> Spigot https://mvnrepository.com/artifact/net.kyori/text-adapter-bukkit

    // PlaceholderAPI
    compileOnly("me.clip", "placeholderapi", "2.11.6")

    // bStats
    implementation("org.bstats", "bstats-bukkit", "3.0.2")

    // Better YAML Support
    implementation("dev.dejvokep", "boosted-yaml", "1.3.5")

    // Database
    implementation("org.xerial", "sqlite-jdbc", "3.46.0.1")  // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("com.mysql", "mysql-connector-j", "9.0.0")  // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j

    // Lombok
    compileOnly("org.projectlombok", "lombok", "1.18.32")
    annotationProcessor("org.projectlombok", "lombok", "1.18.32")
}

tasks.withType<ShadowJar> {
    relocate("net.kyori", "com.beanbeanjuice.libs.net.kyori")
    relocate("org.bstats", "com.beanbeanjuice.libs.org.bstats")
    relocate("dev.dejvokep", "com.beanbeanjuice.libs.dev.dejvokep")
    relocate("com.tcoded.folialib", "com.beanbeanjuice.libs.com.tcoded.folialib")
    relocate("com.github.technicallycoded", "com.beanbeanjuice.libs.com.github.technicallycoded")
    relocate("org.xerial", "com.beanbeanjuice.libs.org.xerial")
    relocate("com.mysql", "com.beanbeanjuice.libs.com.mysql")

    minimize {
        exclude(dependency("com.tcoded:.*:.*"))
        exclude(dependency("com.github.technicallycoded:.*:.*"))
    }

    archiveClassifier.set("")
    archiveBaseName.set(project.name)
    destinationDirectory.set(File(rootProject.projectDir, "libs"))

    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }

    doLast {
        archiveVersion.set(project.version as String)
        println("Compiling: " + project.name + "-" + project.version + ".jar")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.clean {
    doLast {
        file("libs").deleteRecursively()
    }
}

configure<ProcessResources>("processResources") {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}
