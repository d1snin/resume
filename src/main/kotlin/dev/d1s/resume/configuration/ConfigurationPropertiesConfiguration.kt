package dev.d1s.resume.configuration

import dev.d1s.resume.proerties.ResumeConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ResumeConfigurationProperties::class)
class ConfigurationPropertiesConfiguration