package dev.d1s.resume.controller

import dev.d1s.resume.constant.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletResponse

interface ResumeController {

    @GetMapping(MAIN_PAGE_MAPPING)
    fun getMain(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?

    @GetMapping(ABOUT_ME_PAGE_MAPPING)
    fun getAboutMe(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?

    @GetMapping(CONTACTS_PAGE_MAPPING)
    fun getContacts(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?

    @GetMapping(KNOWLEDGE_PAGE_MAPPING)
    fun getKnowledge(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?

    @GetMapping(PROJECTS_PAGE_MAPPING)
    fun getProjects(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?

    @GetMapping(RESUME_PAGE_MAPPING)
    fun getResume(
        @RequestHeader("Accept", required = false) accept: String?,
        @RequestParam("echo", required = false) echo: String?,
        response: HttpServletResponse
    ): ResponseEntity<String>?
}