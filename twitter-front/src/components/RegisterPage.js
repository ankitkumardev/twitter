import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom';
import { checkUserName, register } from '../helpers/Ajax';




class RegisterPage extends Component {
    constructor(props){
        super();
        this.username = React.createRef();
        this.first = React.createRef();
        this.last = React.createRef();
        this.password = React.createRef();
        this.email = React.createRef();
        this.dob = React.createRef();
        this.username = React.createRef();
        this.terms = React.createRef();

            this.state = {
                color: "black",
                text:null,
                missing:false,
                passNotMatched:false
            }
    }
    checkPass(e,pass2){
        // e.preventDefault();
        // this.setState({
        //     passNotMatched:true
        //    });
        //    console.log(this.pass2)
        //    console.log(this.password.current.value )
        // if(this.password.current.value == pass2){
        //     this.setState({
        //         passNotMatched:false
        //        });
        // }
    }
    checkUserName(e){
        e.preventDefault();
        let userName = this.username.current.value;
        // console.log(use)
        if(userName.trim()==""){
            this.setState({
                color: "red",
                text : "User Name can't be blank !!"
              });
        }else{
            checkUserName(userName).then((res) => {
                console.log(res);
                if(res.status==200){
                    if(res.data){
                        this.setState({
                            color: "black",
                            text : "User Name available"
                        });
                    }else{
                        this.setState({
                            color: "red",
                            text : "User Name not available. Try again!"
                        });
                        this.username.current.value = "";
                    }
                    
                } else{
                this.setState({
                    color: "red",
                    text : "User Name not available. Try again!"
                });
                this.username.current.value = "";
                }
                
        }).catch((err)=>{
            this.setState({
            isWrongCred:true
            });
        });
    }

    }

    register(e){
        e.preventDefault();
        if(this.state.text==null||"Check User Name before submit !"==this.state.text){
            this.setState({
                color: "red",
                text : "Check User Name before submit !"
              });
        }
        // console.log("ankit    "+this.first+this.last+this.password+this.email+this.dob+this.username)
        if(!(this.state.text==null||"Check User Name before submit !"==this.state.text)&&this.terms.current.checked&&this.first.current.value&&this.last.current.value&&this.password.current.value&&this.email.current.value&&this.dob.current.value&&this.username.current.value){
            register({firstName:this.first.current.value,lastName:this.last.current.value,password:this.password.current.value,email:this.email.current.value,name:this.username.current.value,dob:this.dob.current.value}).then((res) => {
                console.log(res);
                if(res.status==200){
                    if(res.data){
                        this.props.history.push('/login');
                    }
                    // else{
                    //     this.props.history.push('/register');
                    // }
                    
                } 
                // else{
                //     this.props.history.push('/register');
                // }
                
           }).catch((err)=>{
             this.setState({
               isWrongCred:true
              });
           });
        }
        else{
            // console.log(this.password.current.value)
            // console.log("ankit    "+this.first+this.last+this.password+this.email+this.dob+this.username)

            this.setState({
                missing:true
               });
        }
        

    }

  render() {
    return ( 
        <div class="signup-form">
    <form onSubmit={(e)=> this.register(e)}>
		<h2>Register</h2>
		<p class="hint-text">Create your account. It's free and only takes a minute.</p>
        <div class="form-group row">
			<div class=" col-md-6">
                <input  type="text"  class="form-control" ref={this.first}  placeholder="First Name" required="required"/>
            </div>
            <div class=" col-md-6">
                <input  type="text" class="form-control" name="last_name" ref={this.last} placeholder="Last Name" required="required"/>   	
            </div>
        </div>
        <div class="form-group">
        	<input type="text" onChange={(e)=> this.setState({text:null})} class="form-control" name="username" placeholder="User Name"  ref={this.username} required="required"/>
            <Link class="ml-1" onClick={(e)=> this.checkUserName(e)}>Check UserName</Link><span style={{float:"right", color:this.state.color}}>{this.state.text}</span>
        </div>
        <div class="form-group">
        	<input type="email" class="form-control" name="email" ref={this.email} placeholder="Email" required="required"/>
        </div>
		<div class="form-group">
            <input type="password" class="form-control" name="password" ref={this.password} placeholder="Password" required="required"/>
        </div>
		{/* <div class="form-group">
            <input type="password" class="form-control" onChange={(e) => this.checkPass(e,this.value)} name="confirm_password" ref={this.password2} placeholder="Confirm Password" required="required"/>
            {this.state.passNotMatched==true&&<label style={{color:"red"}}> Password Not Matched</label>}
        </div>       */}
        <div class="form-group">
            <input type="date" class="form-control" name="DOB" placeholder="DOB" ref={this.dob} required="required"/>
        </div>    
        <div class="form-group">
			<label class="checkbox-inline"><input type="checkbox" ref={this.terms} required="required"/> I accept the <a href="#">Terms of Use</a> &amp; <a href="#">Privacy Policy</a></label>
		</div>
        {this.state.missing==true&&<label style={{float:"right", color:"red"}}> Something is missing !!</label>}
		<div class="form-group">
            <button type="submit" class="btn btn-success btn-lg btn-block" >Register Now</button>
        </div>
        
    </form>
    
	<div class="text-center">Already have an account? <Link to="/login">Sign in</Link></div>
</div>
    );
  }
}

export default RegisterPage;
