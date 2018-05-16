import React, {Component} from "react";
import "./404.css";
import {CardContent, Typography} from "material-ui";

class NotFound extends Component {
    render() {
        const bull = <span className="bullet">•</span>;
        return (
                <CardContent className="">
                    <Typography className="title" color="textSecondary">
                        Ошибка
                    </Typography>
                    <Typography variant="headline" component="h2">
                        4{bull}0{bull}4
                    </Typography>
                    <Typography className="pos" color="textSecondary">
                        Значение:
                    </Typography>
                    <Typography component="p">
                        Ресурс не найден :(
                    </Typography>
                </CardContent>
        );
    }
}

export default NotFound;