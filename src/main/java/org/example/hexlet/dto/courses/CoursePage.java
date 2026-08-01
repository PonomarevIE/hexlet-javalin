package org.example.hexlet.dto.courses;

import org.example.hexlet.model.Course;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoursePage {
    private Course course;
}