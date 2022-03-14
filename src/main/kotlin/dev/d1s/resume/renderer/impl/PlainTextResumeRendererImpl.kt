package dev.d1s.resume.renderer.impl

import com.jakewharton.picnic.*
import dev.d1s.resume.page.Page
import dev.d1s.resume.proerties.ResumeConfigurationProperties
import dev.d1s.resume.proerties.model.Knowledge
import dev.d1s.resume.renderer.PlainTextResumeRenderer
import dev.d1s.teabag.logging.logger
import dev.d1s.teabag.stdlib.text.padding
import dev.d1s.teabag.web.appendPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlainTextResumeRendererImpl : PlainTextResumeRenderer {

    @Autowired
    private lateinit var resume: ResumeConfigurationProperties

    private val log = logger

    override fun render(page: Page, padding: Boolean): String {
        log.debug("Rendering ${page.path}")
        val table = when (page) {
            Page.MAIN -> this.renderMain()
            Page.ABOUT_ME -> this.renderAboutMe()
            Page.CONTACTS -> this.renderContacts()
            Page.KNOWLEDGE -> this.renderKnowledge()
            Page.PROJECTS -> this.renderProjects()
        }

        return if (padding) {
            table.padding {
                top = 5
                bottom = 5
                left = 10
                right = 10
            }
        } else {
            table
        }
    }

    private fun renderMain() = tableWithDefaultHeader {}

    private fun renderAboutMe(): String {
        val span = 2

        return tableWithDefaultHeader(span) {
            val summaryInfoMap = mapOf(
                "Name" to resume.name,
                "Nickname" to resume.nickname,
                "Age" to resume.age,
                "Location" to resume.location
            )

            val emptySummary = summaryInfoMap.values.filterNotNull().isEmpty()

            val longBio = resume.longBio

            longBio?.let {
                row {
                    cell(Page.ABOUT_ME.prettyName)
                    cell(it) {
                        alignment = TextAlignment.MiddleLeft
                    }
                }
            }

            if (!emptySummary) {
                summaryInfoMap.forEach {
                    row(it.key, it.value)
                }
            }

            if (emptySummary && longBio == null) {
                this.fullRowCellWithColumnSpan(span, "No information provided.")
            }
        }
    }

    private fun renderContacts(): String {
        val span = 2

        return tableWithDefaultHeader(span) {
            val contacts = resume.contacts

            if (contacts.isNotEmpty()) {
                row("Service", "Address")
            }

            if (contacts.isEmpty()) {
                this.fullRowCellWithColumnSpan(span, "No contacts provided.")
                return@tableWithDefaultHeader
            }

            contacts.forEach {
                row(it.service, it.address)
            }
        }
    }

    private fun renderKnowledge(): String {
        val span = 3

        return tableWithDefaultHeader(span) {
            val languages = resume.languages
            val frameworks = resume.frameworks

            this.buildKnowledge(languages, "Languages")
            this.buildKnowledge(frameworks, "Frameworks")

            if (languages.isEmpty() && frameworks.isEmpty()) {
                this.fullRowCellWithColumnSpan(span, "No knowledge provided.")
            }
        }
    }

    private fun renderProjects(): String {
        val span = 4

        return tableWithDefaultHeader(span) {
            cellStyle {
                paddingLeft = 1
                paddingRight = 1
            }

            val projects = resume.projects

            if (projects.isNotEmpty()) {
                row("Name", "Description", "Status", "URL")
            }

            projects.forEach {
                row(it.name, it.description, it.status, it.url)
            }

            if (projects.isEmpty()) {
                this.fullRowCellWithColumnSpan(span, "No projects provided.")
            }
        }
    }

    private inline fun tableWithDefaultHeader(
        columnSpanValue: Int = 1,
        crossinline block: TableDsl.() -> Unit
    ): String =
        table {
            style {
                borderStyle = BorderStyle.Solid
            }

            cellStyle {
                alignment = TextAlignment.MiddleCenter
                border = true
                paddingTop = 1
                paddingBottom = 1
                paddingLeft = 2
                paddingRight = 2
            }

            header {
                resume.banner?.let {
                    row {
                        cell(it) {
                            columnSpan = columnSpanValue
                        }
                    }
                }

                resume.shortBio?.let {
                    row {
                        cell(it) {
                            columnSpan = columnSpanValue
                        }
                    }
                }

                row {
                    cellStyle {
                        alignment = TextAlignment.MiddleLeft
                    }

                    cell(Page.values().joinToString(separator = "\n") {
                        "* ${it.prettyName}: curl -L ${appendPath(it.path, false)}"
                    }) {
                        columnSpan = columnSpanValue
                    }
                }
            }

            block()
        }.renderText()

    private fun TableDsl.fullRowCellWithColumnSpan(span: Int, content: String) {
        row {
            cell(content) {
                columnSpan = span
            }
        }
    }

    private fun TableDsl.buildKnowledge(knowledge: List<Knowledge>, cellName: String) {
        if (knowledge.isNotEmpty()) {
            row {
                cell(cellName) {
                    rowSpan = knowledge.size
                }

                val firstFramework = knowledge.first()

                cell(firstFramework.name)
                cell(firstFramework.knowledge)
            }

            for (i in knowledge.indices) {
                if (i != 0) {
                    row(knowledge[i].name, knowledge[i].knowledge)
                }
            }
        }
    }
}