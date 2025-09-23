package vn.iot.star.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AjaxCrudController {

    @GetMapping("/ajax-crud")
    public String ajaxCrudPage() {
        return "admin/ajax-crud"; // templates/admin/ajax-crud.html
    }
}
