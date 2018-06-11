import React from 'react';
import axios from "axios/index";
import {Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "material-ui";

class StartupDialog extends React.Component {
    state = {
        open: true,
    };

    handleClose = () => {
        this.setState({ open: false });
    };

    componentDidMount() {
        axios.get("/api/courses/" + sessionStorage.getItem("courseName") + "/startup")
            .then(result => {
                const startup = result.data.startUpText;
                this.setState({startup});
            })
            .catch(error => console.log(error));
    }

    render() {
        return (
            <div>
                <Dialog
                    open={this.state.open}
                    onClose={this.handleClose}
                    aria-labelledby="alert-dialog-title"
                    aria-describedby="alert-dialog-description"
                >
                    <DialogTitle id="alert-dialog-title">{sessionStorage.getItem("courseName")}</DialogTitle>
                    <DialogContent>
                        <DialogContentText id="alert-dialog-description">
                            <div dangerouslySetInnerHTML={{__html: this.state.startup}} />
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary" autoFocus>
                            Закрыть
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

export default StartupDialog;