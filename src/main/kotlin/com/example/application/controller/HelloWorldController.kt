import com.example.application.entity.Event
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller

class HelloWorldController
{
    @GetMapping()
    fun HelloWorld(): String{
        return "Hello World!"
    }
}