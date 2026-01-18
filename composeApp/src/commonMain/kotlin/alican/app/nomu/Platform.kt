package alican.app.nomu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform