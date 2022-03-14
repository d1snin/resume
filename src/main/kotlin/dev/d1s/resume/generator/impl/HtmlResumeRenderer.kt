package dev.d1s.resume.generator.impl

import dev.d1s.resume.generator.ResumeRenderer
import dev.d1s.resume.page.Page
import dev.d1s.resume.proerties.ResumeConfigurationProperties
import dev.d1s.teabag.web.currentUriWithNoPath
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HtmlResumeRenderer : ResumeRenderer {

    @Autowired
    private lateinit var plainTextResumeRenderer: ResumeRenderer

    @Autowired
    private lateinit var resume: ResumeConfigurationProperties

    override fun render(page: Page): String {
        val plainText = plainTextResumeRenderer.render(page)

        return createHTML().html {
            head {
                meta(charset = Charsets.UTF_8.name())
                meta("viewport", "width=device-width, initial-scale=1.0, user-scalable=no")

                resume.name?.let {
                    title(it)
                    meta("title", it)
                    meta("title".og(), it)
                    meta("title".twitter(), it)
                }

                resume.shortBio?.let {
                    meta("description", it)
                    meta("description".og(), it)
                    meta("description".twitter(), it)
                }

                meta("type".og(), "website")
                meta("card".twitter(), "summary_large_image")

                val url = currentUriWithNoPath()
                meta("url".og(), url)
                meta("url".twitter(), url)

                val image = "./pfp.jpg"
                meta("image".twitter(), image)
                meta("image".og(), image)

                styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css")
            }

            body("d-flex justify-content-center bg-dark text-white") {
                style {
                    unsafe {
                        +"""
                         * {
                           font-size: 1vw;
                           }
                        }
                        """
                    }
                }

                pre {
                    +plainText
                }
            }
        }
    }

    private fun String.og() = "og:$this"
    private fun String.twitter() = "twitter:$this"
}