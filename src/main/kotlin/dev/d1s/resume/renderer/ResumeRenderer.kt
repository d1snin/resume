package dev.d1s.resume.renderer

import dev.d1s.resume.page.PageRendering

interface ResumeRenderer {

    fun render(pageRendering: PageRendering): String
}