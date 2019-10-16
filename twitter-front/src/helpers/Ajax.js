import axios from 'axios';
import myConstClass from './GlobalVariable';
import {  getCookie } from '../helpers/Cookie';


export function doLogin(data) {
    let apiUri = "authenticate";
    let apiUrl = myConstClass.apiIpPort +apiUri;
    return axios.post(apiUrl, data, {} , {timeout :120000});
}

export function checkUserName(data) {
    let apiUri = "checkuser/"+data;
    let apiUrl = myConstClass.apiIpPort +apiUri;
    return axios.get(apiUrl, {} , {timeout :120000});
}
export function register(data) {
    let apiUri = "register/";
    let apiUrl = myConstClass.apiIpPort +apiUri;
    return axios.post(apiUrl,data, {} , {timeout :120000});
}
export function getHome() {
    let apiUri = "rest/home";
    let apiUrl = myConstClass.apiIpPort +apiUri;
    let token = getCookie("token");
    let headers={
        'Authentication': token
    };
    console.log("token"+token)
    return axios.get(apiUrl, {'headers':headers} , {timeout :120000});
}
export function insertPostApi(data) {
    let apiUri = "rest/post";
    let apiUrl = myConstClass.apiIpPort +apiUri;
    let token = getCookie("token");
    let headers={
        'Authentication': token
    };
    console.log("token"+token)
    return axios.post(apiUrl,data, {'headers':headers} , {timeout :120000});
}
export function searchPeople(data) {
    let apiUri = "rest/search/"+data;
    let apiUrl = myConstClass.apiIpPort +apiUri;
    let token = getCookie("token");
    let headers={
        'Authentication': token
    };
    console.log("token"+token)
    return axios.get(apiUrl, {'headers':headers} , {timeout :120000});
}
export function followOrUnFollowApi(data,parameter) {
    let apiUri = "rest/";
    if(parameter){
        apiUri+="follow/"
    }else{
        apiUri+="unfollow/"
    }
    apiUri+=data;
    let apiUrl = myConstClass.apiIpPort +apiUri;
    let token = getCookie("token");
    let headers={
        'Authentication': token
    };
    console.log("token"+token)
    return axios.get(apiUrl, {'headers':headers} , {timeout :120000});
}
