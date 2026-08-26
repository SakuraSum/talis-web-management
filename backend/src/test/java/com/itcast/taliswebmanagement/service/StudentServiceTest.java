package com.itcast.taliswebmanagement.service;

import com.itcast.taliswebmanagement.mapper.StudentMapper;
import com.itcast.taliswebmanagement.pojo.Student;
import com.itcast.taliswebmanagement.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentMapper studentMapper;

    @Test
    void violation_shouldIncrementCountAndScore() {
        Student student = new Student();
        student.setId(1);
        student.setViolationCount((short) 0);
        student.setViolationScore((short) 0);
        when(studentMapper.getStudentById(1)).thenReturn(student);

        studentService.violation(1, (short) 5);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentMapper).setViolation(captor.capture());

        Student updated = captor.getValue();
        assertEquals(1, updated.getViolationCount());
        assertEquals(5, updated.getViolationScore());
        assertNotNull(updated.getUpdateTime());
    }

    @Test
    void violation_shouldAccumulateOnExistingScore() {
        Student student = new Student();
        student.setId(1);
        student.setViolationCount((short) 2);
        student.setViolationScore((short) 10);
        when(studentMapper.getStudentById(1)).thenReturn(student);

        studentService.violation(1, (short) 3);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentMapper).setViolation(captor.capture());

        Student updated = captor.getValue();
        assertEquals(3, updated.getViolationCount());
        assertEquals(13, updated.getViolationScore());
    }

    @Test
    void saveStudent_shouldFillCreateTime() {
        Student student = new Student();
        student.setName("张三");

        studentService.saveStudent(student);

        assertNotNull(student.getCreateTime());
        assertNotNull(student.getUpdateTime());
        verify(studentMapper).saveStudent(student);
    }

    @Test
    void updateStudent_shouldFillUpdateTime() {
        Student student = new Student();
        student.setId(1);
        student.setName("李四");

        studentService.updateStudent(student);

        assertNotNull(student.getUpdateTime());
        verify(studentMapper).updateStudent(student);
    }

    @Test
    void getStudentById_returnCorrectStudent() {
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        when(studentMapper.getStudentById(1)).thenReturn(student);

        Student result = studentService.getStudentById(1);

        assertNotNull(result);
        assertEquals("张三", result.getName());
    }
}
