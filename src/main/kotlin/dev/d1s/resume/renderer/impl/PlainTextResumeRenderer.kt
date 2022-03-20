package dev.d1s.resume.renderer.impl

import com.jakewharton.picnic.*
import dev.d1s.resume.constant.PLAIN_TEXT_RESUME_CACHE
import dev.d1s.resume.page.Page
import dev.d1s.resume.page.PageRendering
import dev.d1s.resume.properties.ResumeConfigurationProperties
import dev.d1s.resume.properties.model.Knowledge
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.resume.util.link
import dev.d1s.teabag.stdlib.text.padding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PlainTextResumeRenderer : ResumeRenderer {

    @Autowired
    private lateinit var config: ResumeConfigurationProperties

    @Cacheable(PLAIN_TEXT_RESUME_CACHE)
    override fun render(pageRendering: PageRendering): String {
        return when (pageRendering.page) {
            Page.MAIN -> this.renderMain(pageRendering)
            Page.ABOUT_ME -> this.renderAboutMe(pageRendering)
            Page.CONTACTS -> this.renderContacts(pageRendering)
            Page.KNOWLEDGE -> this.renderKnowledge(pageRendering)
            Page.PROJECTS -> this.renderProjects(pageRendering)
        }
    }

    private fun renderMain(pageRendering: PageRendering) = tableWithDefaultHeader(pageRendering)

    private fun renderAboutMe(pageRendering: PageRendering): String {
        val span = 2

        return tableWithDefaultHeader(pageRendering, span) {
            val summaryInfoMap = mapOf(
                "Name" to config.name,
                "Nickname" to config.nickname,
                "Age" to config.age,
                "Location" to config.location
            )

            val emptySummary = summaryInfoMap.values.filterNotNull().isEmpty()

            val longBio = config.longBio

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

    private fun renderContacts(pageRendering: PageRendering): String {
        val span = 2

        return tableWithDefaultHeader(pageRendering, span) {
            val contacts = config.contacts

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

    private fun renderKnowledge(pageRendering: PageRendering): String {
        val span = 3

        return tableWithDefaultHeader(pageRendering, span) {
            val languages = config.languages
            val frameworks = config.frameworks

            this.buildKnowledge(languages, "Languages")
            this.buildKnowledge(frameworks, "Frameworks")

            if (languages.isEmpty() && frameworks.isEmpty()) {
                this.fullRowCellWithColumnSpan(span, "No knowledge provided.")
            }
        }
    }

    private fun renderProjects(pageRendering: PageRendering): String {
        val span = 4

        return tableWithDefaultHeader(pageRendering, span) {
            cellStyle {
                paddingLeft = 1
                paddingRight = 1
            }

            val projects = config.projects

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
        pageRendering: PageRendering,
        columnSpanValue: Int = 1,
        crossinline block: TableDsl.() -> Unit = {}
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
                config.banner?.let {
                    row {
                        cell(it) {
                            columnSpan = columnSpanValue
                        }
                    }
                }

                config.shortBio?.let {
                    row {
                        cell(
                            "${
                                pageRendering.echo?.let {
                                    "$it\n\n"
                                } ?: ""
                            }$it") {
                            columnSpan = columnSpanValue
                        }
                    }
                }

                row {
                    cellStyle {
                        alignment = TextAlignment.MiddleLeft
                    }

                    cell(Page.values().joinToString(separator = "\n") {
                        "* ${it.prettyName}: curl ${it.link(config.preferHttps)}"
                    }) {
                        columnSpan = columnSpanValue
                    }
                }
            }

            block()
        }.renderText().padding {
            top = 5
            bottom = 5
            left = 10
            right = 10
        }

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