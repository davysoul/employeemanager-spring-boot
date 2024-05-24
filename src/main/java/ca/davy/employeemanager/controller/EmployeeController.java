package ca.davy.employeemanager.controller;

import ca.davy.employeemanager.model.Employee;
import ca.davy.employeemanager.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;


import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee")

public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }
    @PostMapping("/upload-image")
    public ResponseEntity<Map<?,?>> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
      Map<?,?> imageUrl = employeeService.uploadImage(file);
      return ResponseEntity.ok(imageUrl);
    }
    @PostMapping(value = "/add",consumes = MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee(@RequestPart("employee") String employeeJson,@RequestPart("image") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.readValue( employeeJson, Employee.class);



            Map<?,?> uploadResult  = employeeService.uploadImage(file);
            System.out.println("uploadResult "+uploadResult);
            employee.setImage_url(uploadResult.get("url").toString());
            employee.setPublicId(uploadResult.get("public_id").toString());
            Employee newEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(newEmployee,HttpStatus.CREATED);



    }
    @PutMapping(value = "/update/{id}" ,consumes = MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id")Long id,@RequestPart("employee") String employeeJson,@RequestPart(value = "image",required = false) MultipartFile file) throws IOException {
        Employee foundEmployee =  employeeService.findEmployeeById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee = objectMapper.readValue( employeeJson, Employee.class);
        foundEmployee.setEmployeeCode(employee.getEmployeeCode());
        foundEmployee.setName(employee.getName());
        foundEmployee.setEmail(employee.getEmail());
        foundEmployee.setJobTitle(employee.getJobTitle());
        foundEmployee.setPhone(employee.getPhone());
        if(file!=null){
            Map<?,?> uploadResult=  employeeService.replaceImage(file,employee.getPublicId());
            employee.setImage_url(uploadResult.get("url").toString());
            employee.setPublicId(uploadResult.get("public_id").toString());
            foundEmployee.setImage_url(uploadResult.get("url").toString());
            foundEmployee.setPublicId(uploadResult.get("public_id").toString());
        }else{
            foundEmployee.setImage_url(employee.getImage_url());
            foundEmployee.setPublicId(employee.getPublicId());
        }
         employeeService.updateEmployee(foundEmployee);

        return new ResponseEntity<>(foundEmployee,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) throws IOException {
        Employee foundEmployee =  employeeService.findEmployeeById(id);
        employeeService.deleteImageFromCloudinary(foundEmployee.getPublicId());
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/search")
    public List<Employee> searchEmployee(@RequestParam(name = "keyword",defaultValue = "")String keyword){
       return employeeService.searchEmployees("%"+keyword+"%");
    }
}
