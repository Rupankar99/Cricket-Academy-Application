import axios from "axios";
import Auth_Header from './Auth_Header';

const BASE_URL = "http://localhost:8080/students/";


class Student_Service {
    getAllStudents(pageNumber, pageSize, sortBy, sortDir) {
        return axios.get(BASE_URL + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize + "&sortBy=" + sortBy + "&sortDir=" + sortDir, { headers: Auth_Header() })
    }
    delete(id) {
        return axios.delete(BASE_URL + id + "/", { headers: Auth_Header() })
    }
    getStudentbyId(id) {
        return axios.get(BASE_URL + id + "/", {
            headers: Auth_Header()
        })
    }
    count() {
        return axios.get(BASE_URL + "count", {
            headers: Auth_Header()
        })
    }
    editStudent(id, student) {
        return axios.put(BASE_URL + id + "/", student, {
            headers: Auth_Header()
        })
    }
}
export default new Student_Service;