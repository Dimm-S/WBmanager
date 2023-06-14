package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.dto.DdsNewItemDto;
import com.dimm.wbmanager.dds.dto.DdsNewOperationDto;
import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.dto.DdsSimpleDto;
import com.dimm.wbmanager.dds.model.DdsItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public String getAllOperations(@RequestParam (required = false, defaultValue = "2020-01-01") String from,
                                   @RequestParam (required = false,
                                           defaultValue = "#{T(java.time.LocalDate).now().toString}") String to,
                                   Model model) {
        log.info("Запрошен эндпойнт GET:/dds/operations");
        List<DdsOperationDto> operations = service.getAllOperations(from, to);
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
        log.info("Запрошен эндпойнт POST:/dds/operations/create (отправка введённых в форму данных");

        if (bindingResult.hasErrors()) {
            return "addNewOperation";
        }

        service.createOperation(newOperationform);

        return "redirect:/dds/operations";
    }

    @PostMapping("/operations/delete")
    public String deleteOperation(@RequestParam String id, Model model) {
        log.info("Запрошен эндпойнт DELTE:/dds/operations/delete (удаление операции) {}", id);
        service.deleteOperation(Long.parseLong(id));
        return "redirect:/dds/operations";
    }

    @GetMapping("/items")
    public String getAllItems(Model model) {
        log.info("Запрошен эндпойнт GET:/dds/items (отображение всех статей)");
        List<DdsItem> items = service.getAllItems();
        model.addAttribute("items", items);
        return "ddsItems";
    }

    @GetMapping("/items/add")
    public String showAddItemForm(Model model) {
        log.info("Запрошен эндпойнт GET:/dds/items/add (отображение формы добавления статьи)");
        model.addAttribute("newItemForm", new DdsNewItemDto());
        model.addAttribute("ddsGroupsList", service.getAllGroupsNames());
        return "addNewItem";
    }

    @PostMapping("/items/create")
    private String createItem(
            @Valid @ModelAttribute("newItemForm") DdsNewItemDto newItemDto,
            BindingResult bindingResult, Model model) {
        log.info("Запрошен эндпойнт POST:/dds/operations/create (отправка введённых в форму данных");

        if (bindingResult.hasErrors()) {
            return "addNewItem";
        }

        service.createItem(newItemDto);
        return "redirect:/dds/items";
    }
}
