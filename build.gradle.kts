import org.gradle.kotlin.dsl.registering

plugins {
    // Дает корневому проекту базовые задачи (clean, build),
    // не превращая сам корень в Java-модуль
    base
}

allprojects {
    group = "com.example"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

// Задача для сборки и копирования всех JAR в корневую папку build/
tasks.register<Copy>("collectJars") {
    // Запускаем сборку JAR во всех подпроектах
    dependsOn(subprojects.map { it.tasks.matching { t -> t.name == "jar" } })

    // Берем готовые JAR из build/libs каждого модуля
    from(subprojects.map { it.layout.buildDirectory.dir("libs") })
    include("*.jar")

    // Складываем в корневую папку build/ (или укажите file("uild"), если нужна именно она)
    into(layout.buildDirectory)
}