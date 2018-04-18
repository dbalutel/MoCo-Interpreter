import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import CourseView from './components/CourseView';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<CourseView />, document.getElementById('root'));
registerServiceWorker();
