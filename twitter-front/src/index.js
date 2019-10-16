import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { BrowserRouter as Router} from 'react-router-dom';
import {register as registerServiceWorker} from './serviceWorker';
import App from './App';

ReactDOM.render(
<Router>
    <App />
</Router>, document.getElementById('root'));
registerServiceWorker();
