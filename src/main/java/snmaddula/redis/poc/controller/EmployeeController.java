package snmaddula.redis.poc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import snmaddula.redis.poc.entity.Employee;
import snmaddula.redis.poc.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return employeeService.create(emp);
    }

    @PutMapping("{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
        return employeeService.update(id, emp);
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable Long id)   {
        employeeService.removeById(id);
    }
}
