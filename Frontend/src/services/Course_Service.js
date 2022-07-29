import axios from "axios";
import Auth_Header from './Auth_Header';

const BASE_URL = "http://localhost:8080/courses/"

class Course_Service {

    getAllCourses(pageNumber, pageSize, sortBy, sortDir) {
        return axios.get(BASE_URL + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize + "&sortBy=" + sortBy + "&sortDir=" + sortDir, {
            headers: Auth_Header()
        })
    }

    countCourses() {
        return axios.get(BASE_URL + "course-count", {
            headers: Auth_Header()
        })
    }
    getCoursesInAcademy(academyId) {
        return axios.get(BASE_URL + academyId + "/", {
            headers: Auth_Header()
        })
    }

    getCourseById(courseId) {
        return axios.get(BASE_URL + courseId, {
            headers: Auth_Header()
        })
    }

    createCourseInAcademy(academyId, course) {
        return axios.post(BASE_URL + academyId + "/create-course", course, {
            headers: Auth_Header()
        })
    }

    updateCourse(courseId, course) {
        return axios.put(BASE_URL + courseId, course, {
            headers: Auth_Header()
        })
    }

    deleteCourse(courseId) {
        return axios.delete(BASE_URL + courseId, {
            headers: Auth_Header()
        })
    }
}

export default new Course_Service;