import React, { Component } from 'react';
import '../App.css';
import './RegisterPage.css'

import { getHome ,insertPostApi} from '../helpers/Ajax';
import { setCookie, deleteCookie } from '../helpers/Cookie';


class Profile extends Component {

    constructor(props){
        super();
        this.search = React.createRef();
        this.myPost = React.createRef();
        this.state = {
            res:null
            // searchRes:null
        }
    }
    insertPost(e){
        e.preventDefault()
        console.log(this.myPost)
        if(this.myPost.current.value.trim()!=""){
            insertPostApi({text:this.myPost.current.value.trim()})
                .then((res) => {
                    console.log(res);
                    if(res.status==200){
                        // this.setState({
                        //     res:res
                        // });
                        window.location.reload();
                    }
                    
            }).catch((err)=>{
            });  
        }

    }
    searchPeople(e){
        e.preventDefault();
        if(this.search.current.value.trim()!=""){
                    this.props.history.push({
                        pathname: '/search/'+this.search.current.value.trim()
                      })           
        }
    }
    logout(e){
        e.preventDefault();
        deleteCookie("token");
        this.props.history.push("/login");
        
    }
    componentWillMount(){
        getHome().then((res) => {
            console.log(res);
            if(res.status==200){
                this.setState({
                    res:res
                   });
            } else{
                this.props.history.push('/');
            }
            
       }).catch((err)=>{
        this.props.history.push('/');
       });
       
    }

  render() {
    return ( 
        this.state.res&&<div>
        <nav class="navbar navbar-light bg-white">
        <a href="/" class="navbar-brand">Bootsbook</a>
        <form class="form-inline">
            <a href="#" onClick={(e)=>{this.logout(e)}} className="mr-4">Logout</a>
            <div class="input-group">
                <input type="text" class="form-control" aria-label="Recipient's username" aria-describedby="button-addon2" ref={this.search}/>
                <div class="input-group-append">
                    <button class="btn btn-outline-primary" type="submit" id="button-addon2" onClick={(e)=> this.searchPeople(e)}>
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
        </form>
    </nav>


    <div class="container-fluid gedf-wrapper">
        <div class="row">
            <div class="col-md-3">
                <div class="card">
                    <div class="card-body">
                        <div class="h5 show-text">@{this.state.res.data.user.name}</div>
                        <div class="h7 text-muted">Fullname : {this.state.res.data.user.firstName+" "+this.state.res.data.user.lastName}</div>
                        <div class="h7 show-text">Developer of web applications, JavaScript, PHP, Java, Python, Ruby, Java, Node.js,
                            etc.
                        </div>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="h6 text-muted">Followers</div>
                            <div class="h5 show-text">{this.state.res.data.followers}</div>
                        </li>
                        <li class="list-group-item">
                            <div class="h6 text-muted">Following</div>
                            <div class="h5 show-text">{this.state.res.data.following}</div>
                        </li>
                        {/* <li class="list-group-item">Vestibulum at eros</li> */}
                    </ul>
                </div>
            </div>
            <div class="col-md-6 gedf-main">

                {/* <!--- \\\\\\\Post--> */}
                <div class="card gedf-card">
                    <div class="card-header">
                        <ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="posts-tab" data-toggle="tab" href="#posts" role="tab" aria-controls="posts" aria-selected="true">Make
                                    a publication</a>
                            </li>
                        </ul>
                    </div>
                    <div class="card-body">
                        <div class="tab-content" id="myTabContent">
                            <div class="tab-pane fade show active" id="posts" role="tabpanel" aria-labelledby="posts-tab">
                                <div class="form-group">
                                    <label class="sr-only" for="message">post</label>
                                    <textarea class="form-control" id="message" rows="3" placeholder="What are you thinking?" ref={this.myPost}></textarea>
                                </div>

                            </div>
                        </div>
                        <div class="btn-toolbar justify-content-between">
                            <div class="btn-group">
                                <button type="submit" class="btn btn-primary" onClick={(e)=> this.insertPost(e)}>share</button>
                            </div>
                        </div>
                    </div>
                </div>
                {/* <!-- Post /////--> */}

                {/* <!--- \\\\\\\Post--> */}
                {this.state.res.data.postList!=null && this.state.res.data.postList!=[] && this.state.res.data.postList.map((ele,i)=>{
                    return <div class="card gedf-card mt-4">
                    <div class="card-header">
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="mr-2">
                                    <img class="rounded-circle" width="45" src="https://picsum.photos/50/50" alt=""/>
                                </div>
                                <div class="ml-2">
                                    <div class="h5 show-text m-0">@{ele.user.name}</div>
                                    <div class="h7 text-muted">{ele.user.firstName+" "+ele.user.lastName}</div>
                                </div>
                            </div>
                            <div>
                                {/* <div class="dropdown">
                                    <button class="btn btn-link dropdown-toggle" type="button" id="gedf-drop1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="fa fa-ellipsis-h"></i>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="gedf-drop1">
                                        <div class="h6 dropdown-header">Configuration</div>
                                        <a class="dropdown-item" href="#">Save</a>
                                        <a class="dropdown-item" href="#">Hide</a>
                                        <a class="dropdown-item" href="#">Report</a>
                                    </div>
                                </div> */}
                            </div>
                        </div>

                    </div>
                    <div class="card-body">
                        <div class="text-muted h7 mb-2"> <i class="fa fa-clock-o"></i>{"  "+new Date(ele.date).toLocaleString()}</div>
                        {/* <a class="card-link" href="#">
                            <h5 class="card-title">Lorem ipsum dolor sit amet, consectetur adip.</h5>
                        </a> */}

                        <p class="card-text show-text">
                            {ele.text}
                        </p>
                    </div>
                    {/* <div class="card-footer">
                        <a href="#" class="card-link"><i class="fa fa-gittip"></i> Like</a>
                        <a href="#" class="card-link"><i class="fa fa-comment"></i> Comment</a>
                        <a href="#" class="card-link"><i class="fa fa-mail-forward"></i> Share</a>
                    </div> */}
                </div>


                })}
                
            </div>
            {/* <div class="col-md-3">
                <div class="card gedf-card">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the
                            card's content.</p>
                        <a href="#" class="card-link">Card link</a>
                        <a href="#" class="card-link">Another link</a>
                    </div>
                </div>
                <div class="card gedf-card">
                        <div class="card-body">
                            <h5 class="card-title">Card title</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the
                                card's content.</p>
                            <a href="#" class="card-link">Card link</a>
                            <a href="#" class="card-link">Another link</a>
                        </div>
                    </div>
            </div> */}
        </div>
    </div>
    </div>
        
    );
  }
}

export default Profile;
