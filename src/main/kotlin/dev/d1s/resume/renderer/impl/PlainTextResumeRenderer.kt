package dev.d1s.resume.renderer.impl

import com.jakewharton.picnic.*
import dev.d1s.resume.constant.PLAIN_TEXT_RESUME_CACHE
import dev.d1s.resume.constant.RESUME_FILE_LOCATION
import dev.d1s.resume.page.Page
import dev.d1s.resume.page.PageRendering
import dev.d1s.resume.properties.ResumeConfigurationProperties
import dev.d1s.resume.properties.model.Knowledge
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.resume.util.link
import dev.d1s.teabag.stdlib.text.padding
import dev.d1s.teabag.stdlib.text.wrapLines
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PlainTextResumeRenderer : ResumeRenderer {

    private companion object {
        private const val MAX_LINE_WIDTH = 70
    }

    @Autowired
    private lateinit var config: ResumeConfigurationProperties

    @Cacheable(PLAIN_TEXT_RESUME_CACHE)
    override fun render(pageRendering: PageRendering): String {
        return this.renderTableWithDefaultHeader(pageRendering) {
            when (pageRendering.page) {
                Page.MAIN -> {} // nothing to render
                Page.ABOUT_ME -> renderAboutMe()
                Page.CONTACTS -> renderContacts()
                Page.KNOWLEDGE -> renderKnowledge()
                Page.PROJECTS -> renderProjects()
                Page.RESUME -> renderResume()
            }
        }
    }

    private fun TableDsl.renderAboutMe() {
        val summaryInfoMap = mapOf(
            "Name" to config.name!!.toString(),
            "Nickname" to config.nickname!!,
            "Age" to config.age!!.toString(),
            "Location" to config.location!!
        )

        val emptySummary = summaryInfoMap.values.isEmpty()

        val longBio = config.longBio

        longBio?.let {
            row {
                cellWithWrappedLines(
                    asteriskList(Page.ABOUT_ME.prettyName)
                            + "\n"
                            + it
                )
            }
        }

        if (!emptySummary) {
            rowWithWrappedLines(
                asteriskList(
                    *summaryInfoMap.map {
                        it.key to it.value
                    }.toTypedArray()
                )
            )
        }

        if (emptySummary && longBio == null) {
            row(
                asteriskList("No information provided.")
            )
        }
    }

    private fun TableDsl.renderContacts() {
        val contacts = config.contacts

        if (contacts.isNotEmpty()) {
            contacts.forEach {
                rowWithWrappedLines(
                    asteriskList(
                        "Service" to it.service,
                        "Address" to it.address
                    )
                )
            }
        } else {
            row(
                asteriskList("No contacts provided.")
            )
        }
    }

    private fun TableDsl.renderKnowledge() {
        val languages = config.languages
        val frameworks = config.frameworks
        val databases = config.databases
        val editors = config.editors
        val operatingSystems = config.operatingSystems
        val ci = config.ci

        this.buildKnowledge("Languages", languages)
        this.buildKnowledge("Frameworks", frameworks)
        this.buildKnowledge("Databases", databases)
        this.buildKnowledge("Editors", editors)
        this.buildKnowledge("Operating systems", operatingSystems)
        this.buildKnowledge("CI tools", ci)

        if ((languages
                    + frameworks
                    + databases
                    + editors
                    + operatingSystems
                    + ci).isEmpty()
        ) {
            row(
                asteriskList("No knowledge provided.")
            )
        }
    }

    private fun TableDsl.renderProjects() {
        val projects = config.projects

        if (projects.isNotEmpty()) {
            projects.forEach {
                rowWithWrappedLines(
                    asteriskList(
                        "Name" to it.name,
                        "Description" to it.description,
                        "Status" to it.status,
                        "URL" to it.url
                    )
                )
            }
        } else {
            row(
                asteriskList("No projects provided.")
            )
        }
    }

    private fun TableDsl.renderResume() {
        row(
            asteriskList("You can download my resume here (in russian)" to RESUME_FILE_LOCATION)
        )
    }

    private inline fun renderTableWithDefaultHeader(
        pageRendering: PageRendering,
        crossinline block: TableDsl.() -> Unit = {}
    ): String =
        table {
            style {
                borderStyle = BorderStyle.Hidden
            }

            cellStyle {
                alignment = TextAlignment.MiddleLeft
                padding = 1
                border = true
            }

            header {
                config.banner?.let {
                    row {
                        cell(it) { // wrapping is not being applied for the banner
                            alignment = TextAlignment.MiddleCenter
                        }
                    }
                }

                config.shortBio?.let {
                    row {
                        cell(
                            "${
                                pageRendering.echo?.let { echo ->
                                    "$echo\n\n"
                                } ?: ""
                            }$it".wrapLines()) {
                            alignment = TextAlignment.MiddleCenter
                        }
                    }
                }

                this@table.rowWithWrappedLines(
                    asteriskList(
                        *Page.values().map {
                            it.prettyName to "curl ${it.link(config.preferHttps)}"
                        }.toTypedArray()
                    )
                )
            }

            block()
        }.renderText().padding(1)

    private fun asteriskList(vararg values: String) = values.joinToString("\n") {
        "* $it"
    }

    private fun asteriskList(vararg pairs: Pair<String, String?>): String = this.asteriskList(
        *pairs.map {
            "${it.first}: ${it.second}"
        }.toTypedArray()
    )

    private fun TableDsl.buildKnowledge(name: String, knowledge: List<Knowledge>) {
        if (knowledge.isNotEmpty()) {
            rowWithWrappedLines(
                asteriskList(
                    "$name:",
                    *knowledge.map {
                        "${it.name}: ${it.knowledge}"
                    }.toTypedArray()
                )
            )
        }
    }

    private fun String.wrapLines() = this.wrapLines(MAX_LINE_WIDTH)

    private fun RowDsl.cellWithWrappedLines(value: String) {
        cell(value.wrapLines())
    }

    private fun TableDsl.rowWithWrappedLines(value: String) {
        row(value.wrapLines())
    }
}