package dev.d1s.resume.renderer

import dev.d1s.resume.page.Page

interface ResumeRenderer {

    fun render(page: Page): String
}