package dev.d1s.resume.service.impl

import dev.d1s.resume.generator.ResumeRenderer
import dev.d1s.resume.page.Page
import dev.d1s.resume.service.ResumeService
import dev.d1s.teabag.logging.logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResumeServiceImpl : ResumeService {

    @Autowired
    private lateinit var resumeRenderer: ResumeRenderer

    private var pages: Map<Page, String> = mutableMapOf()

    private val log = logger

    override fun get(page: Page): String = if (pages.isEmpty()) {
        this.initializeResumePages()
        log.debug("Initialized pages.")
        pages[page]!!
    } else {
        pages[page]!!
    }

    private fun initializeResumePages() {
        pages = mapOf(
            Page.MAIN to resumeRenderer.render(Page.MAIN),
            Page.ABOUT_ME to resumeRenderer.render(Page.ABOUT_ME),
            Page.CONTACTS to resumeRenderer.render(Page.CONTACTS),
            Page.KNOWLEDGE to resumeRenderer.render(Page.KNOWLEDGE),
            Page.PROJECTS to resumeRenderer.render(Page.PROJECTS)
        )
    }
}