package dev.d1s.resume.service

import dev.d1s.resume.page.Page

interface ResumeService {

    fun get(page: Page): String
}