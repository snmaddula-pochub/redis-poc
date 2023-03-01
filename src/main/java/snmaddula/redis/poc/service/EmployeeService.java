package snmaddula.redis.poc.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import snmaddula.redis.poc.entity.Employee;
import snmaddula.redis.poc.repository.EmployeeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "EMPLOYEE_CACHE")
public class EmployeeService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final EmployeeRepository employeeRepository;

//    @CacheEvict(allEntries = true)
    public Employee create(Employee emp) {
        try {
            Employee empl = employeeRepository.save(emp);
            redisTemplate.opsForHash().put("EMP", empl.getId(), empl);
            return empl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating emp");
        }
    }

//    @Cacheable(key = "'all-employees'")
    public List<Employee> getAll() {
        log.info("Fetching employee data from the Database:: all");
        process();
        List<Employee> employees = employeeRepository.findAll();
        redisTemplate.opsForHash().put("EMP", "all-employees", employees);
        return employees;
    }

//    @Cacheable(key="'employee_id' + #id", value ="Employee", condition = "#result != null")
    public Employee getById(Long id) {
        if(redisTemplate.opsForHash().get("EMP", id) != null) {
            return (Employee) redisTemplate.opsForHash().get("EMP", id);
        }
        log.info("Fetching employee data from the Database: id :: {}", id);
        return employeeRepository.findById(id).orElse(null);
    }


//    @CachePut(key="'employee_id' + #id", value ="Employee")
    public Employee update(Long id, Employee emp) {
        employeeRepository.findById(id)
                .ifPresent(existingEmp -> {
            existingEmp.setFirstName(emp.getFirstName());
            existingEmp.setLastName(emp.getLastName());
            employeeRepository.save(existingEmp);
        });
        return employeeRepository.findById(id).orElse(null);
    }

//    @CacheEvict(key="'employee_id' + #id", value ="Employee")
    public void removeById(Long id) {
        employeeRepository.deleteById(id);
        redisTemplate.opsForHash().delete("EMP", id);
    }

    @SneakyThrows
    private void process() {
        // some process that takes ~2sec
        Thread.sleep(2000);
    }

}
