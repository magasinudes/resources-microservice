package resources;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ResourcesController {

    @RequestMapping("/")
    public String index() {
        return "<h1>Ressource index!<h1>";
    }

    @RequestMapping("/health")
    public String health() {
        return "ok";
    }

}
