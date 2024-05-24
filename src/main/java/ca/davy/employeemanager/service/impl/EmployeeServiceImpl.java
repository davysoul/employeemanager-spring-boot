package ca.davy.employeemanager.service.impl;

import ca.davy.employeemanager.dto.EmployeeDto;
import ca.davy.employeemanager.exception.UserNotFoundException;
import ca.davy.employeemanager.model.Employee;
import ca.davy.employeemanager.repos.EmployeeRepos;
import ca.davy.employeemanager.service.EmployeeService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepos employeeRepos;
    private final Cloudinary cloudinary;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepos employeeRepos, Cloudinary cloudinary) {
        this.employeeRepos = employeeRepos;
        this.cloudinary = cloudinary;
    }

    @Override
    public Employee addEmployee(Employee employee) {
//        Employee employee = new Employee();
//        employee.setEmail(employeeDto.getEmail());
//        employee.setName(employeeDto.getName());
//        employee.setJobTitle(employeeDto.getJobTitle());
        if(employee.getEmployeeCode()==null){employee.setEmployeeCode(UUID.randomUUID().toString());}
        return employeeRepos.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepos.findAll();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepos.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
       employeeRepos.deleteById(id);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepos.findEmployeeById(id).orElseThrow(()->new UserNotFoundException("User by id "+id +" was not found"));
    }

    @Override
    public Map<?,?> uploadImage(MultipartFile file) throws IOException {
        return (Map<?,?>) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    @Override
    public Map<?,?> replaceImage(MultipartFile newFile,String existingImagePublicId) throws IOException {
        Map params = ObjectUtils.asMap("overwrite", true, "public_id", existingImagePublicId);
        //File newImageFile = convertMultipartFileToFile(newFile);
        return (Map<?,?>) cloudinary.uploader().upload(newFile.getBytes(),params);
    }

    @Override
    public void deleteImageFromCloudinary(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepos.searchEmployee(keyword);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }

}
