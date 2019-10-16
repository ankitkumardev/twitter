import React, { Component } from 'react';
import '../App.css';
import './RegisterPage.css'

import { searchPeople, followOrUnFollowApi } from '../helpers/Ajax';
import { deleteCookie } from '../helpers/Cookie';


class Search extends Component {

    constructor(props) {
        super();
        this.search = React.createRef();
        this.state = {
            res:null
        }
    }
    logout(e){
        e.preventDefault();
        deleteCookie("token");
        this.props.history.push("/login");
        
    }
    componentWillMount(){
        // var url_string = window.location.href;
        // var url = new URL(url_string);
        // var param = url.searchParams.get("param");
        var param = this.props.match.params.text;
        if(param&&param.trim()!=""){
            searchPeople(param.trim()).then((res) => {
                console.log(res);
                if(res.status==200){
                    this.setState({
                        res:res.data
                      })
                }
                
           }).catch((err)=>{

           });
           
        }
        
    }
    followOrUnFollow(e,ele,i){
        e.preventDefault();
        followOrUnFollowApi(ele.id,ele.isFriend=="FRIEND"?false:true).then((res) => {
            console.log(res);
            if(res.status==200){
                window.location.reload();
                
            }
            
       }).catch((err)=>{

       });

    }
    searchPeople(e){
        // console.log("ankit")
        e.preventDefault();
        if(this.search.current.value.trim()!=""){
                    this.props.history.push({
                        pathname: '/search/'+this.search.current.value.trim()
                      })     
                      window.location.reload();
    
        }

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

                <div class="container-fluid gedf-wrapper mt-4">
                    <div class="row">
                        <div class="col-md-6 gedf-main">
                            <div>
                            {/* start loop */}
                            {
                                this.state.res!== null &&  this.state.res !== [] &&  this.state.res.map((ele, i) => {
                                    return <div class="card gedf-card mb-2">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="mr-2">
                                                <img class="rounded-circle" width="45" src="https://picsum.photos/50/50" alt="" />
                                            </div>
                                            <div class="ml-2">
                                                <div class="h5 show-text m-0">{"@"+ele.name}</div>
                                                <div class="h7 text-muted">{ele.firstName+" "+ele.lastName}</div>
                                            </div>
                                        </div>
                                        {ele.isFriend!="SELF"&&<div class="mr-2" style={{float:"right"}}>
                                                <button class="btn btn-outline-primary" type="button" id="button-addon2" onClick={(e)=> this.followOrUnFollow(e,ele,i)}>
                                                    {ele.isFriend=="FRIEND"?"Unfollow":"Follow"}
                                                </button>
                                            </div>
                                        }
          
                                    </div>
                                </div>
                            </div>

                                })
                            }
                            </div>




                            {/* <div class="card gedf-card mb-2">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div class="mr-2">
                                                <img class="rounded-circle" width="45" src="https://picsum.photos/50/50" alt="" />
                                            </div>
                                            <div class="ml-2">
                                                <div class="h5 show-text m-0">@LeeCross</div>
                                                <div class="h7 text-muted">Miracles Lee Cross</div>
                                            </div>
                                        </div>
                                        <div class="mr-2" style={{float:"right"}}>
                                                <button class="btn btn-outline-primary" type="button" id="button-addon2">
                                                    {"Follow"}
                                                </button>
                                            </div>
          
                                    </div>
                                </div>
                            </div> */}

                            {/* End Loop */}
                            

                            


                        </div>

                    </div>
                </div>
            </div>

        );
    }
}

export default Search;
