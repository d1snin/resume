package dev.d1s.resume.renderer

import dev.d1s.resume.page.Page

interface PlainTextResumeRenderer : ResumeRenderer {

    override fun render(page: Page): String = this.render(page, true)

    fun render(page: Page, padding: Boolean): String
}