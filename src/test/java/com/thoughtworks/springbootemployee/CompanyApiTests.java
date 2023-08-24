package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.controller.CompanyController;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyApiTests {

    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        companyRepository.cleanAllCompanies();
        mockMvc = MockMvcBuilders.standaloneSetup(new CompanyController(companyRepository, employeeRepository)).build();
    }

    @Test
    void should_return_list_of_companies_when_perform_get_companies() throws Exception {
        // Given
        companyRepository.saveCompany(new Company(1L, "FirstCompany"));
        companyRepository.saveCompany(new Company(2L, "SecondCompany"));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("FirstCompany"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("SecondCompany"));
    }

    @Test
    void should_return_the_company_when_perform_get_company_given_the_company_id() throws Exception {
        // Given
        Company company = companyRepository.saveCompany(new Company(1L, "FirstCompany"));
        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value("FirstCompany"));
    }

    @Test
    void should_return_updated_company_when_perform_put_company_given_existing_company_id_and_updated_data() throws Exception {
        // Given
        Company existingCompany = companyRepository.saveCompany(new Company(1L, "FirstCompany"));
        Company updatedCompany = new Company(existingCompany.getId(), "UpdatedCompany");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/" + existingCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingCompany.getId()))
                .andExpect(jsonPath("$.name").value(updatedCompany.getName()));
    }

}
