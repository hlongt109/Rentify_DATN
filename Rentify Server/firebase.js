// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyDZkq4eY71PIUL6POWvnZ70RWvX5NiSysc",
    authDomain: "rentify-app-d51d7.firebaseapp.com",
    databaseURL: "https://rentify-app-d51d7-default-rtdb.firebaseio.com",
    projectId: "rentify-app-d51d7",
    storageBucket: "rentify-app-d51d7.firebasestorage.app",
    messagingSenderId: "308327500809",
    appId: "1:308327500809:web:6050b90282117df5413cd8",
    measurementId: "G-MK5BTP16NR"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);