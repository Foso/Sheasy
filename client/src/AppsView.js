import React, { Component } from 'react';
import axios from 'axios'
import AutoSizer from 'react-virtualized/dist/commonjs/AutoSizer'
import List from 'react-virtualized/dist/commonjs/List'
import ReactList from 'react-list';
import DownloadIcon from 'mdi-react/DownloadIcon';
import DeleteIcon from 'mdi-react/DeleteIcon';
import Draggable from 'react-draggable';
import Header from './Header';
import { Table } from 'reactstrap';
import { API_ROOT } from './api-config';


class AppsView extends Component {
  constructor(props) {
    super(props)
    this.state = {
      items: [],
      accounts: [],
      requestFailed: false
    }
  }



  componentDidMount() {

    axios.get(`http://${API_ROOT}/api/v1/apps/`, {
      timeout: 10000
    }).then(res => {
      this.setState({ items: res.data, accounts: res.data });
    });

  }

  render() {
    return (

      <div>
           <h2>Installed Apps</h2>
 
        <Table>
          <tr>
            <th>Name</th>
            <th>Download</th>
            <th>Uninstall</th>

          </tr>
          {
            this.state.items.map(f =>
              <tr key={f.name}   >
                <td><div><p>{f.name}</p><p>{f.packageName}</p></div></td><td><a href={`http://${API_ROOT}/api/v1`+"/file?apk="+f.packageName}><DownloadIcon /></a></td><td><DeleteIcon />
                </td>
              </tr>)
          }

        </Table>

      </div>
    );
  }



}

export default AppsView;


