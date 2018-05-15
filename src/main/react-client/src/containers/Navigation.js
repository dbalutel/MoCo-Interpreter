import React, {Component} from "react";
import "./Navigation.css";
import Card from "material-ui/Card/Card";

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