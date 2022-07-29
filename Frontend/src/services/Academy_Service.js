import axios from "axios";
import Auth_Header from './Auth_Header';

const BASE_URL = "http://localhost:8080/academies/"

class Academy_Service {
    get(pageNumber, pageSize) {
        return axios.get(BASE_URL + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize, { headers: Auth_Header() });
    }

    getCount() {
        return axios.get(BASE_URL + "count", { headers: Auth_Header() });
    }

    create(academy) {
        return axios.post(BASE_URL, academy, { headers: Auth_Header() })
    }
    getuserbyId(academyId) {
        return axios.get(BASE_URL + academyId, { headers: Auth_Header() })
    }
    update(academyId, academy) {
        return axios.put(BASE_URL + academyId, academy, { headers: Auth_Header() })
    }
    delete(academyId) {
        return axios.delete(BASE_URL + academyId, { headers: Auth_Header() });
    }
}

export default new Academy_Service;
