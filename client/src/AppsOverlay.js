import React, { Component } from 'react';
import Modal from 'react-modal';

import Draggable from 'react-draggable'; 
import Basic from './Basic'
import AppsView from './AppsView'

 const numbers = [1, 2, 3, 4, 5];
  const listItems = numbers.map((number) =>
    <li>{number}</li>
  );

  const API = 'https://hn.algolia.com/api/v1/search?query=';
const DEFAULT_QUERY = 'redux';
class AppsOverlay extends React.Component {
  
 
  componentDidMount() {
    this.getMoviesFromApiAsync()
  }

   getMoviesFromApiAsync() {
    return fetch('https://facebook.github.io/react-native/movies.json')
      .then((response) => response.json())
      .then((responseJson) => {
        return responseJson.movies;
      })
      .catch((error) => {
        console.error(error);
      });
  }

    constructor(props) {
        super(props);
    
        this.state = {
        
          modalIsOpen: false
       
              };
    
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);

       
      }
    
      openModal() {
        this.setState({modalIsOpen: true});
      }

     
    
      afterOpenModal() {
        // references are now sync'd and can be accessed.
        this.subtitle.style.color = '#f00';
      }
    
      closeModal() {
        this.setState({modalIsOpen: false});
      }

   



      render() {
    
        return (
          <div>
            
<AppsView></AppsView>
          </div>
        );
      }


}
export default AppsOverlay;
