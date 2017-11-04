package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Service
public class StudentDAOImpl implements StudentDAO {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public StudentModel selectStudent(String npm) {
		StudentModel student = restTemplate.getForObject("http://localhost:8080/rest/student/view/" + npm,
				StudentModel.class);
		return student;
	}

	@Override
	public List<StudentModel> selectAllStudents() {
		ResponseEntity<StudentModel[]> sm = restTemplate.getForEntity("http://localhost:8080/rest/student/viewall/", StudentModel[].class);
		StudentModel[] smBody = sm.getBody();
		List<StudentModel> retSm = new ArrayList<>();
		for (StudentModel studentModel : smBody) {
			retSm.add(studentModel);
		}
		return retSm;
	}

	@Override
	public CourseModel selectCourse(String idc) {
		CourseModel c = restTemplate.getForObject("http://localhost:8080/rest/course/view/" + idc,
				CourseModel.class);
		return c;
	}
}