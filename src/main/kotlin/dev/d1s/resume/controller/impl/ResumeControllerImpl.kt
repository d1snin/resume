package dev.d1s.resume.controller.impl

import dev.d1s.resume.controller.ResumeController
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

    override fun getMain(): ResponseEntity<String> = this.page(Page.MAIN)
    override fun getAboutMe(): ResponseEntity<String> = this.page(Page.ABOUT_ME)
    override fun getContacts(): ResponseEntity<String> = this.page(Page.CONTACTS)
    override fun getKnowledge(): ResponseEntity<String> = this.page(Page.KNOWLEDGE)
    override fun getProjects(): ResponseEntity<String> = this.page(Page.PROJECTS)

    private fun page(page: Page) = ResponseEntity.ok()
        .header("Content-Type", "${MediaType.TEXT_PLAIN_VALUE}; charset=utf-8")
        .body(resumeService.get(page))
}