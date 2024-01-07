import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jdk.incubator.vector.VectorOperators.LOG
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.logging.Filter


@Component
@Order(1)
interface TransactionFilter: Filter {
    @Throws(IOException::class, ServletException::class)
    fun doFilter(
        request: ServletRequest,
        response: ServletResponse?,
        chain: FilterChain
    ) {
        val req = request as HttpServletRequest
//        LOG.
//        chain.doFilter(request, response)
//        LOG.info(
//            "Committing a transaction for req : {}",
//            req.requestURI
//        )
    } // other methods
}