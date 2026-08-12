rootProject.name = "dsa"

rootDir.listFiles()
    ?.asSequence()
    ?.filter { it.isDirectory && it.resolve("build.gradle.kts").isFile }
    ?.sortedBy { it.name }
    ?.forEach { include(it.name) }
