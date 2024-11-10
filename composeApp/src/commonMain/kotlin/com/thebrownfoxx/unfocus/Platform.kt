package com.thebrownfoxx.unfocus

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform