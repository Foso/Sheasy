import React from "react";
import Socket from "simple-websocket";
import Notification from './Notification';

class SocketComponent extends React.Component{
    constructor(props) {
        super(props);
       

        this.state = {
            ignore: true,
            title: '',
            notification : "HHHH"
      };

        this.socket =   new Socket({
            url: "ws://192.168.178.22:8765"
           
        });

        this.handleButtonClick = this.handleButtonClick.bind(this);
        this.onMessage = this.onMessage.bind(this);

        this.socket.on('connect', function () {
            console.log("connected")
          });
           
         /* this.socket.on('data', function (data) {
            console.log('got message: ' + data)
        
            this.sendMessage("hh") 

          
       
          });*/

          this.socket.on('data', this.onMessage);
         
      }


            
       onMessage(data){
       // this.state.socket.sendMessage(message);
       console.log('got message: ' +data);
       this.setState({ notification: 'j'+ data }); 
      // this.handleButtonClick();
      }


      handlePermissionGranted(){
        console.log('Permission Granted');
        this.setState({
          ignore: false
        });
      }
      handlePermissionDenied(){
        console.log('Permission Denied');
        this.setState({
          ignore: true
        });
      }
      handleNotSupported(){
        console.log('Web Notification not Supported');
        this.setState({
          ignore: true
        });
      }
    
      handleNotificationOnClick(e, tag){
        console.log(e, 'Notification clicked tag:' + tag);
      }
    
      handleNotificationOnError(e, tag){
        console.log(e, 'Notification error tag:' + tag);
      }
    
      handleNotificationOnClose(e, tag){
        console.log(e, 'Notification closed tag:' + tag);
      }
    
      handleNotificationOnShow(e, tag){
        this.playSound();
        console.log(e, 'Notification shown tag:' + tag);
      }
    
      playSound(filename){
        document.getElementById('sound').play();
      }
    
      handleButtonClick() {
    console.log();
        if(this.state.ignore) {
          return;
        }
    
        const now = Date.now();
    
        const title = 'React-Web-Notification' + now;
        const body = 'Hello' + new Date();
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
    


    

    render(){
        return (
            <div className="container">
                <aside>
          <h2>Installed Apps</h2>
        
        
        </aside>
        <div>{this.state.notification}</div>
        <button onClick={this.handleButtonClick.bind(this)}>Notif!</button>
        <button onClick={() => this.sendMessage("Hello")} >Send Message</button>
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
          title={this.state.title}
          options={this.state.options}
        />
        <audio id='sound' preload='auto'>
          <source src='./sound.mp3' type='audio/mpeg' />
          <source src='./sound.ogg' type='audio/ogg' />
          <embed hidden='true' autostart='false' loop='false' src='./sound.mp3' />
</audio>
            </div>
        );
    }
}

export default SocketComponent;