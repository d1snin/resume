package dev.d1s.resume.generator.impl

import dev.d1s.resume.generator.ResumeRenderer
import dev.d1s.resume.page.Page
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HtmlResumeRenderer : ResumeRenderer {

    @Autowired
    private lateinit var plainTextResumeRenderer: ResumeRenderer

    override fun render(page: Page): String {
        val plainText = plainTextResumeRenderer.render(page)

        return createHTML().html {
            head {
                meta("viewport", "width=device-width, initial-scale=1.0, user-scalable=no")
                styleLink("./resume.css")
            }

            body {
                pre {
                    +plainText
                }
            }
        }
    }
}