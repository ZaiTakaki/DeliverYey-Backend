package com.IT342.DeliverYey.Controller;

import com.IT342.DeliverYey.Entity.MenuEntity;
import com.IT342.DeliverYey.Entity.OrderEntity;
import com.IT342.DeliverYey.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "http://localhost:5173")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/insertMenu")
    public MenuEntity insertMenu(@RequestBody MenuEntity menu) {
        return menuService.insertMenu(menu);
    }

    @GetMapping("/getAllMenu")
    public List<MenuEntity> getAllMenu(){
        return menuService.getAllMenu();
    }

    @PutMapping("/updateMenu")
    public MenuEntity updateMenu(@RequestParam int menuId, @RequestBody MenuEntity newMenuDetails){
        return menuService.updateMenu(menuId, newMenuDetails);
    }

    @DeleteMapping("/deleteMenu/{menuId}")
    public String deleteMenu(@PathVariable int menuId){
        return menuService.deleteMenu(menuId);
    }
}
