package dev.d1s.resume.util

import dev.d1s.resume.page.Page
import dev.d1s.teabag.web.appendRootPath

fun Page.link(https: Boolean) = appendRootPath(path, replaceHttpWithHttps = https)