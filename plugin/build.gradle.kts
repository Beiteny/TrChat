@file:Suppress("PropertyName", "SpellCheckingInspection")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val taboolib_version: String by project

dependencies {
    implementation(project(":project:common"))
    implementation(project(":project:module-adventure"))
    implementation(project(":project:module-nms"))
    implementation(project(":project:runtime-bukkit"))
    implementation(project(":project:runtime-bungee"))
    implementation(project(":project:runtime-velocity"))
}

tasks {
    withType<ShadowJar> {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")
        // 删除一些不必要的文件
        exclude("META-INF/maven/**")
        exclude("META-INF/tf/**")
        exclude("module-info.java")
    }
    sourcesJar {
        archiveFileName.set("${rootProject.name}-${archiveFileName.get().substringAfter('-')}")
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].allSource) }
    }
    build {
        dependsOn(shadowJar)
    }
}

gradle.buildFinished {
    File(buildDir, "libs/${project.name}-${rootProject.version}.jar").delete()
}