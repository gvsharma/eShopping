package com.pj.springsecurity.web.api.product;

import com.pj.springsecurity.dto.CategoryDTO;
import com.pj.springsecurity.exceptions.exceptions.GenericException;
import com.pj.springsecurity.model.order.Category;
import com.pj.springsecurity.repo.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController
{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ModelMapper modelMapper)
    {
        this.categoryRepository = categoryRepository;
        this.modelMapper=modelMapper;
    }

    @GetMapping(path = "/list")
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id,HttpServletRequest request)
    {
        Optional<Category> category=categoryRepository.findById(id);
        if(!category.isPresent())
        {
            throw new GenericException(" Category with id:"+id+" is not Found","",HttpStatus.NOT_FOUND,
                    LocalDateTime.now(),null,request.getRequestURI());
        }
        return new ResponseEntity<>(category.get(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public Category createNewCategory(@RequestBody CategoryDTO categoryDTO)
    {
        Category category=modelMapper.map(categoryDTO,Category.class);
        return categoryRepository.saveAndFlush(category);
    }

    @PutMapping(value = "/update")
    public Category updateCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request)
    {
        Category category=modelMapper.map(categoryDTO,Category.class);
        if(!categoryRepository.findById(category.getId()).isPresent())
        {
            throw new GenericException("Failed to update the Category. Category with id:"+category.getId()+" is not Found","",HttpStatus.NOT_FOUND,
                    LocalDateTime.now(),null,request.getRequestURI());
        }
        return categoryRepository.saveAndFlush(category);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteCategoryById(@PathVariable Long id, HttpServletRequest request)
    {
        if(!categoryRepository.findById(id).isPresent())
        {
            throw new GenericException("Failed to delete the Category. Category with id:"+id+" is not Found","",HttpStatus.NOT_FOUND,
                    LocalDateTime.now(),null,request.getRequestURI());
        }
        categoryRepository.deleteById(id);
    }
}