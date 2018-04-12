import React, { Component } from 'react';
import Dropzone from 'react-dropzone'
import { API_ROOT } from './api-config';

import axios, { post } from 'axios';

class FileDropZone extends React.Component {
    constructor() {
      super()
      this.state = { files: [] },
      this.onDrop = this.onDrop.bind(this);
    }
  
    onDrop(files) {
      console.log(files)

      console.log(files[0])

      
      const formData = new FormData();
      formData.append('file', files[0]);


      axios({
        url: `http://${API_ROOT}/api/v1/file?file=/storage/emulated/0/rdrd/${files[0].name}`,
        method: 'POST',
        data: formData
      })


    }



  
  
    render() {
      return (
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
      );
    }



    
  }

  
  
  export default FileDropZone;



  