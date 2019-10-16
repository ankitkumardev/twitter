import React, { Component } from 'react';
import './App.css';
// import Footer from "./constants/Footer";
// import Header from "./constants/Header";
import Routes from './Route';
import ErrorPage from './Error/ErrorPage';

class App extends Component {
  render() {
    return (
      <div className="blogPage">
        {/* <Header/> */}
        <ErrorPage>
        <Routes/>
      
</ErrorPage>

        {/* <Footer/> */}

       

      </div>
    );
  }
}

export default App;
