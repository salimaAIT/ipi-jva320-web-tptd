package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
public class HomeController
{

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

        //Page d'accueil
        @GetMapping(value = "/")
        public String home( final ModelMap modelMap)
        {
            long Nbr = salarieAideADomicileService.countSalaries();
            modelMap.put("Nbr", Nbr);
            return "home";
        }

        //Afficher listes des salariés
    @GetMapping("/salaries")
    public String afficherListeSalaries(ModelMap modelMap) {
        List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        modelMap.put("salaries", salaries);
        return "list";
    }

        //Afficher la page des détails d'un salarié
    @GetMapping(value = "/salaries/{id}")
    public String SalariesDetails(final ModelMap modelMap,@PathVariable long id) {
       // SalarieAideADomicile aide = new SalarieAideADomicile("Jeannette Dupontelle",
            //    LocalDate.of(2021, 7, 1), LocalDate.now(), 0, 0, 10, 1, 0);
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        modelMap.put("salarie", salarie);
        modelMap.put("actionUrl", "/salaries/{id}");
        return "detail_Salarie";
    }

    //Création d'un salarié aide à domicile
    @GetMapping(value = "/salaries/aide/new")
    public String createSalarie(final ModelMap modelMap) {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        modelMap.put("salarie", salarie);
        modelMap.put("actionUrl", "/salaries/save");
        return "detail_Salarie";
    }



    @PostMapping("/salaries/save")
    public String saveSalarie(SalarieAideADomicile newSalarie) {

        try {
            salarieAideADomicileService.creerSalarieAideADomicile(newSalarie);
        } catch (SalarieException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/salaries/"+ newSalarie.getId();
    }
}
