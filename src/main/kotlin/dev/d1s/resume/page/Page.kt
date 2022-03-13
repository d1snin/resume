package dev.d1s.resume.page

import dev.d1s.resume.constant.*

enum class Page(val path: String, val prettyName: String) {
    MAIN(
        MAIN_PAGE_MAPPING,
        "Main"
    ),
    ABOUT_ME(
        ABOUT_ME_PAGE_MAPPING,
        "About me"
    ),
    CONTACTS(
        CONTACTS_PAGE_MAPPING,
        "Contacts"
    ),
    KNOWLEDGE(
        KNOWLEDGE_PAGE_MAPPING,
        "Knowledge"
    ),
    PROJECTS(
        PROJECTS_PAGE_MAPPING,
        "Projects"
    )
}