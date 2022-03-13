package dev.d1s.resume.proerties.model

import javax.validation.constraints.NotBlank

data class Project(
    @NotBlank var name: String? = null,
    @NotBlank var description: String? = null,
    @NotBlank var status: String? = null,
    @NotBlank var url: String? = null
)