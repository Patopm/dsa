plugins {
    application
}

application {
    mainClass.set("Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
