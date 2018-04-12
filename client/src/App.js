import React, { Component } from 'react';
import './App.css';
import Basic from './Basic'
import Draggable from 'react-draggable';
import SFU from './SimpleFileUpload'

import Header from './Header';
import ShareComponent from './ShareComponent'

import Test from './Test'
import {
  Button,
 
  Panel,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem
} from 'reactstrap';



class App extends Component {


  constructor() {
    super();

  }

  render() {

    return (

      <div className="App">

        <div><Header /></div>
        <Button type="button" class="btn btn-primary">ghgg</Button>

        <Draggable>
          <div>I can now be moved around!</div>
        </Draggable>
      

        <ShareComponent />
        <Test />

      </div>
    );
  }
}

export default App;
