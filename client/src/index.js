import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import registerServiceWorker from './registerServiceWorker';
import 'react-virtualized/styles.css'




ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();
