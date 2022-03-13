package dev.d1s.resume.service.impl

import dev.d1s.resume.generator.ResumeRenderer
import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
import dev.d1s.resume.service.ResumeService
import dev.d1s.teabag.logging.logger
import dev.d1s.teabag.stdlib.text.padding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResumeServiceImpl : ResumeService {

    @Autowired
    private lateinit var htmlResumeRenderer: ResumeRenderer

    @Autowired
    private lateinit var plainTextResumeRenderer: ResumeRenderer

    private var htmlPages: Map<Page, String> = mutableMapOf()
    private var plainTextPages: Map<Page, String> = mutableMapOf()

    private val log = logger

    override fun get(page: Page, kind: Kind): String {
        if (htmlPages.isEmpty() && plainTextPages.isEmpty()) {
            this.initializeResumePages()
            log.debug("Initialized pages.")
        }

        return when (kind) {
            Kind.HTML -> htmlPages[page]!!
            Kind.PLAIN_TEXT -> plainTextPages[page]!!
        }
    }

    private fun initializeResumePages() {
        setOf(Page.MAIN, Page.ABOUT_ME, Page.CONTACTS, Page.KNOWLEDGE, Page.PROJECTS).forEach {
            htmlPages = htmlPages + (it to htmlResumeRenderer.render(it))
            plainTextPages = plainTextPages + (it to plainTextResumeRenderer.render(it).padding {
                top = 5
                bottom = 5
                left = 10
                right = 10
            })
        }
    }
}