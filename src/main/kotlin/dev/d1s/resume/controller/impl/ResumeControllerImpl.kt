package dev.d1s.resume.controller.impl

import dev.d1s.resume.controller.ResumeController
import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
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
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.MAIN, accept, response)

    override fun getAboutMe(
        accept: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.ABOUT_ME, accept, response)

    override fun getContacts(
        accept: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.CONTACTS, accept, response)

    override fun getKnowledge(
        accept: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.KNOWLEDGE, accept, response)

    override fun getProjects(
        accept: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>? = this.getPage(Page.PROJECTS, accept, response)

    private fun getPage(page: Page, accept: String?, response: HttpServletResponse): ResponseEntity<String>? =
        if (page in resumeService.getAvailablePages()) {
            val builder = ResponseEntity.ok()

            if (accept != null && accept.contains(MediaType.TEXT_HTML_VALUE)) {
                builder.contentType(MediaType.TEXT_HTML_VALUE)
                    .body(resumeService.get(page, Kind.HTML))
            } else {
                builder.contentType(MediaType.TEXT_PLAIN_VALUE)
                    .body(resumeService.get(page, Kind.PLAIN_TEXT))
            }
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value())
            null
        }

    private fun ResponseEntity.BodyBuilder.contentType(value: String): ResponseEntity.BodyBuilder = apply {
        header("Content-Type", "$value; charset=utf-8")
    }
}