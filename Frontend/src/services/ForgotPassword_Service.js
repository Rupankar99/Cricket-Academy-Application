import axios from "axios";

const EMAIL_URL = "http://localhost:8080/api/auth/forgot-password?email="
const PASSWORD_URL = "http://localhost:8080/api/auth/reset-password?token="

class ForgotPassword_Service {
    sendEmail(email) {
        return axios.post(EMAIL_URL + email)
    }

    resetPassword(token, password) {
        return axios.put(PASSWORD_URL + token + "&password=" + password)
    }
}

export default new ForgotPassword_Service