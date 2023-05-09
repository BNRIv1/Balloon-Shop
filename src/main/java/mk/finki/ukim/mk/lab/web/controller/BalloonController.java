package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/balloons")
public class BalloonController {

    private final BalloonService balloonService;
    private final ManufacturerService manufacturerService;

    public BalloonController(BalloonService balloonService,
                             ManufacturerService manufacturerService){
        this.balloonService = balloonService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getBalloonsPage(@RequestParam(required = false) String search, @RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if (search != null && !search.isEmpty()){
            model.addAttribute("balloons", this.balloonService.searchByNameOrDescription(search));
        }else{
            model.addAttribute("balloons", this.balloonService.listAll());
        }
        return "listBalloons";
    }

    @PostMapping
    public String chooseColor(HttpServletRequest request){
        String color = request.getParameter("color");
        request.getSession().setAttribute("color", color);
        return "redirect:/selectBalloon";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBalloon(@PathVariable Long id){
        this.balloonService.deleteById(id);
        return "redirect:/balloons";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddBalloonPage(Model model){
        List<Manufacturer> manufacturerList = this.manufacturerService.listAll();
        model.addAttribute("manufacturers", manufacturerList);
        return "add-balloon";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditBalloonPage(@PathVariable Long id, Model model){
        if (this.balloonService.findById(id).isPresent()){
            Balloon balloon = this.balloonService.findById(id).get();
            List<Manufacturer> manufacturers = this.manufacturerService.listAll();
            model.addAttribute("balloon", balloon);
            model.addAttribute("manufacturers", manufacturers);
            return "add-balloon";
        }
        return "redirect:/balloons?error=Balloon%20Not%20Found";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveBalloon (@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               @RequestParam String description,
                               @RequestParam Long manufacturerId){

        this.balloonService.save(id, name, description, manufacturerId);
        return "redirect:/balloons";
    }

}
