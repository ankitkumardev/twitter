import React from 'react';
import { withRouter } from 'react-router';
import { getCookie } from './Cookie';

export default function requireAuth(Component) {

  class AuthenticatedComponent extends React.Component {

    constructor(props) {
        super()
    }
    componentWillMount() {
      this.checkAuth();
    }

    checkAuth() {
      if (getCookie("token")==null ||getCookie("token").toString().trim()=="") {
        const location = this.props.location;
        const redirect = location.pathname + location.search;
        this.props.history.push(`/login?redirect=${redirect}`);
      }
    }

    render() {
      return (getCookie("token")==null ||getCookie("token").toString().trim()=="")
        ? null
        :  <Component { ...this.props } />;
    }

  }

  return withRouter(AuthenticatedComponent);
}