
import React from 'react';
import SideNav, { Nav, NavIcon, NavText } from 'react-sidenav';
import { Container, Row, Col } from 'reactstrap';




class Test extends React.Component {


  
    

    render() {
        return (
          <Container>
        
        <Row>
          <Col sm={{ size: 'auto', offset: 1 }}>.col-sm .col-sm-offset-1</Col>
          <Col sm={{ size: 'auto', offset: 1 }}>.col-sm .col-sm-offset-1</Col>
        </Row>
      </Container>
        );
      }



}


export default Test;

