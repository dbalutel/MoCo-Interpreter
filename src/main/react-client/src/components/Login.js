import React, {Component} from 'react';
import {Button, CardActions, CardContent, Grid, InputAdornment, MenuItem, TextField, Typography} from "material-ui";
import {AccountCircle} from "@material-ui/icons/index";
import axios from "axios/index";
import {withRouter} from "react-router-dom";

class Login extends Component {

    constructor(props) {
        super(props)
    }

    state = {
        courseName: '',
        courses: [],
        username: '',
    };

    componentDidMount() {
        axios.get("/api/courses")
            .then(result => {
                const courses = result.data;
                this.setState({courses});
            })
            .catch(error => console.log(error));
    }

    handleChange = name => event => {
        this.setState({
            [name]: event.target.value,
        });
    };

    logIn = () => {
        axios.get("/api/login/" + this.state.username + "/course/" + this.state.courseName)
            .then(result => {
                sessionStorage.setItem("username", this.state.username);
                sessionStorage.setItem("courseName", this.state.courseName);
                sessionStorage.setItem("lastLesson", result.data);
                this.props.history.push("/course/" + this.state.courseName);
            })
            .catch(error => console.log(error));
    };

    isLoginDisabled = () => {
        return this.state.courseName.length === 0 || this.state.username.length === 0;
    };

    render() {
        return (
            <React.Fragment>
                <CardContent>
                    <Grid container spacing={24}>
                        <Grid item xs={12}>
                            <Typography variant="headline" component="h2">Выбор курса</Typography>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField fullWidth placeholder="Имя студента"
                                       value={this.state.username}
                                       onChange={this.handleChange("username")}
                                       InputProps={{
                                           startAdornment: (
                                               <InputAdornment position="start">
                                                   <AccountCircle/>
                                               </InputAdornment>
                                           )
                                       }}/>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                select
                                helperText="Выберите желаемый курс"
                                value={this.state.courseName}
                                onChange={this.handleChange("courseName")}
                            >
                                {this.state.courses.map(option => (
                                    <MenuItem key={option} value={option}>
                                        {option}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                    </Grid>
                </CardContent>
                <CardActions>
                    <Button variant="raised" color="primary"
                            disabled={this.isLoginDisabled.call(this)}
                            onClick={this.logIn.bind(this)}>
                        Войти
                    </Button>
                </CardActions>
            </React.Fragment>
        );
    }
}

export default withRouter(Login);