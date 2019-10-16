import React, { Component } from 'react';
import '../App.css';
import './RegisterPage.css'
import { doLogin } from '../helpers/Ajax';
import { setCookie, getCookie } from '../helpers/Cookie';
import { Link } from 'react-router-dom';



class LoginPage extends Component {

  constructor(props){
    super();
        this.state = {
            isWrongCred:false,
            text:"Wrong credential !"
        }
  }
  componentWillMount(){
    if(!(getCookie("token")==null ||getCookie("token").toString().trim()=="")){
      this.props.history.push('/');
    }
  }

  submit(e) { 
    e.preventDefault();
    let userName = this.user.value;
    let password = this.password.value;
    if(userName.trim()&&password.trim()){
    doLogin({ username: userName, password: password}).then((res) => {
         console.log(res);
         if(res.status==200){
          setCookie("token" , res.data.token);
          var url_string = window.location.href;
          var url = new URL(url_string);
          var param = url.searchParams.get("redirect");
          if(param==null|| param.toString().trim()=="")
            param = "/";
          this.props.history.push(param);
         } else{
           this.setState({
            isWrongCred:true,
            text:"Wrong credential !"
           });
         }
         
    }).catch((err)=>{
      this.setState({
        isWrongCred:true,
        text:"Wrong credential !"
       });
    });
  }else{
    this.setState({
      isWrongCred:true,
      text:"Please enter credential !"
     });
  }
    
  }

  render() {
    return ( 
      <div class="signup-form">
      <form>
      <h2>Login</h2>
          	
          <div class="form-group">
            <input type="text" class="form-control" name="email" placeholder="UserName" onChange={(e)=>{this.setState({isWrongCred:false})}} required="required" ref={(c) => this.user = c}/>
          </div>
      <div class="form-group">
              <input type="password" class="form-control" name="password" onChange={(e)=>{this.setState({isWrongCred:false})}} placeholder="Password" required="required" ref={(c) => this.password = c}/>
      </div>
      {this.state.isWrongCred&&<div class="form-group">
          <p style={{color:"red"}}>{this.state.text}</p>
      </div>
      }
         
      <div class="form-group">
              <button type="submit" class="btn btn-success btn-lg btn-block" onClick={(e)=> this.submit(e)}>Login</button>
          </div>
          
      </form>
    <div class="text-center">New User ? <Link to="/register">Register</Link></div>
  </div>
    );
  }
}

export default LoginPage;
