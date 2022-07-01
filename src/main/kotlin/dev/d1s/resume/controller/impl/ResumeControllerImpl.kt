package dev.d1s.resume.controller.impl

import dev.d1s.resume.controller.ResumeController
import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
import dev.d1s.resume.page.pageRendering
import dev.d1s.resume.service.ResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class ResumeControllerImpl : ResumeController {

    @Autowired
    private lateinit var resumeService: ResumeService

    override fun getMain(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.MAIN, accept, echo, response)

    override fun getAboutMe(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.ABOUT_ME, accept, echo, response)

    override fun getContacts(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.CONTACTS, accept, echo, response)

    override fun getKnowledge(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.KNOWLEDGE, accept, echo, response)

    override fun getProjects(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.PROJECTS, accept, echo, response)

    override fun getResume(
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.RESUME, accept, echo, response)

    private fun getPage(
        page: Page,
        accept: String?,
        echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? =
        if (page in resumeService.getAvailablePages()) {
            val builder = ResponseEntity.ok()

            if (accept != null && accept.contains(MediaType.TEXT_HTML_VALUE)) {
                builder.contentType(MediaType.TEXT_HTML_VALUE).body(
                    resumeService.get(
                        pageRendering {
                            this.page = page
                            kind = Kind.HTML
                            this.echo = echo
                        }
                    )
                )
            } else {
                builder.contentType(MediaType.TEXT_PLAIN_VALUE).body(
                    resumeService.get(
                        pageRendering {
                            // kind is PLAIN_TEXT by default
                            this.page = page
                            this.echo = echo
                        }
                    )
                )
            }
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value())
            null
        }

    private fun ResponseEntity.BodyBuilder.contentType(value: String): ResponseEntity.BodyBuilder = apply {
        header("Content-Type", "$value; charset=utf-8")
    }
}