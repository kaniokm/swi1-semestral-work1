import axios from "axios";

export function usedTimes() {
    return axios.get('http://localhost:8080/reservations/localdate/times?localDate=2021-04-04').then(function (response) {
        return response.data;
    })
}