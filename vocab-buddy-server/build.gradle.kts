plugins {
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.onixbyte"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // MySQL Connector
    runtimeOnly("com.mysql:mysql-connector-j")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    // Spring Starters
    // implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // MyBatis Plus
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.3.1")

    // Web Dev Suite
    implementation("cn.vorbote:vorbote-webdev-spring-boot-starter:2023.05.a00")

    // Apache POI
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.test {
    useJUnitPlatform()
}
