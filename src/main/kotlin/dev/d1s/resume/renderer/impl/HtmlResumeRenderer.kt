package dev.d1s.resume.renderer.impl

import dev.d1s.resume.page.Page
import dev.d1s.resume.properties.ResumeConfigurationProperties
import dev.d1s.resume.properties.model.Knowledge
import dev.d1s.resume.renderer.ResumeRenderer
import dev.d1s.resume.service.ResumeService
import dev.d1s.resume.util.link
import dev.d1s.teabag.stdlib.text.isUrl
import dev.d1s.teabag.web.appendRootPath
import dev.d1s.teabag.web.currentUriWithNoPath
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HtmlResumeRenderer : ResumeRenderer {

    @Autowired
    private lateinit var config: ResumeConfigurationProperties

    @Autowired
    private lateinit var resumeService: ResumeService

    override fun render(page: Page): String {
        val pfpSource = appendRootPath("profile-picture.jpg", replaceHttpWithHttps = config.preferHttps)

        return createHTML().html {
            head {
                meta(charset = Charsets.UTF_8.name())
                meta("viewport", "width=device - width, initial - scale = 1.0")

                config.name?.let {
                    title(it)
                    meta("title", it)
                    meta("title".og(), it)
                    meta("title".twitter(), it)
                }

                config.shortBio?.let {
                    meta("description", it)
                    meta("description".og(), it)
                    meta("description".twitter(), it)
                }

                meta("type".og(), "website")
                meta("card".twitter(), "summary_large_image")

                val url = currentUriWithNoPath(replaceHttpWithHttps = config.preferHttps)
                meta("url".og(), url)
                meta("url".twitter(), url)

                meta("image".twitter(), pfpSource)
                meta("image".og(), pfpSource)

                styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css")

                script {
                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                }
            }

            body("justify-content-center") {
                div("bg-dark") {
                    nav("navbar navbar-expand-lg navbar-dark") {
                        div("container-fluid") {
                            button(classes = "navbar-toggler", type = ButtonType.button) {
                                attributes["data-bs-toggle"] = "collapse"
                                attributes["data-bs-target"] = "#navbarSupportedContent"
                                attributes["aria-controls"] = "navbarSupportedContent"
                                attributes["aria-expanded"] = "false"
                                attributes["aria-label"] = "Toggle navigation"

                                span("navbar-toggler-icon")
                            }

                            div("collapse navbar-collapse") {
                                id = "navbarSupportedContent"

                                ul("navbar-nav") {
                                    resumeService.getAvailablePages().forEach {
                                        li("nav-item") {
                                            a(
                                                it.link(config.preferHttps),
                                                classes = "nav-link"
                                            ) {
                                                attributes["aria-current"] = "page"

                                                span(
                                                    classes = if (it == page) {
                                                        "active"
                                                    } else {
                                                        ""
                                                    }
                                                ) {
                                                    +it.prettyName
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    div("container-fluid") {
                        div("container-sm justify-content-center mx-auto text-center text-white fst-italic m-5") {
                            img("Profile picture", pfpSource, "rounded-circle pfp")

                            config.shortBio?.let {
                                p("m-5") {
                                    +it
                                }
                            }
                        }
                    }

                    div("container-fluid bg-dark stub")
                }

                div("container-fluid stub")

                div("container-sm text-white mx-auto") {
                    when (page) {
                        Page.MAIN -> renderMain()
                        Page.ABOUT_ME -> renderAboutMe()
                        Page.CONTACTS -> renderContacts()
                        Page.KNOWLEDGE -> renderKnowledge()
                        Page.PROJECTS -> renderProjects()
                    }
                }

                style {
                    unsafe {
                        +"* { font-family: monospace, monospace; }"
                        +"body { background-color: #45474b; }"
                        +".active { color: #e9e9e9; }"
                        +".pfp { width: 13em; height: auto; }"
                        +".stub { height: 2em; }"
                        +".card { width: 35rem; }"
                    }
                }
            }
        }
    }

    private fun DIV.renderMain() {
        newSection("What should I see here?") {
            +"See what can I tell you "

            a(Page.ABOUT_ME.httpsLink) {
                +"about me"
            }

            +", my "

            a(Page.CONTACTS.httpsLink) {
                +"contacts"
            }

            +", "

            a(Page.KNOWLEDGE.httpsLink) {
                +"knowledge"
            }

            +" and "

            a(Page.PROJECTS.httpsLink) {
                +"projects"
            }

            +"."
        }
    }

    private fun DIV.renderAboutMe() {
        newAboutMeSection(config.longBio, "About me")
        newAboutMeSection(config.name, "Name")
        newAboutMeSection(config.nickname, "Nickname")
        newAboutMeSection(config.age?.toString(), "Age")
        newAboutMeSection(config.location, "Location")

        if (listOfNotNull(
                config.longBio,
                config.name,
                config.nickname,
                config.age,
                config.location
            ).isEmpty()
        ) {
            p {
                +"No information provided."
            }
        }
    }

    private fun DIV.renderContacts() {
        newSection("Contacts") {
            renderCards(config.contacts, "contacts", true, {
                it.service!!
            }) {
                val address = it.address!!

                if (address.isUrl()) {
                    a(address, classes = "card-text") {
                        +address
                    }
                } else {
                    code("card-text") {
                        +address
                    }
                }
            }
        }
    }

    private fun DIV.renderKnowledge() {
        newSection("Languages") {
            renderKnowledgeCards(config.languages, "languages")
        }

        newSection("Frameworks") {
            renderKnowledgeCards(config.frameworks, "frameworks")
        }
    }

    private fun DIV.renderProjects() {
        newSection("Projects") {
            renderCards(config.projects, "projects", true, {
                it.name!!
            }) {
                projectProperty("Name", false) {
                    +it.name!!
                }

                projectProperty("Description", false) {
                    +it.description!!
                }

                projectProperty("Status", false) {
                    code {
                        +it.status!!
                    }
                }

                projectProperty("URL", true) {
                    val url = it.url!!

                    a(url) {
                        +url
                    }
                }
            }
        }
    }

    private inline fun DIV.newSection(name: String, crossinline content: DIV.() -> Unit) {
        h2("fw-bold mb-5") {
            +name
        }

        div("mb-5") {
            content()
        }
    }

    private fun DIV.newAboutMeSection(content: String?, name: String) {
        content?.let {
            newSection(name) {
                p {
                    +it
                }
            }
        }
    }

    private inline fun <T> DIV.renderCards(
        items: Collection<T>,
        emptyMessageNoun: String,
        alignCenter: Boolean,
        crossinline header: (T) -> String,
        crossinline cardBody: DIV.(T) -> Unit
    ) {
        div(
            "row m-3 ${
                if (alignCenter) {
                    "justify-content-center"
                } else {
                    ""
                }
            }"
        ) {
            items.forEach {
                div(
                    "card col-4 text-white bg-dark m-3"
                ) {
                    div("card-header") {
                        +header(it)
                    }

                    div("card-body") {
                        cardBody(it)
                    }
                }
            }
        }

        if (items.isEmpty()) {
            p {
                +"No $emptyMessageNoun provided."
            }
        }
    }

    private fun DIV.renderKnowledgeCards(knowledge: Collection<Knowledge>, emptyMessageNoun: String) {
        renderCards(knowledge, emptyMessageNoun, false, {
            it.name!!
        }) {
            p {
                +it.knowledge!!
            }
        }
    }

    private inline fun DIV.projectProperty(name: String, last: Boolean, value: DIV.() -> Unit) {
        span("fw-bold") {
            +"$name: "
        }

        value()

        if (!last) {
            br()
        }
    }

    private val Page.httpsLink get() = link(config.preferHttps)

    private fun String.og() = "og:$this"
    private fun String.twitter() = "twitter:$this"
}
