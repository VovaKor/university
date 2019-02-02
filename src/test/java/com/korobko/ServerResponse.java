package com.korobko;

import com.korobko.models.Student;

import java.util.ArrayList;
import java.util.List;

public class ServerResponse {
    public _embedded _embedded;
    public _links _links;
}
class _links {
    public Self self;
}
class Self {
    public String href;
}
class _embedded {
    public List<Student> students = new ArrayList<>();
}