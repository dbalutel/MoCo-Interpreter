import React, {Component} from "react";
import "./Navigation.css";
import Grid from "material-ui/Grid/Grid";
import TextField from "material-ui/TextField/TextField";
import Card from "material-ui/Card/Card";
import Button from "material-ui/Button";
import CardContent from "material-ui/Card/CardContent";
import CardActions from "material-ui/Card/CardActions";
import Typography from "material-ui/Typography/Typography";
import InputAdornment from "material-ui/Input/InputAdornment";
import MenuItem from "material-ui/Menu/MenuItem";
import {AccountCircle} from "@material-ui/icons/index";
import axios from 'axios';

class Navigation extends Component {
    render() {
        return (
            <Card className="card-selector">
                {this.props.content}
            </Card>
        );
    }
}

export default Navigation;