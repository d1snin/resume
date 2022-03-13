package dev.d1s.resume.controller.impl

import dev.d1s.resume.controller.ResumeController
import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
import dev.d1s.resume.service.ResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ResumeControllerImpl : ResumeController {

    @Autowired
    private lateinit var resumeService: ResumeService

    override fun getMain(accept: String?): ResponseEntity<String> = this.page(Page.MAIN, accept)
    override fun getAboutMe(accept: String?): ResponseEntity<String> = this.page(Page.ABOUT_ME, accept)
    override fun getContacts(accept: String?): ResponseEntity<String> = this.page(Page.CONTACTS, accept)
    override fun getKnowledge(accept: String?): ResponseEntity<String> = this.page(Page.KNOWLEDGE, accept)
    override fun getProjects(accept: String?): ResponseEntity<String> = this.page(Page.PROJECTS, accept)

    private fun page(page: Page, accept: String?): ResponseEntity<String> {
        val builder = ResponseEntity.ok()

        return if (accept != null && accept.contains(MediaType.TEXT_HTML_VALUE)) {
            builder.contentType(MediaType.TEXT_HTML_VALUE)
                .body(resumeService.get(page, Kind.HTML))
        } else {
            builder.contentType(MediaType.TEXT_PLAIN_VALUE)
                .body(resumeService.get(page, Kind.PLAIN_TEXT))
        }
    }

    private fun ResponseEntity.BodyBuilder.contentType(value: String): ResponseEntity.BodyBuilder = apply {
        header("Content-Type", "$value; charset=utf-8")
    }
}