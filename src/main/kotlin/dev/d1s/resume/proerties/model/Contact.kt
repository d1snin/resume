package dev.d1s.resume.proerties.model

import javax.validation.constraints.NotBlank

data class Contact(
    @NotBlank var service: String? = null,
    @NotBlank var address: String? = null
)