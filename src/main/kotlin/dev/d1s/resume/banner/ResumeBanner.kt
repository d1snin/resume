package dev.d1s.resume.banner

import dev.d1s.teabag.stdlib.text.padding
import org.springframework.boot.Banner
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import java.io.PrintStream

class ResumeBanner : Banner {

    override fun printBanner(environment: Environment, sourceClass: Class<*>, out: PrintStream) {
        val banner = environment["resume.banner"]

        banner?.let {
            out.println(
                it.padding {
                    left = 5
                    top = 2
                    bottom = 2
                }
            )
        }
    }
}