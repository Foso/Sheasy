
import React, { Component } from 'react';

import axios from 'axios';

import pauseImg from './img/pause.svg'

import playImg from './img/play.svg'
import nextImg from './img/skip-next.svg'
import prevImg from './img/skip-previous.svg'
import { API_ROOT } from './api-config';



class MediaControl extends React.Component {



    constructor(props) {
        super(props);
       
        this.next = this.next.bind(this);
        this.prev = this.prev.bind(this);
        this.pause = this.pause.bind(this);
        this.play= this.play.bind(this);
        this.andlePermissionGranted= this.andlePermissionGranted.bind(this);
    }

    andlePermissionGranted(data){
      
        axios.get(`http://${API_ROOT}/api/v1/media/`+data, {
          timeout:3000
        });
      }
  
      next(){
        this.andlePermissionGranted("next")
      }
  
      prev(){
        this.andlePermissionGranted("prev")
      }
  
      pause(){
        this.andlePermissionGranted("pause")
      }
      play(){
        this.andlePermissionGranted("play")
      }

    render() {
        return (
            <div>
                <img src={prevImg}  onClick={this.prev}/>
                 <img src={pauseImg}  onClick={this.pause}/>
          
                 <img src={playImg}  onClick={this.play}/>
             
                 <img src={nextImg} onClick={this.next}/>
          
          </div>
        );
      }



}


export default MediaControl;

