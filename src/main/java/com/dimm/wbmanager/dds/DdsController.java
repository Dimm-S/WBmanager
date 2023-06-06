package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.dto.DdsNewOperationDto;
import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.dto.DdsSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/dds")
public class DdsController {
    private final DdsService service;

    @GetMapping
    public String getDds(Model model) {
        log.info("Запрошен эндпойнт GET:/dds");
        List<DdsSimpleDto> simpleDds = service.getDds();
        List<String> operItems = service.getItemsNames("oper");
        List<String> investItems = service.getItemsNames("invest");
        List<String> finItems = service.getItemsNames("fin");
        model.addAttribute("dds", simpleDds);
        model.addAttribute("operItems", operItems);
        model.addAttribute("investItems", investItems);
        model.addAttribute("finItems", finItems);
        return "dds";
    }

    @GetMapping("/operations")
    public String getAllOperations(Model model) {
        log.info("Запрошен эндпойнт GET:/dds/operations");
        List<DdsOperationDto> operations = service.getAllOperations();
        model.addAttribute("operations", operations);
        return "operations";
    }

    @GetMapping("/operations/add")
    public String showAddOperationForm(Model model) {
        log.info("Запрошен эндпойнт GET:/dds/operations/add (отображение формы добавления операции)");
        model.addAttribute("newOperationForm", new DdsNewOperationDto());
        model.addAttribute("ddsItemsList", service.getAllItemsNames());
        model.addAttribute("ddsAccountsList", service.getAllAccountsNames());
        return "addNewOperation";
    }

    @PostMapping("/operations/create")
    public String createOperation(
            @Valid @ModelAttribute("newOperationForm") DdsNewOperationDto newOperationform,
            BindingResult bindingResult, Model model) {
        log.info("Запрошен эндпойнт GET:/dds/operations/create (отправка введённых в форму данных");

        if (bindingResult.hasErrors()) {
            return "addNewOperation";
        }

        service.createOperation(newOperationform);

        return "redirect:/dds/operations";
    }
}
