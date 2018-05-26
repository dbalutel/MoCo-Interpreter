import React, {Component} from "react";
import {withStyles} from "material-ui/styles/index";
import PropTypes from "prop-types";
import {Divider, ListItemText, MenuItem, MenuList} from "material-ui";

import axios from "axios";

const styles = theme => ({
    toolbar: theme.mixins.toolbar,
    menuItem: {
        '&:focus': {
            backgroundColor: theme.palette.primary.main,
            '& $primary, & $icon': {
                color: theme.palette.common.white,
            },
        },
    },
    primary: {},
});

class CourseContent extends Component {

    state = {
        lessons: []
    };

    componentDidMount() {
        axios.get("/api/courses/" + sessionStorage.getItem("courseName") + "/lessons")
            .then(result => {
                const lessons = result.data;
                this.setState({lessons});
            })
            .catch(error => console.log(error));
    }

    renderCourseContent(classes) {
        const listOfLessons = [];
        this.state.lessons.forEach(lesson => {
            listOfLessons.push(
                <React.Fragment key={lesson.lessonNumber}>
                    <MenuItem divider className={classes.menuItem}>
                        <ListItemText classes={{primary: classes.primary}}
                                      primary={lesson.lessonNumber + ". " + lesson.lessonName}/>
                    </MenuItem>
                </React.Fragment>
            )
        });
        return listOfLessons;
    }

    render() {
        const {classes} = this.props;

        return (
            <React.Fragment>
                <div className={classes.toolbar}/>
                <Divider/>
                <MenuList>
                    {this.renderCourseContent(classes)}
                </MenuList>
            </React.Fragment>
        );
    }
}

CourseContent.propTypes = {
    classes: PropTypes.object.isRequired
};
export default withStyles(styles, {withTheme: true})(CourseContent);