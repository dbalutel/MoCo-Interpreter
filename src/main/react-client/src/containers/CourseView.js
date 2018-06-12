import React, {Component} from 'react';
import "./CourseView.css";
import PropTypes from 'prop-types';
import {withStyles} from 'material-ui/styles';
import Drawer from 'material-ui/Drawer';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import Hidden from 'material-ui/Hidden';
import MenuIcon from '@material-ui/icons/Menu';
import CourseContent from "../components/CourseContent";
import StartupDialog from "../components/StartupDialog";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

import axios from "axios";
import {
    Button,
    Card, Checkbox,
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    FormControl, FormControlLabel, FormLabel,
    Paper, Radio, RadioGroup, TextField
} from "material-ui";

const drawerWidth = 240;

const styles = theme => ({
    appBar: {
        position: 'absolute',
        marginLeft: drawerWidth,
        [theme.breakpoints.up('md')]: {
            width: `calc(100% - ${drawerWidth}px)`,
        },
        backgroundColor: '#3366CC'
    },
    navIconHide: {
        [theme.breakpoints.up('md')]: {
            display: 'none',
        },
    },
    drawerPaper: {
        width: drawerWidth,
        [theme.breakpoints.up('md')]: {
            position: 'relative',
        },
    },
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        padding: theme.spacing.unit * 3,
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular,
    },
});

class CourseView extends Component {
    state = {
        mobileOpen: false,
        lesson: null,
        value: ''
    };

    componentDidMount() {
        axios.get("/api/" + sessionStorage.getItem("username") + "/courses/"
            + sessionStorage.getItem("courseName")
            + "/lessons/"
            + sessionStorage.getItem("lastVisitedLesson"))
            .then(result => {
                const lesson = result.data;
                console.log(lesson);
                this.setState({lesson});
            })
            .catch(error => console.log(error));
    }

    handleDrawerToggle = () => {
        this.setState({mobileOpen: !this.state.mobileOpen});
    };

    handleChange = event => {
        this.setState({ value: event.target.value });
    };

    renderLesson() {
        if (this.state.lesson !== null) {
            return (
                <div className="overflow-class">
                    <Typography variant="headline" component="h2">{this.state.lesson.name}</Typography>
                    <div dangerouslySetInnerHTML={{__html: this.state.lesson.content}}/>
                </div>
            );
        }
    }

    renderTest() {
        if (this.state.lesson !== null
            && this.state.lesson.testInstances !== null
            && this.state.lesson.testInstances !== undefined
            && this.state.lesson.testInstances.length > 0) {

            return (
                <div className="overflow-class">
                    <Typography variant="headline" component="h2">{this.state.lesson.testInstances[0].name}</Typography>
                    <Typography component="h5">{this.state.lesson.testInstances[0].information}</Typography>
                    {this.renderQuestions()}
                </div>
            );
        }
    }

    renderQuestions() {

        const questionsToRender = [];

        for (let i = 0; i < this.state.lesson.testInstances[0].questions.length; i++) {
            questionsToRender.push(
                <Card className="question-cards">
                    <ExpansionPanel>
                        <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                            <Typography>{this.state.lesson.testInstances[0].questions[i].content}</Typography>
                        </ExpansionPanelSummary>
                        <ExpansionPanelDetails>
                            <Typography>
                                {this.renderAnswers(i, this.state.lesson.testInstances[0].questions[i].questionType)}
                            </Typography>
                            <div>
                                <Button color="primary">Ответить</Button>
                            </div>
                        </ExpansionPanelDetails>
                    </ExpansionPanel>
                </Card>
            )
        }

        return(
            questionsToRender
        );
    }

    renderAnswers(questionNumber, questionType) {

        if (questionType === 'SINGLE') {
            return(
                <RadioGroup
                    aria-label="gender"
                    name="gender1"
                    value={this.state.value}
                    onChange={this.handleChange}
                >
                    {this.renderSingleAnswer(this.state.lesson.testInstances[0].questions[questionNumber].answers)}
                </RadioGroup>
            );

        } else if (questionType === 'FREE') {
            return(
                <div>
                    {this.renderFreeAnswer()}
                </div>
            )
        } else if (questionType === 'MULTIPLE') {
            return(
                <div>
                    {this.renderMultipleQuestion(this.state.lesson.testInstances[0].questions[questionNumber].answers)}
                </div>
            )

        }
    }

    renderSingleAnswer(answers) {
        const answersToRender = [];

        for (let i = 0; i < answers.length; i++) {
            answersToRender.push(
                <FormControlLabel value={answers[i].content} control={<Radio />} label={answers[i].content} />
            )
        }

        return(
            answersToRender
        );
    }

    renderMultipleQuestion(answers) {
        const answersToRender = [];

        for (let i = 0; i < answers.length; i++) {
            answersToRender.push(<div>
                <Checkbox/>
                {answers[i].content}
            </div>);
        }

        return(
            answersToRender
        );
    }

    renderFreeAnswer() {
        return(
            <div>
                <TextField
                    id=""
                    label=""
                    defaultValue=""
                    margin="normal"
                />
            </div>
        );
    }

    render() {
        const {classes, theme} = this.props;

        return (
            <div className="root">
                <AppBar className={classes.appBar}>
                    <Toolbar>
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            onClick={this.handleDrawerToggle}
                            className={classes.navIconHide}
                        >
                            <MenuIcon/>
                        </IconButton>
                        <Typography variant="title" color="inherit" noWrap>
                            {sessionStorage.getItem("courseName")}
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Hidden mdUp>
                    <Drawer
                        variant="temporary"
                        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
                        open={this.state.mobileOpen}
                        onClose={this.handleDrawerToggle}
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                        ModalProps={{
                            keepMounted: true
                        }}
                    >
                        <CourseContent/>
                    </Drawer>
                </Hidden>
                <Hidden smDown implementation="css">
                    <Drawer
                        variant="permanent"
                        open
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                    >
                        <CourseContent/>
                    </Drawer>
                </Hidden>
                <main className={classes.content}>
                    <div className={classes.toolbar}/>
                    <StartupDialog/>
                    <div className="overflow-class">
                        <Paper className="content">
                            {this.renderLesson()}
                        </Paper>
                        <Paper className="content">
                            {this.renderTest()}
                        </Paper>
                    </div>
                </main>
            </div>
        );
    }
}

CourseView.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, {withTheme: true})(CourseView);
