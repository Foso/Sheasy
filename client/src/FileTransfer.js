import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter,InputGroup,InputGroupAddon,Input, ListGroup, ListGroupItem } from 'reactstrap';
import AppsView from './AppsView';
import FileDropZone from './Basic'
import { Table } from 'reactstrap';
import { API_ROOT } from './api-config';
import axios, { post } from 'axios';
import DownloadIcon from 'mdi-react/DownloadIcon';
import DeleteIcon from 'mdi-react/DeleteIcon';
import { Container, Row, Col,Grid } from 'reactstrap';
import './MyModal.css';
import Dropzone from 'react-dropzone'



class FileTransfer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      leftItems: [],
      rightItems: [],
      title : "Hallo",
      requestFailed: false,
       files: [] 
    };

    this.toggle = this.toggle.bind(this);
    this.search = this.search.bind(this);
    this.onDrop = this.onDrop.bind(this);

  }

  toggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  onDrop(files) {
    console.log(files)

    console.log(files[0])

    
    const formData = new FormData();
    formData.append('file', files[0]);


    axios({
      url: `http://${API_ROOT}/api/v1/file?upload=/storage/emulated/0/${files[0].name}`,
      method: 'POST',
      data: formData
    })


  }

  componentDidMount() {
    this.setState(
      { title: `http://${API_ROOT}/api/v1/file?upload=/storage/emulated/0/` }
    )
    axios.get(`http://${API_ROOT}/api/v1/file?download=/storage/emulated/0/`, {
      timeout: 10000
    }).then(res => {
      this.setState(
        { leftItems: res.data }
      );
      console.log(res.data)
    });



  }

  search(data){
    this.setState(
      { title: `http://${API_ROOT}/api/v1/file?upload=`+data }
    )
    axios.get(`http://${API_ROOT}/api/v1/file?download=`+data, {
      timeout: 10000
    }).then(res => {
      this.setState(
        { rightItems: res.data }
      );
      console.log(res.data)
    });
  }

  render() {
    return (
      
      <div>
        <div onClick={this.toggle}>Files</div>
       

        <Modal isOpen={this.state.modal} toggle={this.toggle}  class="custom-modal" className={this.props.className} size="lg" >
          <ModalHeader toggle={this.toggle}>Modal title</ModalHeader>
          <ModalBody>
          <InputGroup>
        <InputGroupAddon addonType="prepend" >@</InputGroupAddon>
        <Input placeholder={this.state.title} />
      </InputGroup>
      <section>
          <div className="dropzone">
            <Dropzone onDrop={this.onDrop.bind(this)}>
              <p>Try dropping some files here, or click to select files to upload.</p>
            </Dropzone>
          </div>
          <aside>
            <h2>Dropped files</h2>
            <ul>
              {
                this.state.files.map(f => <li key={f.name}>{f.name} - {f.size} bytes</li>)
              }
            </ul>
          </aside>
        </section>
          <Container>
        
        <Row>
          <Col>
          <ListGroup>
         
          {
            
            this.state.leftItems.map(f =>
              <ListGroupItem key={f.name}  tag="button" action  onClick={() => this.search(f.path)}>{f.name}</ListGroupItem>
               )
          }
        </ListGroup>
          
           </Col>
          <Col > {
            this.state.rightItems.map(f =>
              <tr key={f.name}   >
                <div>{f.name}</div> </tr>)
          }</Col>
        </Row>
      </Container>
         

          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.toggle}>Do Something</Button>{' '}
            <Button color="secondary" onClick={this.toggle}>Cancel</Button>
          </ModalFooter>
        </Modal>

       
      </div>
    );
  }
}

export default FileTransfer;