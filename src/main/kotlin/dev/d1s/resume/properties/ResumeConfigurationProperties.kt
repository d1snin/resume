package dev.d1s.resume.properties

import dev.d1s.resume.properties.model.Contact
import dev.d1s.resume.properties.model.Knowledge
import dev.d1s.resume.properties.model.Project
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Validated
@ConstructorBinding
@ConfigurationProperties("resume")
data class ResumeConfigurationProperties(
    val preferHttps: Boolean = true,
    val excludePages: List<String> = listOf(),
    val banner: String? = null,
    val name: String? = null,
    val nickname: String? = null,
    val shortBio: String? = null,
    val longBio: String? = null,
    val location: String? = null,
    val age: Int? = null,
    val languages: List<@Valid Knowledge> = listOf(),
    val frameworks: List<@Valid Knowledge> = listOf(),
    val databases: List<@Valid Knowledge> = listOf(),
    val editors: List<@Valid Knowledge> = listOf(),
    val operatingSystems: List<@Valid Knowledge> = listOf(),
    val ci: List<@Valid Knowledge> = listOf(),
    val contacts: List<@Valid Contact> = listOf(),
    val projects: List<@Valid Project> = listOf(),
)