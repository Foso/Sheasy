import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import AppsView from './AppsView';
import Basic from './Basic'
import { Table } from 'reactstrap';
import { API_ROOT } from './api-config';
import axios, { post } from 'axios';
import DownloadIcon from 'mdi-react/DownloadIcon';
import DeleteIcon from 'mdi-react/DeleteIcon';
class Contacts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modal: false,
      items: [],
      accounts: [],
      requestFailed: false
    };

    this.toggle = this.toggle.bind(this);

  }

  toggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  componentDidMount() {

    axios.get(`http://${API_ROOT}/api/v1/contacts`, {
      timeout: 10000
    }).then(res => {
      this.setState(
        { items: res.data, accounts: res.data }
      );
      console.log(res.data)
    });

  }

  render() {
    return (
      <div>
        <div onClick={this.toggle}>Contacts</div>
       

        <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className} size="lg">
          <ModalHeader toggle={this.toggle}>Modal title</ModalHeader>
          <ModalBody>
         
          <Basic />
          <Table>
          <tbody>
          <tr>
            <th>Name</th>
            <th>Download</th>
            <th>Uninstall</th>

          </tr>
          {
            this.state.items.map(f =>
              <tr key={f.name}   >
                <td><div><p>{f.name}</p><p>{f.phone}</p></div></td><td><a href={`http://${API_ROOT}/api/v1`+"/file?file="+f.phone}><DownloadIcon /></a></td><td><DeleteIcon />
                </td>
              </tr>)
          }
 </tbody>
        </Table>

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

export default Contacts;