package main;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoListController {
    @RequestMapping("/")
    public String index(){
        return String.valueOf("The random value is - " + (int)(10000 + Math.random() * 100000));
    }
}
