package dev.d1s.resume.service.impl

import dev.d1s.resume.constant.RESUME_CACHE
import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page
import dev.d1s.resume.renderer.PlainTextResumeRenderer
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.resume.service.ResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ResumeServiceImpl : ResumeService {

    @Autowired
    private lateinit var htmlResumeRenderer: ResumeRenderer

    @Autowired
    private lateinit var plainTextResumeRenderer: PlainTextResumeRenderer

    @Cacheable(RESUME_CACHE)
    override fun get(page: Page, kind: Kind): String = when (kind) {
        Kind.HTML -> htmlResumeRenderer.render(page)
        Kind.PLAIN_TEXT -> plainTextResumeRenderer.render(page)
    }
}