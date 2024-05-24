package ca.davy.employeemanager.service;

import ca.davy.employeemanager.model.Employee;
import ca.davy.employeemanager.repos.EmployeeRepos;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface EmployeeService  {
        public Employee addEmployee(Employee employee);
       public List<Employee> findAll();
       public Employee updateEmployee(Employee employee);
       public void deleteEmployee(Long id);
       public Employee findEmployeeById(Long id);
       public Map<?,?> uploadImage(MultipartFile file) throws IOException;
       public Map<?,?> replaceImage(MultipartFile file,String existingImagePublicId) throws IOException;
       public void deleteImageFromCloudinary(String publicId) throws IOException;
       public List<Employee> searchEmployees(String keyword);

}

