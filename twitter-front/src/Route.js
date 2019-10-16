import React, { Component } from 'react';
import { BrowserRouter as Router, Switch, Route, Link, Redirect} from 'react-router-dom';
import RegisterPage from "./components/RegisterPage"
import Profile from "./components/Profile"
import requireAuth from "./helpers/RouteHelper"
// import UpperBlogPage from './components/UpperBlogPage';
// import UploadBlog from './components/UploadBlog';
// import App from './App';
import PageNotFound from './Error/PageNotFound'
import Search from './components/Search';
import LoginPage from './components/Login';


class Routes extends Component {
  render() {
    return (
    
        
          <Switch>
              <Route exact path='/login' component={LoginPage} />
              <Route exact path='/register' component={RegisterPage} />
              <Route exact path='/' component={requireAuth(Profile)} />
              <Route exact path='/search/:text' component={requireAuth(Search)} />
              {/* <Route exact path='/post/:title/:id' component={UpperBlogPage} />
              <Route  exact path='/upload' component={UploadBlog} /> */}
              {/* <Route  exact path='/pagenotfound' component={PageNotFound} /> */}
              
              
              {/* <Route render={() => <Redirect to="/pagenotfound" />} /> */}
          </Switch>
      
      );
      }
    }
    //   constructor(props) {
 
    //     super(props);
 
    //     this.state = {
    //         data: ''
    //     };
 
    //     this.onchange=()=>{
    //       this.onChange();
    //     }
 
    // }
 
    // onChange(data) {
    //     this.setState({
    //         data
    //     });
    // }
 
    // // ...
 
    // render() {
    //     return (
    //         <ReactTextEdit data={this.state.data}
    //                        onChange={this.onchange}/>
    //     );
    // }
  //   
// }

export default Routes;
