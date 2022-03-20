package dev.d1s.resume.service

import dev.d1s.resume.page.Page
import dev.d1s.resume.page.PageRendering

interface ResumeService {

    fun get(pageRendering: PageRendering): String

    fun getAvailablePages(): Set<Page>
}