package com.IT342.DeliverYey.Service;

import com.IT342.DeliverYey.Entity.MenuEntity;
import com.IT342.DeliverYey.Entity.StudentEntity;
import com.IT342.DeliverYey.Repository.MenuRepository;
import com.IT342.DeliverYey.Repository.StudentRepository;
import com.IT342.DeliverYey.UserType;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private StudentRepository studentRepository;
    private Object student;

    public MenuEntity insertMenu(MenuEntity menu) {
        try {
            // Save the menu
            menuRepository.save(menu);

            // Assuming you want to update the student's user type if it's not null
            StudentEntity student = menu.getStudent();
            if (student != null) {
                student.setUserType(UserType.MENU);
                studentRepository.save(student);
            }

            return menu;
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return null or handle the error appropriately
            return null;
        }
    }


    public List<MenuEntity> getAllMenu() {
        List<MenuEntity> menuList = menuRepository.findAll();

        menuList.forEach(menu -> {
            Hibernate.initialize(menu.getStaff());
        });

        return menuList;
    }

    public MenuEntity updateMenu(int menuId, MenuEntity newMenuDetails) {
        try {
            MenuEntity menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new NoSuchElementException("Menu with ID " + menuId + " not found."));

            menu.setName(newMenuDetails.getName());
            menu.setCategory(newMenuDetails.getCategory());
            menu.setPrice(newMenuDetails.getPrice());

            return menuRepository.save(menu);
        } catch (NoSuchElementException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            return null; // or throw a custom exception, return a default value, etc.
        }
    }

    public String deleteMenu(int menuId) {
        Optional<MenuEntity> optionalMenu = menuRepository.findById(menuId);

        if (optionalMenu.isPresent()) {
            MenuEntity menu = optionalMenu.get();
            StudentEntity student = menu.getStudent();

            if (student != null) {
                student.setMenu(null);
                student.setUserType(UserType.STUDENT);
                studentRepository.save(student);
            }

            menuRepository.deleteById(menuId);
            return "Menu " + menuId + " is successfully deleted!";
        } else {
            return "Menu " + menuId + " does not exist.";
        }
    }

    private boolean isValidContactInfo(String contactInfo) {
        return contactInfo != null && Pattern.matches("^09\\d{9}$", contactInfo);
    }
}
