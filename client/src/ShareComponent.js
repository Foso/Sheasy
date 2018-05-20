
import React, { Component } from 'react';

import axios from 'axios';
import Socket from "simple-websocket";
import Notification from './Notification';

import { Button, Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';
import logo from './img/alarm-light.svg'
import { Tooltip } from 'reactstrap';
import { API_ROOT } from './api-websocket-config';

class ShareComponent extends React.Component {


  constructor(props) {
    super(props);


    this.state = {
      ignore: true,
      title: '',
      isOpen: false,
      notification: "HHHH",
      tooltipOpen: false,
      notificationBody: "BBHHHH",
      dropdownOpen: false
    };

    this.socket = new Socket({
      url: `ws://${API_ROOT}/websocket/message`
    });

    this.handleButtonClick = this.handleButtonClick.bind(this);
    this.onMessage = this.onMessage.bind(this);
    this.toggle = this.toggle.bind(this);
    this.tooltipToggle = this.tooltipToggle.bind(this);


    this.socket.on('connect', function () {
      console.log("connected")
    });

    /* this.socket.on('data', function (data) {
       console.log('got message: ' + data)
   
       this.sendMessage("hh") 

     
  
     });*/

    this.socket.on('data', this.onMessage);

  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  tooltipToggle() {
    this.setState({
      tooltipOpen: !this.state.tooltipOpen
    });
  }



  onMessage(data) {
    // this.state.socket.sendMessage(message);
    console.log('got message: ' + data);
    this.setState({ notification: JSON.parse(data.toString())['title'], notificationBody: JSON.parse(data.toString())['text'] });
  }


  handlePermissionGranted() {
    console.log('Permission Granted');
    this.setState({
      ignore: false
    });
  }
  handlePermissionDenied() {
    console.log('Permission Denied');
    this.setState({
      ignore: true
    });
  }
  handleNotSupported() {
    console.log('Web Notification not Supported');
    this.setState({
      ignore: true
    });
  }

  handleNotificationOnClick(e, tag) {
    console.log(e, 'Notification clicked tag:' + tag);
  }

  handleNotificationOnError(e, tag) {
    console.log(e, 'Notification error tag:' + tag);
  }

  handleNotificationOnClose(e, tag) {
    console.log(e, 'Notification closed tag:' + tag);
  }

  handleNotificationOnShow(e, tag) {
    this.playSound();
    console.log(e, 'Notification shown tag:' + tag);
  }

  playSound(filename) {
    document.getElementById('sound').play();
  }

  handleButtonClick() {
    console.log();
    if (this.state.ignore) {
      return;
    }

    const now = Date.now();

    const title = 'React-Web-Notification' + now;
    const body = this.state.notificationBody
    const tag = now;
    const icon = 'http://georgeosddev.github.io/react-web-notification/example/Notifications_button_24.png';
    // const icon = 'http://localhost:3000/Notifications_button_24.png';

    // Available options
    // See https://developer.mozilla.org/en-US/docs/Web/API/Notification/Notification
    const options = {
      tag: tag,
      body: body,
      icon: icon,
      lang: 'en',
      dir: 'ltr',
      sound: './sound.mp3'  // no browsers supported https://developer.mozilla.org/en/docs/Web/API/notification/sound#Browser_compatibility
    }
    this.setState({
      title: title,
      options: options
    });
  }



  render() {
    return (
      <div>


        <Notification
          ignore={this.state.ignore && this.state.title !== ''}
          notSupported={this.handleNotSupported.bind(this)}
          onPermissionGranted={this.handlePermissionGranted.bind(this)}
          onPermissionDenied={this.handlePermissionDenied.bind(this)}
          onShow={this.handleNotificationOnShow.bind(this)}
          onClick={this.handleNotificationOnClick.bind(this)}
          onClose={this.handleNotificationOnClose.bind(this)}
          onError={this.handleNotificationOnError.bind(this)}
          timeout={1000}
          title={this.state.notification}
          options={this.state.options}
        />
        <audio id='sound' preload='auto'>
          <source src='./sound.mp3' type='audio/mpeg' />
          <source src='./sound.ogg' type='audio/ogg' />
          <embed hidden='true' autostart='false' loop='false' src='./sound.mp3' />
        </audio>
        <img src={logo} />

        <Button type="button" id="TooltipExample" onClick={this.handleButtonClick.bind(this)} class="btn btn-primary">{this.state.notification}</Button>
        <Tooltip placement="right" isOpen={this.state.tooltipOpen} target="TooltipExample" toggle={this.tooltipToggle}>
          <p>{this.state.notification}</p>
          {this.state.notificationBody}
        </Tooltip>

      </div>
    );
  }

}

export default ShareComponent