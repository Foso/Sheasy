import React, { Component } from 'react';
import MediaControl from './MediaControl'
import NotificationCom from './NotificationCom'
import AppsModalExample from './AppsModalExample'
import DeviceModalExample from './DeviceModalExample'
import FileTransfer from './FileTransfer'



import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink,
  
   } from 'reactstrap';
import Contacts from './Contacts';
   


    class Header extends Component {
   
    constructor(props) {
        super(props);
       

        this.state = {
            ignore: true,
            title: '',
            isOpen: false,
            notification : "HHHH"
      };

      
      }
   
    render() {
      return (
        <div>
          
         <Navbar color="faded" light expand="md">
          <NavbarBrand href="https://github.com/foso">Github</NavbarBrand>
          <NavbarToggler onClick={this.toggle} />
          <Collapse isOpen={this.state.isOpen} navbar>
            <Nav className="ml-auto" navbar>
         
            <NavItem  >
                <NavLink><MediaControl /></NavLink>
              </NavItem>
          
              <NavItem  onClick={this.openModal}>
                <NavLink> <div><AppsModalExample /></div></NavLink>
              </NavItem>
              <NavItem  onClick={this.openModal}>
                <NavLink> <div><DeviceModalExample /></div></NavLink>
              </NavItem>
              <NavItem  onClick={this.openModal}>
                <NavLink> <div><FileTransfer/></div></NavLink>
              </NavItem>
              <NavItem  onClick={this.openModal}>
                <NavLink> <div><Contacts/></div></NavLink>
              </NavItem>
              
              <NavItem>
                <NavLink><NotificationCom /></NavLink>
              </NavItem>
            </Nav>
          </Collapse>
        </Navbar>
        
     
      

        </div>
      );
    }
     
       
   
  }
  
  export default Header;