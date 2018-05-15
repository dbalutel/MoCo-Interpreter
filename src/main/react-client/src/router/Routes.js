import React, {Component} from 'react';
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import CourseView from "../containers/CourseView";
import Navigation from "../containers/Navigation";
import NotFound from "../components/404";
import Switch from "react-router-dom/es/Switch";
import Login from "../components/Login";

const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={(props) => (
        sessionStorage.getItem("username") !== null
            ? <Component {...props} />
            : <Redirect to="/" />
    )} />
);

class Routes extends Component {
    render() {
        return(
          <Router>
              <React.Fragment>
                  <Switch>
                      <Route exact path="/" render={()=><Navigation content={<Login/>}/>}/>
                      <PrivateRoute exact path="/course/:name" component={CourseView}/>
                      <Route render={()=><Navigation content={<NotFound/>}/>}/>
                  </Switch>
              </React.Fragment>
          </Router>
        );
    }
}
export default Routes;
