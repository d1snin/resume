package dev.d1s.resume.proerties.model

import javax.validation.constraints.NotBlank

data class Knowledge(
    @NotBlank var name: String? = null,
    @NotBlank var knowledge: String? = null
)