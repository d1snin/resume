package dev.d1s.resume.renderer.impl

import dev.d1s.resume.page.Page
import dev.d1s.resume.proerties.ResumeConfigurationProperties
import dev.d1s.resume.renderer.PlainTextResumeRenderer
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.teabag.web.currentUriWithNoPath
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HtmlResumeRenderer : ResumeRenderer {

    @Autowired
    private lateinit var plainTextResumeRenderer: PlainTextResumeRenderer

    @Autowired
    private lateinit var resume: ResumeConfigurationProperties

    override fun render(page: Page): String {
        val plainText = plainTextResumeRenderer.render(page, false)

        return createHTML().html {
            head {
                meta(charset = Charsets.UTF_8.name())
                meta("viewport", "width=device-width, initial-scale=1.0")

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

                val url = currentUriWithNoPath(replaceHttpWithHttps = resume.preferHttps)
                meta("url".og(), url)
                meta("url".twitter(), url)

                val image = "./pfp.jpg"
                meta("image".twitter(), image)
                meta("image".og(), image)

                styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css")
            }

            body("d-flex justify-content-center bg-dark text-white mt-5") {
                style {
                    unsafe {
                        +"""
                         pre {
                           font-size: 1vw;
                           font-family: monospace, monospace !important;
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
