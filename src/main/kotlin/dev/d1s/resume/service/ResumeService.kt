package dev.d1s.resume.service

import dev.d1s.resume.page.Kind
import dev.d1s.resume.page.Page

interface ResumeService {

    fun get(page: Page, kind: Kind): String

    fun getAvailablePages(): Set<Page>
}