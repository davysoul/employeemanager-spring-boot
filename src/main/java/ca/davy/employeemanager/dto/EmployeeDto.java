package ca.davy.employeemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String jobTitle;
    private String phone;
    private MultipartFile file;
    private String employeeCode;
}
