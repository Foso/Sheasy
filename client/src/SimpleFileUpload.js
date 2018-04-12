import React from 'react'
import axios, { post } from 'axios';
import { API_ROOT } from './api-config';

class SimpleReactFileUpload extends React.Component {


componentDidMount() {

  const formData = new FormData();
  formData.append('action', 'ADD');
  formData.append('param', 0);
  formData.append('secondParam', 0);
  formData.append('file', new Blob(['test payload'], { type: 'text/csv' }));
  axios({
    url: `htt://${API_ROOT}/api/v1/file?file=/storage/emulated/0/rdrd/test.txt`,
    method: 'POST',
    data: formData,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'multipart/form-data'
    }
  })

}

render(){
  return (

    <div>
         <h2>Installed Apps</h2>

     
    </div>
  );
}

}



export default SimpleReactFileUpload