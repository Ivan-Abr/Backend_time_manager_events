import com.example.application.exceptions.TokenValidationException
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.Filter
import jakarta.servlet.annotation.WebInitParam

@WebFilter(filterName = "myFilter",
    urlPatterns = ["/*"],
    )
class TokenValidationFilter : Filter {

    override fun init(filterConfig: FilterConfig?) {
        // Реализация метода init
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = (request as HttpServletRequest).getHeader("Authorization")
        println("PRIVET ETO PRIGOZHIN ZHENYA")
        if (!isValidToken("krut")) {
            throw TokenValidationException("Invalid token")

        }
        println("OOOOOOO BLYA A MY I NE DUMALY")
        chain.doFilter(request, response)
    }

    override fun destroy() {
        // Реализация метода destroy
    }

    private fun isValidToken(token: String?): Boolean {
        if (token =="de-bil") return true
        // Реализация вашей функции для проверки токена
        // Вернуть true, если токен действителен, и false в противном случае
        return false
    }
}
