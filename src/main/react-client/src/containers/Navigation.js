import React, {Component} from "react";
import "./Navigation.css";
import Card from "material-ui/Card/Card";

class Navigation extends Component {
    render() {
        return (
            <div className="card-selector">
                <Card>
                    {this.props.content}
                </Card>
            </div>
        );
    }
}

export default Navigation;