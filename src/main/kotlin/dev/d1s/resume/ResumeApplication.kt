package dev.d1s.resume

import dev.d1s.resume.banner.ResumeBanner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ResumeApplication

fun main(args: Array<String>) {
    runApplication<ResumeApplication>(*args) {
        setBanner(ResumeBanner())
    }
}
