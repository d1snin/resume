package dev.d1s.resume.controller

import dev.d1s.resume.constant.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

interface ResumeController {

    @GetMapping("/$MAIN_PAGE_MAPPING")
    fun getMain(): ResponseEntity<String>

    @GetMapping("/$ABOUT_ME_PAGE_MAPPING")
    fun getAboutMe(): ResponseEntity<String>

    @GetMapping("/$CONTACTS_PAGE_MAPPING")
    fun getContacts(): ResponseEntity<String>

    @GetMapping("/$KNOWLEDGE_PAGE_MAPPING")
    fun getKnowledge(): ResponseEntity<String>

    @GetMapping("/$PROJECTS_PAGE_MAPPING")
    fun getProjects(): ResponseEntity<String>
}