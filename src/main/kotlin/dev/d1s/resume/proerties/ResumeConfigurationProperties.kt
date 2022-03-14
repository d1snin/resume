package dev.d1s.resume.proerties

import dev.d1s.resume.proerties.model.Contact
import dev.d1s.resume.proerties.model.Knowledge
import dev.d1s.resume.proerties.model.Project
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@ConfigurationProperties("resume")
data class ResumeConfigurationProperties(
    var preferHttps: Boolean = true,
    var banner: String? = null,
    var name: String? = null,
    var nickname: String? = null,
    var shortBio: String? = null,
    var longBio: String? = null,
    var location: String? = null,
    var age: Int? = null,
    @Validated var languages: List<Knowledge> = listOf(),
    @Validated var frameworks: List<Knowledge> = listOf(),
    @Validated var contacts: List<Contact> = listOf(),
    @Validated var projects: List<Project> = listOf(),
)