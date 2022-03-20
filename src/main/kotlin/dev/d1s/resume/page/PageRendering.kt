package dev.d1s.resume.page

data class PageRendering(
    var page: Page = Page.MAIN,
    var kind: Kind = Kind.PLAIN_TEXT,
    var echo: String? = null
)

inline fun pageRendering(block: PageRendering.() -> Unit) =
    PageRendering().apply(block)