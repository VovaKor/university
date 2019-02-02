package com.korobko.repositories;

import com.korobko.models.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "students", path = "students")
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByFirstName(@Param("firstName") String firstName);
    List<Student> findByLastName(@Param("lastName") String lastName);

    /**
     * An example of server response:
     * <pre>
     *     {
     *     "_embedded": {
     *         "students": [
     *             {
     *                 "firstName": "John",
     *                 "lastName": "White1",
     *                 "_links": {
     *                     "self": {
     *                         "href": "http://localhost:8080/students/1"
     *                     },
     *                     "student": {
     *                         "href": "http://localhost:8080/students/1"
     *                     },
     *                     "university": {
     *                         "href": "http://localhost:8080/students/1/university"
     *                     }
     *                 }
     *             },
     *             {
     *                 "firstName": "John",
     *                 "lastName": "White2",
     *                 "_links": {
     *                     "self": {
     *                         "href": "http://localhost:8080/students/2"
     *                     },
     *                     "student": {
     *                         "href": "http://localhost:8080/students/2"
     *                     },
     *                     "university": {
     *                         "href": "http://localhost:8080/students/2/university"
     *                     }
     *                 }
     *             }
     *         ]
     *     },
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8080/students/search/findStudentsByUniversityNameLike?name=My%20University"
     *         }
     *     }
     * }
     * </pre>
     * @param name the {@code University} name to search
     * @return the list of {@code Student} objects
     */
    List<Student> findStudentsByUniversityNameLike(@Param("name") String name);
    List<Student> findStudentsByUniversityId(@Param("id") Long id);
}
