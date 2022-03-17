package dev.d1s.resume.service.impl

import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
import dev.d1s.resume.properties.ResumeConfigurationProperties
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.resume.service.ResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResumeServiceImpl : ResumeService {

    @Autowired
    private lateinit var htmlResumeRenderer: ResumeRenderer

    @Autowired
    private lateinit var plainTextResumeRenderer: ResumeRenderer

    @Autowired
    private lateinit var resumeConfigurationProperties: ResumeConfigurationProperties

    override fun get(page: Page, kind: Kind): String = when (kind) {
        Kind.HTML -> htmlResumeRenderer.render(page)
        Kind.PLAIN_TEXT -> plainTextResumeRenderer.render(page)
    }

    override fun getAvailablePages(): Set<Page> = Page.values().filter {
        it.name.lowercase() !in resumeConfigurationProperties.excludePages
    }.toSet()
}